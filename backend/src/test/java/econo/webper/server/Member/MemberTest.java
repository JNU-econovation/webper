package econo.webper.server.Member;

import econo.webper.server.component.Component;
import econo.webper.server.component.ComponentCategory;
import econo.webper.server.directory.Directory;
import econo.webper.server.directory.DirectoryCategory;
import econo.webper.server.directory.dto.DirectoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberTest {

    private  final int SECOND_INDEX = 2;
    private final int FIRST_INDEX = 1;
    Member member;

    List<Directory> directoryList;

    @BeforeEach
    public void setUp() {
        directoryList = new ArrayList<>();
        directoryList.add(mock(Directory.class));
        member = new Member(1, "jj@naver.com", "123123", "jjbae", directoryList, Collections.singletonList(MemberRole.USER));
    }

    @DisplayName("정상적인 부모 디렉토리를 가진 디렉토리 save 테스")
    @Test
    public void saveDirectoryTest() {
        Directory directory = mock(Directory.class);

        when(directory.getParentDirectory()).thenReturn(null);
        member.saveDirectory(directory);

        assertThat(member.getDirectories().get(FIRST_INDEX)).isEqualTo(directory);
    }

    @DisplayName("정상적인 부모 디렉토리를 가지지 않은 디렉토리 save 테스")
    @Test
    public void saveDirectoryWithWrongParentDirectoryTest() {
        Directory directory = mock(Directory.class);
        Directory parentDirectory = new Directory()
                .builder()
                .id(null)
                .build();

        when(directory.getParentDirectory()).thenReturn(parentDirectory);

        assertThat(member.saveDirectory(directory)).isEqualTo(null);
    }

    @DisplayName("Id로 디렉토리 검색 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void findDirectoryByIdTest(Integer argument) {
        Directory directory = Directory
                .builder()
                .id(argument)
                .build();
        member.saveDirectory(directory);

        assertThat(member.findDirectoryById(argument)).isEqualTo(directory);
    }

    @DisplayName("Id가 null값일 때 디렉토리 검색 테스트")
    @Test
    public void findDirectoryByNullTest() {
        assertThat(member.findDirectoryById(null)).isEqualTo(null);
    }

    @DisplayName("Id가 null이 아닐때 디렉토리 삭제 테스트")
    @Test
    public void deleteDirectoryTest() {
        Directory directory = Directory
                .builder()
                .id(SECOND_INDEX)
                .build();
        member.saveDirectory(directory);

        assertThat(member.findDirectoryById(SECOND_INDEX)).isNotNull();
        member.deleteDirectory(SECOND_INDEX);
        assertThat(member.findDirectoryById(SECOND_INDEX)).isNull();
    }

    @DisplayName("디렉토리 업데이트 테스트")
    @Test
    public void updateDirectoryTest() {
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setId(FIRST_INDEX);
        directoryDTO.setTitle("Changed Title");
        directoryDTO.setCategory(DirectoryCategory.WISHLIST);

        Directory directory = new Directory().builder()
                .id(FIRST_INDEX)
                .title("Title")
                .category(DirectoryCategory.BLOG)
                .build();
        member.saveDirectory(directory);

        member.updateDirectory(directoryDTO);
        Directory directoryById = member.findDirectoryById(FIRST_INDEX);

        assertThat(directoryById.getTitle()).isEqualTo("Changed Title");
        assertThat(directoryById.getCategory()).isEqualTo(DirectoryCategory.WISHLIST);
    }

    @DisplayName("컴포넌트 검색 테스트")
    @Test
    public void findComponentByIdTest() {
        Component component = new Component(1, "Component", 1, ComponentCategory.BLOG);
        Directory directory = new Directory().builder()
                .id(FIRST_INDEX)
                .title("Title")
                .category(DirectoryCategory.BLOG)
                .parentDirectory(null)
                .components(Collections.singletonList(component))
                .build();
        member.saveDirectory(directory);

        Component componentById = member.findComponentById(FIRST_INDEX);
        assertThat(componentById).isEqualTo(component);
    }

    @DisplayName("존재하지 않는 컴포넌트 검색 테스트")
    @Test
    public void findNonExistComponentTest() {
        Directory directory = new Directory().builder()
                .id(FIRST_INDEX)
                .title("Title")
                .category(DirectoryCategory.BLOG)
                .parentDirectory(null)
                .build();
        member.saveDirectory(directory);

        assertThat(member.findComponentById(SECOND_INDEX)).isEqualTo(null);
    }

    @DisplayName("멤버의 모든 디렉토리 조회 테스트")
    @Test
    public void getAllDirectoriesTest() {
        Directory directory1 = new Directory().builder()
                .id(FIRST_INDEX)
                .title("Title1")
                .build();
        Directory directory2 = new Directory().builder()
                .id(SECOND_INDEX)
                .title("Title2")
                .build();
        member.saveDirectory(directory1);
        member.saveDirectory(directory2);

        List<Directory> directories = member.getAllDirectories();
        assertThat(directories.get(FIRST_INDEX)).isEqualTo(directory1);
        assertThat(directories.get(SECOND_INDEX)).isEqualTo(directory2);

    }
}