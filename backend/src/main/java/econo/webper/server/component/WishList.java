package econo.webper.server.component;

import econo.webper.server.component.dto.VideoUpdateDTO;
import econo.webper.server.component.dto.WishListUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity @NoArgsConstructor
@Getter
public class WishList extends Component {

    private String thumbnailURL;

    private String redirectionLink;

    private String price;

    private String deliveryInfo;

    private String description;

    private String shoppingMall;

    public WishList(String title, Integer directoryId, ComponentCategory category, String thumbnailURL, String redirectionLink, String price, String deliveryInfo, String description, String shoppingmall) {
        super(title, directoryId, category);
        this.thumbnailURL = thumbnailURL;
        this.redirectionLink = redirectionLink;
        this.price = price;
        this.deliveryInfo = deliveryInfo;
        this.description = description;
        this.shoppingMall = shoppingmall;
    }

    @Override
    public boolean update(Object updateDTO) {
        WishListUpdateDTO wishListUpdateDTO = (WishListUpdateDTO) updateDTO;
        super.updateTitle(wishListUpdateDTO.getTitle());
        this.thumbnailURL = wishListUpdateDTO.getThumbnailURL();
        this.redirectionLink = wishListUpdateDTO.getRedirectionLink();
        this.price = wishListUpdateDTO.getPrice();
        this.deliveryInfo = wishListUpdateDTO.getDeliveryInfo();
        this.description = wishListUpdateDTO.getDescription();
        this.shoppingMall = wishListUpdateDTO.getShoppingMall();
        return true;
    }
}
