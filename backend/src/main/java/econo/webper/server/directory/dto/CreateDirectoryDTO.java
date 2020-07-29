package econo.webper.server.directory.dto;

import econo.webper.server.directory.DirectoryCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDirectoryDTO {

    private Integer parentDirectoryId;

    private String title;

    private DirectoryCategory category;

}