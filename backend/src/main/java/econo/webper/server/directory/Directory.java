package econo.webper.server.directory;

import econo.webper.server.component.Component;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@Entity
public class Directory {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private DirectoryCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Directory SuperDirectory;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Directory> subDirectories;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Component> components;

}
