package econo.webper.server.component;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity @NoArgsConstructor
@Getter
public class WishList extends Component {

    private String thumbnailURL;

    private String redirectionLink;

    private int price;

    private String deliveryInfo;

    private String description;

    private String shoppingMall;

    public WishList(String title, Integer directoryId, ComponentCategory category, String thumbnailURL, String redirectionLink, int price, String deliveryInfo, String description, String shoppingmall) {
        super(title, directoryId, category);
        this.thumbnailURL = thumbnailURL;
        this.redirectionLink = redirectionLink;
        this.price = price;
        this.deliveryInfo = deliveryInfo;
        this.description = description;
        this.shoppingMall = shoppingmall;
    }
}
