package econo.webper.server.Member;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.Directory;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.exception.NoSuchMemberException;
import econo.webper.server.login.GoogleUserinfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(GoogleUserinfoDTO googleUserinfoDTO, List<MemberRole> memberRoles) {
        Optional<Member> savedMember = memberRepository.findByEmail(googleUserinfoDTO.getEmail());
        if (savedMember.isPresent()) {
            return savedMember.get();
        }
        Member member = Member.builder()
                .email(googleUserinfoDTO.getEmail())
                .roles(memberRoles)
                .build();
        return memberRepository.save(member);
    }

    public Member findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        return member;
    }

    public Directory saveDirectory(Member member, CreateDirectoryDTO createDirectoryDTO) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            return null;
        }
        Member savedMember = savedOptionalMember.get();
        Directory parentDirectory = savedMember.findDirectoryById(createDirectoryDTO.getParentDirectoryId());
        if (!isRightCreateDirectoryDTO(createDirectoryDTO, parentDirectory)) {
            return null;
        }
        Directory directory = Directory.builder()
                .title(createDirectoryDTO.getTitle())
                .category(createDirectoryDTO.getCategory())
                .parentDirectory(parentDirectory)
                .build();
        Directory savedDirectory = savedMember.saveDirectory(directory);
        memberRepository.save(savedMember);
        return savedDirectory;
    }

    private boolean isRightCreateDirectoryDTO(CreateDirectoryDTO createDirectoryDTO, Directory parentDirectory) {
        if (createDirectoryDTO.getParentDirectoryId() != null && parentDirectory == null) {
            return false;
        }
        return true;
    }


    public boolean deleteDirectory(Member member, DirectoryDTO directoryDTO) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            return false;
        }
        Member savedMember = savedOptionalMember.get();
        boolean isDelete = savedMember.deleteDirectory(directoryDTO);
        if (isDelete == false) {
            return false;
        }
        memberRepository.save(savedMember);
        return true;
    }

    public Directory updateDirectory(Member member, DirectoryDTO directoryDTO) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            throw new NoSuchMemberException("해당 멤버가 존재하지 않습니다.");
        }
        Member savedMember = savedOptionalMember.get();
        return savedMember.updateDirectory(directoryDTO);
    }

    public Directory getDirectory(Member member, Integer id) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            throw new NoSuchMemberException("해당 멤버가 존재하지 않습니다.");
        }
        Member savedMember = savedOptionalMember.get();
        return savedMember.findDirectoryById(id);
    }
}
