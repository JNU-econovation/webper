package econo.webper.server.component;

import javax.persistence.Entity;

@Entity
public class Blog extends Component {

    private String thumbnailURL;

    private String description;

    private String redirectionLink;

}
