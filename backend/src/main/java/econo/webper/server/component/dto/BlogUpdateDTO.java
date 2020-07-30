package econo.webper.server.component.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogUpdateDTO {

    private Integer id;

    private String title;

    private String thumbnailURL;

    private String description;

    private String redirectionLink;
}
