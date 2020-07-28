package econo.webper.server.directory.dto;

import econo.webper.server.directory.DirectoryCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryDTO {

    private Integer Id;

    private String title;

    private DirectoryCategory category;

    private Integer parentDirectoryId;

}

