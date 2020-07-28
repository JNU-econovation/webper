package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    private final MemberService memberService;

    public DirectoryService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Directory createDirectory(Member member, CreateDirectoryDTO createDirectoryDTO) {
        return memberService.saveDirectory(member, createDirectoryDTO);
    }

    public Directory updateDirectory(Member member, DirectoryDTO directoryDTO) {
        return memberService.updateDirectory(member, directoryDTO);
    }

    public boolean deleteDirectory(Member member, DirectoryDTO directoryDTO) {
        return memberService.deleteDirectory(member, directoryDTO);
    }

    public Directory getDirectory(Member member, Integer id) {
        return memberService.getDirectory(member, id);
    }

    public List<DirectoryDTO> getDirectoryDTOs(Member savedMember) {
        List<DirectoryDTO> directoryDTOs = new ArrayList<>();
        savedMember.getDirectories().forEach(directory -> directoryDTOs.add(new DirectoryDTO(directory.getId(),directory.getTitle(),directory.getCategory())));
        return directoryDTOs;
    }
}
