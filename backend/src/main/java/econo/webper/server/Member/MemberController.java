package econo.webper.server.Member;

import econo.webper.server.directory.DirectoryService;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Member", tags = {"Member - 담당자 : 배종진"})
@RestController
public class MemberController {

    private final DirectoryService directoryService;

    private final MemberService memberService;

    public MemberController(DirectoryService directoryService, MemberService memberService) {
        this.directoryService = directoryService;
        this.memberService = memberService;
    }

    @GetMapping("/root-directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getRootDirectories(@AuthenticationPrincipal MemberDetails memberDetails) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        List<DirectoryDTO> directoryDTOs = directoryService.getDirectoryDTOs(savedMember);
        if (directoryDTOs.size() == 0) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_ROOT_DIRECTORY);
        }
        return ResponseEntity.ok(directoryDTOs);
    }
}
