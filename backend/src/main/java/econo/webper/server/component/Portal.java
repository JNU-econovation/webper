package econo.webper.server.component;

import econo.webper.server.component.dto.BlogUpdateDTO;
import econo.webper.server.component.dto.PortalUpdateDTO;
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

    public Portal(String title, Integer directoryId, ComponentCategory category, String faviconURL, String redirectionLink, String description) {
        super(title, directoryId, category);
        this.faviconURL = faviconURL;
        this.redirectionLink = redirectionLink;
        this.description = description;
    }

    @Override
    public boolean update(Object updateDTO) {
        PortalUpdateDTO portalUpdateDTO = (PortalUpdateDTO) updateDTO;
        super.updateTitle(portalUpdateDTO.getTitle());
        this.faviconURL = portalUpdateDTO.getFaviconURL();
        this.description = portalUpdateDTO.getDescription();
        this.redirectionLink = portalUpdateDTO.getRedirectionLink();
        return true;
    }
}
