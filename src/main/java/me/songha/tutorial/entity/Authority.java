package me.songha.tutorial.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "NAME", length = 50)
    private String authorityName;

    @OneToMany(mappedBy = "authority")
    private Set<MemberAuthority> memberAuthorities;
}
