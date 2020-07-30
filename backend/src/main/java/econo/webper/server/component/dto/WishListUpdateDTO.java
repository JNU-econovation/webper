package econo.webper.server.component.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListUpdateDTO {

    private Integer id;

    private String title;

    private String thumbnailURL;

    private String redirectionLink;

    private String price;

    private String deliveryInfo;

    private String description;

    private String shoppingMall;
}
