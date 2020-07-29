package econo.webper.server.component;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import econo.webper.server.component.dto.BlogDTO;
import econo.webper.server.component.dto.PortalDTO;
import econo.webper.server.component.dto.VideoDTO;
import econo.webper.server.component.dto.WishListDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ComponentService {

    MemberService memberService;

    ComponentRepository componentRepository;

    public ComponentService(MemberService memberService, ComponentRepository componentRepository) {
        this.memberService = memberService;
        this.componentRepository = componentRepository;
    }

    public Component getLastCreatedComponent() {
        return componentRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);
    }

    public Component saveBlog(Member member, BlogDTO blogDTO) {
        Blog blog = new Blog(blogDTO.getTitle(),
                blogDTO.getDirectoryId(),
                blogDTO.getComponentCategory(),
                blogDTO.getThumbnailURL(),
                blogDTO.getDescription(),
                blogDTO.getThumbnailURL());
        return memberService.saveComponent(member, blog);
    }

    public Component savePortal(Member member, PortalDTO portalDTO) {
        Portal portal = new Portal(portalDTO.getTitle(),
                portalDTO.getDirectoryId(),
                portalDTO.getComponentCategory(),
                portalDTO.getFaviconURL(),
                portalDTO.getRedirectionLink(),
                portalDTO.getDescription());
        return memberService.saveComponent(member, portal);
    }

    public Component saveVideo(Member member, VideoDTO videoDTO) {
        Video video = new Video(videoDTO.getTitle(),
                videoDTO.getDirectoryId(),
                videoDTO.getComponentCategory(),
                videoDTO.getThumbnailURL(),
                videoDTO.getRedirectionLink());
        return memberService.saveComponent(member, video);
    }

    public Component saveWishList(Member member, WishListDTO wishListDTO) {
        WishList wishList = new WishList(wishListDTO.getTitle(),
                wishListDTO.getDirectoryId(),
                wishListDTO.getComponentCategory(),
                wishListDTO.getThumbnailURL(),
                wishListDTO.getRedirectionLink(),
                wishListDTO.getPrice(),
                wishListDTO.getDeliveryType(),
                wishListDTO.getDescription());
        return memberService.saveComponent(member, wishList);
    }

    public Component findById(Integer id) {
        return componentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
