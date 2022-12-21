package me.songha.tutorial.service;

import me.songha.tutorial.entity.Member;
import me.songha.tutorial.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository userRepository) {
        this.memberRepository = userRepository;
    }

    /**
     * @Description :: DB 조회를 통해 가져온 권한 정보를
     * org.springframework.security.core.userdetails.UserDetails 형식으로 리턴한다.
     */
    @Override
    @Transactional
    public User loadUserByUsername(final String username) {
        return memberRepository.findFirstWithMemberAuthoritiesByUsername(username)
                .map(member -> createUser(username, member))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> cannot found."));
    }

    private User createUser(String username, Member member) {
        if (member.getStatus() != 1) {
            throw new RuntimeException(username + " -> is disabled.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getMemberAuthorities().stream()
                .map(memberAuthority -> new SimpleGrantedAuthority(memberAuthority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
    }
}
