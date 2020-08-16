package econo.webper.server.member;

import econo.webper.server.component.Component;
import econo.webper.server.directory.Directory;
import econo.webper.server.directory.dto.DirectoryDTO;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
        if (id == null) {
            return null;
        }
        for (Directory directory : directories) {
            result = directory.findById(id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public boolean deleteDirectory(Integer id) {
        Directory directoryById = findDirectoryById(id);
        if (directoryById.getParentDirectory() == null) {
            return directories.removeIf(directory -> directory.getId() == id);
        }
        Directory parentDirectory = directoryById.getParentDirectory();
        return parentDirectory.deleteChildDirectory(id);
    }

    public Directory updateDirectory(DirectoryDTO directoryDTO) {
        Directory directoryById = findDirectoryById(directoryDTO.getId());
        if (directoryById == null) {
            return null;
        }
        return directoryById.updateDirectory(directoryDTO.getTitle(), directoryDTO.getCategory());

    }

    public Component findComponentById(Integer id) {
        Component result = null;
        for (Directory directory : directories) {
            result = directory.findComponentById(id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public List<Directory> getAllDirectories() {
        List<Directory> directories = new ArrayList<>();
        directories.addAll(this.directories);
        for (int index = 0; index < directories.size(); index++) {
            directories.addAll(directories.get(index).getAllChildDirectories());
        }
        return directories;
    }
}

