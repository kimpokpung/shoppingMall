package home.project.controller.user;

import home.project.dto.requestDTO.CreateQnARequestDTO;
import home.project.dto.responseDTO.QnADetailResponse;
import home.project.dto.responseDTO.QnAResponse;
import home.project.dto.responseDTO.ReviewDetailResponse;
import home.project.response.CustomResponseEntity;
import home.project.service.common.QnAService;
import home.project.service.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "QnA", description = "QnA관련 API입니다")
@RequestMapping("/api/qna")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/InternalServerErrorResponseSchema")))
})
@RequiredArgsConstructor
@RestController
public class QnAController {

    private final QnAService qnAService;
    private final PageUtil pageUtil;


    @Operation(summary = "QnA 작성 메서드", description = "QnA 작성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/VerifyResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/MemberValidationFailedResponseSchema"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/UnauthorizedResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))

    })
    @PostMapping("/join")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createQnA(@RequestBody CreateQnARequestDTO createQnARequestDTO) {

        QnADetailResponse qnADetailResponse = qnAService.join(createQnARequestDTO);

        String successMessage = "QnA가 작성되었습니다.";

        return new CustomResponseEntity<>(qnADetailResponse, successMessage, HttpStatus.OK);
    }

    @Operation(summary = "id로 QnA 상세정보 조회 메서드", description = "id로 QnA 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/ProductResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @GetMapping("/qna_detail")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findQnAByIdReturnQnADetailResponse(@RequestParam("qnAId") Long qnAId) {
        QnADetailResponse qnADetailResponse = qnAService.findByIdReturnQnADetailResponse(qnAId);
        String successMessage = qnAId + "에 해당하는 QnA 입니다.";
        return new CustomResponseEntity<>(qnADetailResponse, successMessage, HttpStatus.OK);
    }

    @Operation(summary = "전체 QnA 조회 메서드", description = "전체 QnA 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedProductListResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema")))
    })
    @GetMapping("/qnas")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findAll(
            @PageableDefault(page = 1, size = 5)
            @SortDefault.SortDefaults(
                    {@SortDefault(sort = "qnAId", direction = Sort.Direction.ASC)})
            @ParameterObject Pageable pageable) {
        pageable = pageUtil.pageable(pageable);
        Page<QnAResponse> pagedQnA = qnAService.findAll(pageable);

        long totalCount = pagedQnA.getTotalElements();

        int page = pagedQnA.getNumber();

        String successMessage = "모든 QnA 입니다.";

        return new CustomResponseEntity<>(pagedQnA.getContent(), successMessage, HttpStatus.OK, totalCount, page);
    }

    @Operation(summary = "상품에 해당하는 QnA 조회 메서드", description = "상품에 해당하는 QnA 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedProductListResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema")))
    })
    @GetMapping("/product_qna")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findProductReview(@RequestParam(value = "productId", required = false) Long productId,
                                               @PageableDefault(page = 1, size = 5)
                                               @SortDefault.SortDefaults(
                                                       {@SortDefault(sort = "qnAId", direction = Sort.Direction.ASC)})
                                               @ParameterObject Pageable pageable) {
        pageable = pageUtil.pageable(pageable);
        Page<QnAResponse> pagedReview = qnAService.findProductQnA(productId, pageable);

        long totalCount = pagedReview.getTotalElements();

        int page = pagedReview.getNumber();

        String successMessage = "상품에 해당하는 모든 QnA 입니다.";

        return new CustomResponseEntity<>(pagedReview.getContent(), successMessage, HttpStatus.OK, totalCount, page);
    }

    @Operation(summary = "내 QnA 조회 메서드", description = "내 QnA 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedProductListResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema")))
    })
    @GetMapping("/my_qna")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findMyQnA(
            @PageableDefault(page = 1, size = 5)
            @SortDefault.SortDefaults(
                    {@SortDefault(sort = "cartId", direction = Sort.Direction.ASC)})
            @ParameterObject Pageable pageable) {
        pageable = pageUtil.pageable(pageable);
        Page<QnAResponse> pagedQnA = qnAService.findAllMyQnA(pageable);

        long totalCount = pagedQnA.getTotalElements();

        int page = pagedQnA.getNumber();

        String successMessage = "내 모든 QnA 입니다.";

        return new CustomResponseEntity<>(pagedQnA.getContent(), successMessage, HttpStatus.OK, totalCount, page);
    }


    @Operation(summary = "QnA 삭제 메서드", description = "QnA 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/GeneralSuccessResponseSchema"))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/ForbiddenResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @DeleteMapping("delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteQnA(@RequestParam("qnAId") Long qnAId) {
        qnAService.deleteById(qnAId);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("successMessage",  qnAId + "번 QnA가 삭제되었습니다.");
        return new CustomResponseEntity<>(responseMap, "QnA 삭제 성공", HttpStatus.OK);
    }

}





















