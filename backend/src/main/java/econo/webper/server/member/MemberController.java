package econo.webper.server.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.directory.DirectoryService;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.security.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private final ObjectMapper objectMapper;

    public MemberController(DirectoryService directoryService, MemberService memberService, ObjectMapper objectMapper) {
        this.directoryService = directoryService;
        this.memberService = memberService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/root-directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getRootDirectories(@AuthenticationPrincipal User user) throws JsonProcessingException {
        Member member = memberService.findMemberByEmail(user.getUsername());
        List<DirectoryDTO> directoryDtoList = directoryService.getDirectoryDTOs(member);
        if (directoryDtoList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(directoryDtoList));
    }
}
