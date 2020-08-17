package econo.webper.server.component;

import econo.webper.server.member.Member;
import econo.webper.server.security.User;
import econo.webper.server.member.MemberService;
import econo.webper.server.component.dto.*;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getComponent(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.findById(member, id);
        if (component == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PostMapping("/component/blog")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveBlog(@AuthenticationPrincipal User user, @RequestBody BlogDTO blogDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.saveBlog(member, blogDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/video")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveVideo(@AuthenticationPrincipal User user, @RequestBody VideoDTO videoDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.saveVideo(member, videoDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/wishlist")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveWishList(@AuthenticationPrincipal User user, @RequestBody WishListDTO wishListDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.saveWishList(member, wishListDTO);
        return getResponseEntity(component);
    }

    @PostMapping("/component/portal")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity savePortal(@AuthenticationPrincipal User user, @RequestBody PortalDTO portalDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.savePortal(member, portalDTO);
        return getResponseEntity(component);
    }

    @PatchMapping("/component/blog")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateBlog(@AuthenticationPrincipal User user, @RequestBody BlogUpdateDTO blogUpdateDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.updateBlog(member, blogUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/portal")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updatePortal(@AuthenticationPrincipal User user, @RequestBody PortalUpdateDTO portalUpdateDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.updatePortal(member, portalUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/video")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateVideo(@AuthenticationPrincipal User user, @RequestBody VideoUpdateDTO videoUpdateDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.updateVideo(member, videoUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @PatchMapping("/component/wishlist")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateWishList(@AuthenticationPrincipal User user, @RequestBody WishListUpdateDTO wishListUpdateDTO) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        Component component = componentService.updateWishList(member, wishListUpdateDTO);
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_COMPONENT);
        }
        return ResponseEntity.ok(component);
    }

    @DeleteMapping("/component/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity deleteComponent(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        boolean isDelete = componentService.deleteComponent(member, id);
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
