package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberDetails;
import econo.webper.server.Member.MemberRepository;
import econo.webper.server.Member.MemberService;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "Directory CRUD", tags = {"Directory CRUD - 담당자 : 배종진"})
@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    private final MemberService memberService;


    public DirectoryController(DirectoryService directoryService, MemberService memberService, MemberRepository memberRepository) {
        this.directoryService = directoryService;
        this.memberService = memberService;
    }

    @PostMapping("/Directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity createDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody CreateDirectoryDTO createDirectoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.createDirectory(savedMember, createDirectoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_DIRECTORY);
        }
        return ResponseEntity.ok(savedMember);
    }

}
