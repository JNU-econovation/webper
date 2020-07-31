package econo.webper.server.directory.dto;

import econo.webper.server.directory.DirectoryCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryResponseDTO {

    private Integer Id;

    private String title;

    private DirectoryCategory category;

    private Integer parentDirectoryId;

    private List<DirectoryDTO> childDirectories;
}
