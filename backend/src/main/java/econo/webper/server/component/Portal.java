package econo.webper.server.component;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
public class Portal extends Component {

    private String faviconURL;

    private String redirectionLink;

    private String description;

    public Portal(String title, Integer directoryId, ComponentCategory componentCategory, String faviconURL, String redirectionLink, String description) {
        super(title, directoryId, componentCategory);
        this.faviconURL = faviconURL;
        this.redirectionLink = redirectionLink;
        this.description = description;
    }
}
