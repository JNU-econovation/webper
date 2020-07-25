package econo.webper.server.component.dto;

import econo.webper.server.component.ComponentCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListDTO {

    private String title;

    private Integer directoryId;

    private ComponentCategory category;

    private String thumbnailURL;

    private String redirectionLink;

    private int price;

    private String deliveryType;

    private String description;
}
