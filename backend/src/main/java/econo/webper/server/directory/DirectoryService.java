package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    private final MemberService memberService;

    private final DirectoryRepository directoryRepository;

    public DirectoryService(MemberService memberService, DirectoryRepository directoryRepository) {
        this.memberService = memberService;
        this.directoryRepository = directoryRepository;
    }

    public Directory createDirectory(Member member, CreateDirectoryDTO createDirectoryDTO) {
        return memberService.saveDirectory(member, createDirectoryDTO);
    }

    public Directory updateDirectory(Member member, DirectoryDTO directoryDTO) {
        return memberService.updateDirectory(member, directoryDTO);
    }

    public boolean deleteDirectory(Member member, Integer id) {
        return memberService.deleteDirectory(member, id);
    }

    public Directory getDirectory(Member member, Integer id) {
        return memberService.getDirectory(member, id);
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
}
