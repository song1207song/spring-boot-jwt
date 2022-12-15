package me.songha.tutorial.repository;

import me.songha.tutorial.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByUsername(String username);
}
