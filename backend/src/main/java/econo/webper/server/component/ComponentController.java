package econo.webper.server.component;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberDetails;
import econo.webper.server.Member.MemberService;
import econo.webper.server.component.dto.BlogDTO;
import econo.webper.server.component.dto.PortalDTO;
import econo.webper.server.component.dto.VideoDTO;
import econo.webper.server.component.dto.WishListDTO;
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

    @PostMapping("/component/blog")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveBlog(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody BlogDTO blogDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveBlog(savedMember, blogDTO);
        if (component.getCategory() != blogDTO.getCategory()) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        return getResponseEntity(component);
    }

    @PostMapping("/component/video")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveVideo(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody VideoDTO videoDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveVideo(savedMember, videoDTO);
        if (component.getCategory() != videoDTO.getCategory()) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        return getResponseEntity(component);
    }

    @PostMapping("/component/wishlist")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity saveWishList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody WishListDTO wishListDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.saveWishList(savedMember, wishListDTO);
        if (component.getCategory() != wishListDTO.getCategory()) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        return getResponseEntity(component);
    }

    @PostMapping("/component/portal")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity savePortal(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PortalDTO portalDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Component component = componentService.savePortal(savedMember, portalDTO);
        if (component.getCategory() != portalDTO.getCategory()) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        return getResponseEntity(component);
    }

    @GetMapping("/component")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getComponent(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam Integer id) {
        Component component = componentService.findById(id);
        if (component == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_COMPONENT);
        }
        String componentJsonData;
        try {
            componentJsonData = component.objectToJson();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ExceptionMessage.JSON_PROCESSING_EXCEPTION);
        }
        return ResponseEntity.ok(componentJsonData);
    }

    private ResponseEntity getResponseEntity(Component component) {
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        String ComponentJsonData;
        try {
            ComponentJsonData = componentService.getLastCreatedComponent().objectToJson();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ExceptionMessage.JSON_PROCESSING_EXCEPTION);
        }
        return ResponseEntity.ok(ComponentJsonData);
    }
}
