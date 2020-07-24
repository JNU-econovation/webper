package econo.webper.server.component;

import javax.persistence.Entity;

@Entity
public class Portal extends Component{

    private String faviconURL;

    private String redirectionLink;

    private String description;
}
