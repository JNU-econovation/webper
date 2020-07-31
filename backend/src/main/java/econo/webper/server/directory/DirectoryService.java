package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.directory.dto.MainPageDirectoryDTO;
import econo.webper.server.utils.RandomGenerator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    private final MemberService memberService;

    private final DirectoryRepository directoryRepository;

    private final RandomGenerator randomGenerator;

    public DirectoryService(MemberService memberService, DirectoryRepository directoryRepository, RandomGenerator randomGenerator) {
        this.memberService = memberService;
        this.directoryRepository = directoryRepository;
        this.randomGenerator = randomGenerator;
    }

    public Directory createDirectory(Member member, CreateDirectoryDTO createDirectoryDTO) {
        return memberService.saveDirectory(member, createDirectoryDTO);
    }

    public Directory updateDirectory(Member member, DirectoryDTO directoryDTO) {
        Directory directory = memberService.updateDirectory(member, directoryDTO);
        if (directory == null) {
            return null;
        }
        Directory updatedDirectory = directoryRepository.save(directory);
        return updatedDirectory;
    }

    public boolean deleteDirectory(Member member, Integer id) {
        return memberService.deleteDirectory(member, id);
    }

    public Directory findDirectoryById(Member member, Integer id) {
        return memberService.findDirectoryById(member, id);
    }

    public List<DirectoryDTO> getDirectoryDTOs(Member savedMember) {
        List<DirectoryDTO> directoryDTOs = new ArrayList<>();
        Integer parentDirectoryId = null;
        for (Directory directory : savedMember.getDirectories()) {
            if (directory.getParentDirectory() != null) {
                parentDirectoryId = directory.getParentDirectory().getId();
            }
            directoryDTOs.add(new DirectoryDTO(directory.getId(), directory.getTitle(), directory.getCategory(), parentDirectoryId));
        }
        return directoryDTOs;
    }

    public Directory getLastIdDirectory() {
        return directoryRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);
    }

    public DirectoryDTO createDirectoryDTO(Directory directory) {
        Integer parentDirectoryId = null;
        if (directory.getParentDirectory() != null) {
            parentDirectoryId = directory.getParentDirectory().getId();
        }
        return new DirectoryDTO(directory.getId(), directory.getTitle(),directory.getCategory(), parentDirectoryId);
    }

    public Directory getRandomDirectory(Member member) {
        List<Directory> directories = member.getAllDirectories();
        if (directories.size() == 0) {
            return null;
        }
        int randomInt = randomGenerator.generateRandomInt(directories.size());
        return directories.get(randomInt);
    }

    public MainPageDirectoryDTO createMainPageDirectoryDTO(Directory directory) {
        MainPageDirectoryDTO mainPageDirectoryDTO
                = new MainPageDirectoryDTO(directory.getId(),directory.getTitle(),directory.getCategory(),directory.getComponents());
        return mainPageDirectoryDTO;
    }
}
