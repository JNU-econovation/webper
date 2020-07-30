package econo.webper.server.component.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoUpdateDTO {

    private Integer id;

    private String title;

    private String thumbnailURL;

    private String redirectionLink;
}
