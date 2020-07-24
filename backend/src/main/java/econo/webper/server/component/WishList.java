package econo.webper.server.component;

import javax.persistence.Entity;

@Entity
public class WishList extends Component {

    private String thumbnailURL;

    private String redirectionLink;

    private int price;

    private String deliveryType;

    private String description;
}
