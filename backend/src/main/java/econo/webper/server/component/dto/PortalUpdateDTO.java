package econo.webper.server.component.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PortalUpdateDTO {

    private Integer id;

    private String title;

    private String faviconURL;

    private String redirectionLink;

    private String description;
}
