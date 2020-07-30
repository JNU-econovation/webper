package econo.webper.server.component;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import econo.webper.server.component.dto.*;
import econo.webper.server.directory.Directory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ComponentService {

    MemberService memberService;

    ComponentRepository componentRepository;

    public ComponentService(MemberService memberService, ComponentRepository componentRepository) {
        this.memberService = memberService;
        this.componentRepository = componentRepository;
    }

    public Component getLastCreatedComponent() {
        return componentRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
    }

    public Component saveBlog(Member member, BlogDTO blogDTO) {
        Blog blog = new Blog(blogDTO.getTitle(),
                blogDTO.getDirectoryId(),
                blogDTO.getCategory(),
                blogDTO.getThumbnailURL(),
                blogDTO.getDescription(),
                blogDTO.getThumbnailURL());
        return memberService.saveComponent(member, blog);
    }

    public Component savePortal(Member member, PortalDTO portalDTO) {
        Portal portal = new Portal(portalDTO.getTitle(),
                portalDTO.getDirectoryId(),
                portalDTO.getCategory(),
                portalDTO.getFaviconURL(),
                portalDTO.getRedirectionLink(),
                portalDTO.getDescription());
        return memberService.saveComponent(member, portal);
    }

    public Component saveVideo(Member member, VideoDTO videoDTO) {
        Video video = new Video(videoDTO.getTitle(),
                videoDTO.getDirectoryId(),
                videoDTO.getCategory(),
                videoDTO.getThumbnailURL(),
                videoDTO.getRedirectionLink());
        return memberService.saveComponent(member, video);
    }

    public Component saveWishList(Member member, WishListDTO wishListDTO) {
        WishList wishList = new WishList(wishListDTO.getTitle(),
                wishListDTO.getDirectoryId(),
                wishListDTO.getCategory(),
                wishListDTO.getThumbnailURL(),
                wishListDTO.getRedirectionLink(),
                wishListDTO.getPrice(),
                wishListDTO.getDeliveryInfo(),
                wishListDTO.getDescription(),
                wishListDTO.getShoppingMall());
        return memberService.saveComponent(member, wishList);
    }

    public Component findById(Member member, Integer id) {
        return member.findComponentById(id);
    }

    public Component updateBlog(Member member, BlogUpdateDTO blogUpdateDTO) {
        Component component = findById(member, blogUpdateDTO.getId());
        if (component == null) {
            return null;
        }
        if (!component.update(blogUpdateDTO)) {
            return null;
        }
        return component;
    }

    public Component updatePortal(Member member, PortalUpdateDTO portalUpdateDTO) {
        Component component = findById(member, portalUpdateDTO.getId());
        if (component == null) {
            return null;
        }
        if (!component.update(portalUpdateDTO)) {
            return null;
        }
        return component;
    }

    public Component updateVideo(Member member, VideoUpdateDTO videoUpdateDTO) {
        Component component = findById(member, videoUpdateDTO.getId());
        if (component == null) {
            return null;
        }
        if (!component.update(videoUpdateDTO)) {
            return null;
        }
        return component;
    }
    public Component updateWishList(Member member, WishListUpdateDTO wishListUpdateDTO) {
        Component component = findById(member, wishListUpdateDTO.getId());
        if (component == null) {
            return null;
        }
        if (!component.update(wishListUpdateDTO)) {
            return null;
        }
        return component;
    }

    public boolean deleteComponent(Member member, Integer id) {
        Component component = findById(member, id);
        if (component == null) {
            return false;
        }
        Directory directory = member.findDirectoryById(component.getDirectoryId());
        if (directory == null) {
            return false;
        }
        boolean isDeleteComponent = directory.deleteComponent(id);
        if (isDeleteComponent == true) {
            memberService.saveMember(member);
            return true;
        }
        return false;
    }
}
