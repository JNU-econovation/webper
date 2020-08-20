package econo.webper.server.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.component.Component;
import econo.webper.server.directory.Directory;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.exception.NoSuchDirectoryException;
import econo.webper.server.exception.NoSuchMemberException;
import econo.webper.server.exception.NotSaveDirectoryException;
import econo.webper.server.login.dto.GoogleUserinfoDTO;
import econo.webper.server.utils.ExceptionMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    ObjectMapper objectMapper;

    public MemberService(MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
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
        Directory parentDirectory = member.findDirectoryById(createDirectoryDTO.getParentDirectoryId());
        if (!isRightCreateDirectoryDTO(createDirectoryDTO, parentDirectory)) {
            throw new NotSaveDirectoryException(ExceptionMessage.NOT_CREATE_COMPONENTS);
        }
        Directory directory = Directory.builder()
                .title(createDirectoryDTO.getTitle())
                .category(createDirectoryDTO.getCategory())
                .parentDirectory(parentDirectory)
                .build();
        Directory savedDirectory = member.saveDirectory(directory);
        memberRepository.save(member);
        return savedDirectory;
    }

    private boolean isRightCreateDirectoryDTO(CreateDirectoryDTO createDirectoryDTO, Directory parentDirectory) {
        if (createDirectoryDTO.getParentDirectoryId() != null && parentDirectory == null) {
            return false;
        }
        return true;
    }


    public boolean deleteDirectory(Member member, Integer directoryId) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            return false;
        }
        Member savedMember = savedOptionalMember.get();
        boolean isDelete = savedMember.deleteDirectory(directoryId);
        if (isDelete == false) {
            return false;
        }
        memberRepository.save(savedMember);
        return true;
    }

    public Directory updateDirectory(Member member, DirectoryDTO directoryDTO) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            throw new NoSuchMemberException(ExceptionMessage.NON_MEMBER_EXIST);
        }
        Member savedMember = savedOptionalMember.get();
        return savedMember.updateDirectory(directoryDTO);
    }

    public Directory findDirectoryById(Member member, Integer id) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            throw new NoSuchMemberException(ExceptionMessage.NON_MEMBER_EXIST);
        }
        Member savedMember = savedOptionalMember.get();
        return savedMember.findDirectoryById(id);
    }

    public Component saveComponent(Member member, Component component) {
        Optional<Member> savedOptionalMember = memberRepository.findById(member.getId());
        if (!savedOptionalMember.isPresent()) {
            throw new NoSuchMemberException(ExceptionMessage.NON_MEMBER_EXIST);
        }
        Member savedMember = savedOptionalMember.get();
        Directory directoryById = savedMember.findDirectoryById(component.getDirectoryId());
        if (directoryById == null) {
            throw new NoSuchDirectoryException(ExceptionMessage.NON_DIRECTORY_EXIST);
        }
        String componentCategory = component.getCategory().name();
        String directoryCategory = directoryById.getCategory().name();
        if (directoryCategory != componentCategory) {
            return null;
        }
        boolean isSave = directoryById.saveComponent(component);
        if (isSave == false) {
            return null;
        }
        memberRepository.save(savedMember);
        return component;
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
