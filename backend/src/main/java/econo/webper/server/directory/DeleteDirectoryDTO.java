package econo.webper.server.directory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteDirectoryDTO {

    private Integer deleteDirectoryId;

    private String title;

    private DirectoryCategory category;

}
