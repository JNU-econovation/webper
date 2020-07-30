package econo.webper.server.component;

import econo.webper.server.component.dto.VideoUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
public class Video extends Component {

    private String thumbnailURL;

    private String redirectionLink;

    public Video(String title, Integer directoryId, ComponentCategory category, String thumbnailURL, String redirectionLink) {
        super(title, directoryId, category);
        this.thumbnailURL = thumbnailURL;
        this.redirectionLink = redirectionLink;
    }

    @Override
    public boolean update(Object updateDTO) {
        VideoUpdateDTO videoUpdateDTO = (VideoUpdateDTO) updateDTO;
        super.updateTitle(videoUpdateDTO.getTitle());
        this.thumbnailURL = videoUpdateDTO.getThumbnailURL();
        this.redirectionLink = videoUpdateDTO.getRedirectionLink();
        return true;
    }
}
