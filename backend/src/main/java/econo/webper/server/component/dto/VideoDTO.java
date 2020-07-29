package econo.webper.server.component.dto;

import econo.webper.server.component.ComponentCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {

    private String title;

    private Integer directoryId;

    private ComponentCategory componentCategory;

    private String thumbnailURL;

    private String redirectionLink;

}
