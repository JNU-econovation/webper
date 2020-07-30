package econo.webper.server.component;


import econo.webper.server.component.dto.BlogUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
public class Blog extends Component {

    private String thumbnailURL;

    private String description;

    private String redirectionLink;

    public Blog(String title, Integer directoryId, ComponentCategory category, String thumbnailURL, String description, String redirectionLink) {
        super(title, directoryId, category);
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.redirectionLink = redirectionLink;
    }

    @Override
    public boolean update(Object updateDTO) {
        BlogUpdateDTO blogUpdateDTO = (BlogUpdateDTO) updateDTO;
        super.updateTitle(blogUpdateDTO.getTitle());
        if (blogUpdateDTO.getThumbnailURL() != null) {
            this.thumbnailURL = blogUpdateDTO.getThumbnailURL();
        }
        if (blogUpdateDTO.getDescription() != null) {
            this.description = blogUpdateDTO.getDescription();
        }
        if (blogUpdateDTO.getRedirectionLink() != null) {
            this.redirectionLink = blogUpdateDTO.getRedirectionLink();
        }
        return true;
    }

}
