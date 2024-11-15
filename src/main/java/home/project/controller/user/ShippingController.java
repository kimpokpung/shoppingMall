package home.project.controller.user;

import home.project.dto.responseDTO.ShippingResponse;
import home.project.response.CustomResponseEntity;
import home.project.service.order.ShippingService;
import home.project.service.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배송", description = "배송관련 API입니다")
@RequestMapping("/api/shipping")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/InternalServerErrorResponseSchema")))
})
@RequiredArgsConstructor
@RestController
public class ShippingController {

    private final ShippingService shippingService;
    private final PageUtil pageUtil;

    @Operation(summary = "id로 배송 조회 메서드", description = "id로 배송 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/ProductResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @GetMapping("/shipping")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findShippingById(@RequestParam("shippingId") Long shippingId) {
        ShippingResponse shippingResponse = shippingService.findByIdReturnShippingResponse(shippingId);
        String successMessage = shippingId + "에 해당하는 배송 입니다.";
        return new CustomResponseEntity<>(shippingResponse, successMessage, HttpStatus.OK);
    }

//    @Operation(summary = "내 배송 조회 메서드", description = "내 배송 조회 메서드입니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation",
//                    content = @Content(schema = @Schema(ref = "#/components/schemas/ProductResponseSchema"))),
//            @ApiResponse(responseCode = "404", description = "Resource not found",
//                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
//    })
//    @GetMapping("/my_shipping")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<?> findShippingById(@RequestParam("shippingId") Long shippingId, @PageableDefault(page = 1, size = 5)
//    @SortDefault.SortDefaults({
//            @SortDefault(sort = "brand", direction = Sort.Direction.ASC)
//    }) @ParameterObject Pageable pageable) {
//
//        pageable = pageUtil.pageable(pageable);
//        Page<ShippingResponse> pagedShippingResponse = shippingService.findByMemberIdReturnShippingResponse(shippingId, pageable);
//        String successMessage = shippingId + "에 해당하는 배송 입니다.";
//        long totalCount = pagedShippingResponse.getTotalElements();
//        int page = pagedShippingResponse.getNumber();
//        return new CustomResponseEntity<>(pagedShippingResponse.getContent(), successMessage, HttpStatus.OK, totalCount, page);
//    }





}
