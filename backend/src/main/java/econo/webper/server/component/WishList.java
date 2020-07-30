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
        if (wishListUpdateDTO.getThumbnailURL() != null) {
            this.thumbnailURL = wishListUpdateDTO.getThumbnailURL();
        }
        if (wishListUpdateDTO.getRedirectionLink() != null) {
            this.redirectionLink = wishListUpdateDTO.getRedirectionLink();
        }
        if (wishListUpdateDTO.getPrice() != null) {
            this.price = wishListUpdateDTO.getPrice();
        }
        if (wishListUpdateDTO.getDeliveryInfo() != null) {
            this.deliveryInfo = wishListUpdateDTO.getDeliveryInfo();
        }
        if (wishListUpdateDTO.getDescription() != null) {
            this.description = wishListUpdateDTO.getDescription();
        }
        if (wishListUpdateDTO.getShoppingMall() != null) {
            this.shoppingMall = wishListUpdateDTO.getShoppingMall();
        }
        return true;
    }
}
