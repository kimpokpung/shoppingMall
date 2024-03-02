package home.project.controller;


import home.project.domain.Member;
import home.project.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "회원", description = "회원관련 API 입니다")
@RequestMapping(path = "/api/users")
@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
    @PostMapping("join")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
//            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
//    })
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        memberService.join(member);
        return ResponseEntity.ok(member);
    }

    @PostMapping("login")
    public ResponseEntity<Member> login(@RequestBody Member member) {
        memberService.login(member);
        return ResponseEntity.ok(member);
    }

    @GetMapping("findMember")
    public ResponseEntity<Optional<Member>> findMember(@RequestParam("memberId") Long memberId){
        Optional<Member> member =  memberService.findById(memberId);
        return ResponseEntity.ok(member);
    }

    @GetMapping("findAllMember")
    public ResponseEntity<List<Member>> findAllMember() {
        List<Member> memberList = memberService.findAll();
        return ResponseEntity.ok(memberList);
    }

}