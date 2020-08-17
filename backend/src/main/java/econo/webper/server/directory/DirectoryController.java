package econo.webper.server.directory;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.component.Component;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.directory.dto.DirectoryResponseDTO;
import econo.webper.server.directory.dto.MainPageDirectoryDTO;
import econo.webper.server.member.Member;
import econo.webper.server.member.MemberRepository;
import econo.webper.server.member.MemberService;
import econo.webper.server.security.User;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity getDirectory(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Directory directory = directoryService.findDirectoryById(member, id);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_DIRECTORY);
        }
        DirectoryResponseDTO directoryResponseDTO = directoryService.createDirectoryResponseDTO(directory);
        return ResponseEntity.ok(directoryResponseDTO);
    }

    @PostMapping("/directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity createDirectory(@AuthenticationPrincipal User user, @RequestBody CreateDirectoryDTO createDirectoryDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Directory directory = directoryService.createDirectory(member, createDirectoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_DIRECTORY);
        }
        Directory createdDirectory = directoryService.getLastIdDirectory();
        DirectoryDTO directoryDTO = directoryService.createDirectoryDTO(createdDirectory);
        return ResponseEntity.ok(directoryDTO);
    }

    @PatchMapping("/directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateDirectory(@AuthenticationPrincipal User user, @RequestBody DirectoryDTO directoryDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Directory directory = directoryService.updateDirectory(member, directoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_DIRECTORY);
        }
        return ResponseEntity.ok(directory);
    }

    @DeleteMapping("/directory/{directoryId}")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity deleteDirectory(@AuthenticationPrincipal User user, @PathVariable Integer directoryId) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        boolean isDelete = directoryService.deleteDirectory(member, directoryId);
        if (isDelete == false) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_DELETE_DIRECTORY);
        }
        return ResponseEntity.ok(member);
    }

    @GetMapping("/directory/{id}/components")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getDirectoryComponents(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Directory directory = directoryService.findDirectoryById(member, id);
        if (directory == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_DIRECTORY);
        }
        List<Component> components = directory.getComponents();
        return ResponseEntity.ok(components);
    }

    @GetMapping("/directory/random")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getRandomDirectory(@AuthenticationPrincipal User user) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Directory directory = directoryService.getRandomDirectory(member);
        if (directory == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_RANDOM_DIRECTORY);
        }
        MainPageDirectoryDTO mainPageDirectoryDTO = directoryService.createMainPageDirectoryDTO(directory);
        return ResponseEntity.ok(mainPageDirectoryDTO);
    }

}


