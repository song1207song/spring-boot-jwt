package me.songha.tutorial.service;

import lombok.RequiredArgsConstructor;
import me.songha.tutorial.dto.MemberDto;
import me.songha.tutorial.entity.Authority;
import me.songha.tutorial.entity.Member;
import me.songha.tutorial.entity.MemberAuthority;
import me.songha.tutorial.exception.DuplicateMemberException;
import me.songha.tutorial.exception.NotFoundMemberException;
import me.songha.tutorial.repository.MemberAuthorityRepository;
import me.songha.tutorial.repository.MemberRepository;
import me.songha.tutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {
    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final PasswordEncoder passwordEncoder;
    private static final byte STATUS_ACTIVATED = 1;

    @Transactional
    public MemberDto signup(MemberDto userDto) {
        if (memberRepository.findFirstWithMemberAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
//                .memberAuthorities(Collections.singleton(memberAuthority))
//                .authorities(Collections.singleton(authority))
                .status(STATUS_ACTIVATED)
                .build();

        Member savedMember = memberRepository.save(member);

        Authority authority = Authority.builder().authorityName("ROLE_USER").build();
        MemberAuthority memberAuthority = MemberAuthority.builder()
                .memberId(savedMember.getId())
                .authorityName(authority.getAuthorityName())
//                .authority(authority)
//                .member(savedMember)
                .build();
        MemberAuthority savedMemberAuthority = memberAuthorityRepository.save(memberAuthority);

        member.setMemberAuthorities(Collections.singleton(savedMemberAuthority));

        return MemberDto.from(member);
    }

    @Transactional(readOnly = true)
    public MemberDto getUserWithAuthorities(String username) {
        return MemberDto.from(memberRepository.findFirstWithMemberAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public MemberDto getMyUserWithAuthorities() {
        return MemberDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findFirstWithMemberAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}
