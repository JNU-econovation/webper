package econo.webper.server.Member;

import econo.webper.server.directory.Directory;
import econo.webper.server.directory.DirectoryDTO;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Directory> directories = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<MemberRole> roles = new ArrayList<>();

    public Directory saveDirectory(Directory directory) {
        if (directory.getParentDirectory() == null) {
            directories.add(directory);
            return directory;
        }
        Directory parentDirectory = findDirectoryById(directory.getParentDirectory().getId());
        if (parentDirectory == null) {
            return null;
        }
        return parentDirectory.saveChildDirectory(directory);
    }

    public Directory findDirectoryById(Integer id) {
        Directory result = null;
        for (Directory directory : directories) {
            result = directory.findById(id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public boolean deleteDirectory(DirectoryDTO directoryDTO) {
        Directory directoryById = findDirectoryById(directoryDTO.getId());
        if (directoryById.getParentDirectory() == null) {
            return directories.removeIf(directory -> directory.getId() == directoryDTO.getId());
        }
        Directory parentDirectory = directoryById.getParentDirectory();
        return parentDirectory.deleteChildDirectory(directoryDTO.getId());
    }

    public Directory updateDirectory(DirectoryDTO directoryDTO) {
        Directory directoryById = findDirectoryById(directoryDTO.getId());
        if (directoryById == null) {
            return null;
        }
        return directoryById.updateDirectory(directoryDTO.getTitle(), directoryDTO.getCategory());

    }
}

