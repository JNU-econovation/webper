package econo.webper.server.directory;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import econo.webper.server.component.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Directory {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private DirectoryCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Directory parentDirectory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Directory> childDirectories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Component> components = new ArrayList<>();

    public Directory saveChildDirectory(Directory directory) {
        childDirectories.add(directory);
        return directory;
    }

    public Directory findById(int id) {
        if (this.id == id) {
            return this;
        }
        Directory result = null;
        for (Directory childDirectory : childDirectories) {
            result = childDirectory.findById(id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }


}
