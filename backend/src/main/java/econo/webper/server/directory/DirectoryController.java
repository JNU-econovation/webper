package econo.webper.server.directory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberDetails;
import econo.webper.server.Member.MemberRepository;
import econo.webper.server.Member.MemberService;
import econo.webper.server.component.Component;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Api(value = "Directory CRUD", tags = {"Directory CRUD - 담당자 : 배종진"})
@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    private final MemberService memberService;

    private final ObjectMapper objectMapper;

    public DirectoryController(DirectoryService directoryService, MemberService memberService, MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.directoryService = directoryService;
        this.memberService = memberService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam Integer id) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.getDirectory(savedMember, id);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_DIRECTORY);
        }
        DirectoryDTO directoryDTO = directoryService.createDirectoryDTO(directory);
        return ResponseEntity.ok(directoryDTO);
    }

    @PostMapping("/directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity createDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody CreateDirectoryDTO createDirectoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.createDirectory(savedMember, createDirectoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_DIRECTORY);
        }
        Directory createdDirectory = directoryService.getLastIdDirectory();
        DirectoryDTO directoryDTO = directoryService.createDirectoryDTO(createdDirectory);
        return ResponseEntity.ok(directoryDTO);
    }

    @PatchMapping("/directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody DirectoryDTO directoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.updateDirectory(savedMember, directoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_DIRECTORY);
        }
        return ResponseEntity.ok(directory);
    }

    @DeleteMapping("/directory/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity deleteDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Integer id) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        boolean isDelete = directoryService.deleteDirectory(savedMember, id);
        if (isDelete == false) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_DELETE_DIRECTORY);
        }
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping("/directory/{id}/components")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getDirectoryComponents(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Integer id){
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.getDirectory(savedMember, id);
        if (directory == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_DIRECTORY);
        }
        String componentsJsonData;
        try {
            componentsJsonData = objectMapper.writeValueAsString(directory.getComponents());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ExceptionMessage.JSON_PROCESSING_EXCEPTION);
        }
        return ResponseEntity.ok(componentsJsonData);
    }

}


