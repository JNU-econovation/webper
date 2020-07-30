package econo.webper.server.component;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberDetails;
import econo.webper.server.Member.MemberService;
import econo.webper.server.component.dto.*;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@Api(value = "Component CRUD", tags = {"Component CRUD - 담당자 : 배종진"})
@RestController
public class ComponentController {

    MemberService memberService;

    ComponentService componentService;

    public ComponentController(MemberService memberService, ComponentService componentService) {
        this.memberService = memberService;
        this.componentService = componentService;
    }

    @GetMapping("/component")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getComponent(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam Integer id) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.findById(savedMember, id);
        if (component == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PostMapping("/component/blog")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveBlog(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody BlogDTO blogDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveBlog(savedMember, blogDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/video")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveVideo(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody VideoDTO videoDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveVideo(savedMember, videoDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/wishlist")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveWishList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody WishListDTO wishListDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveWishList(savedMember, wishListDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/portal")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity savePortal(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PortalDTO portalDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.savePortal(savedMember, portalDTO);
        return getResponseEntity(component);
    }

    @PatchMapping("/component/blog")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateBlog(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody BlogUpdateDTO blogUpdateDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.updateBlog(savedMember, blogUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/portal")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updatePortal(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PortalUpdateDTO portalUpdateDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.updatePortal(savedMember, portalUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/video")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateVideo(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody VideoUpdateDTO videoUpdateDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.updateVideo(savedMember, videoUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/wishlist")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateWishList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody WishListUpdateDTO wishListUpdateDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.updateWishList(savedMember, wishListUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @DeleteMapping("/component/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity deleteComponent(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Integer id) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        boolean isDelete = componentService.deleteComponent(savedMember, id);
        if (!isDelete) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_DELETE_COMPONENT);
        }
        return ResponseEntity.ok().build();
    }

    private ResponseEntity getResponseEntity(Component component) {
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        Component lastCreatedComponent = componentService.getLastCreatedComponent();
        return ResponseEntity.ok(lastCreatedComponent);
    }

}
