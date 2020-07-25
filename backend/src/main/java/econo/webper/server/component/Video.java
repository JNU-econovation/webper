package econo.webper.server.component;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
public class Video extends Component {

    private String thumbnailURL;

    private String redirectionLink;

    public Video(String title, Integer directoryId, ComponentCategory componentCategory, String thumbnailURL, String redirectionLink) {
        super(title, directoryId, componentCategory);
        this.thumbnailURL = thumbnailURL;
        this.redirectionLink = redirectionLink;
    }
}
