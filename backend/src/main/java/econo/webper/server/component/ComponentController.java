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

    private ResponseEntity getResponseEntity(Component component) {
        if (component == null) {
            ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        String createdComponentJsonData;
        try {
            createdComponentJsonData= componentService.getLastCreatedComponent().objectToJson();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ExceptionMessage.JSON_PROCESSING_EXCEPTION);
        }
        return ResponseEntity.ok(createdComponentJsonData);
    }
}
