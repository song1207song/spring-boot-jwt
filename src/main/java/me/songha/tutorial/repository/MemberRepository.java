package me.songha.tutorial.repository;

import me.songha.tutorial.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // Eager 조회로 Member 를 조회할 때 AUTHORITY 정보도 함께 조회한다.
    @EntityGraph(attributePaths = "memberAuthorities")
    Optional<Member> findFirstWithMemberAuthoritiesByUsername(String username);

//    @Query("select m from Member m join fetch m.memberAuthorities where m.username = :username")
//    Optional<Member> findByUsernameWithMemberAuthorities(String username);
}
