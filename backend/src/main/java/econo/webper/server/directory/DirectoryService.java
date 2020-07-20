package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberService;
import org.springframework.stereotype.Service;

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

    public Directory getDirectory(Member member, DirectoryDTO directoryDTO) {
        return memberService.getDirectory(member, directoryDTO);
    }
}
