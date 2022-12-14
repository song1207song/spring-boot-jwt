package me.songha.tutorial.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

   @Id
   @Column(name = "ID")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "USERNAME", length = 50, unique = true)
   private String username;

   @Column(name = "PASSWORD", length = 100)
   private String password;

   @Column(name = "NICKNAME", length = 50)
   private String nickname;

   @Column(name = "STATUS")
   private int status;

   @Column(name = "INSERT_DATE")
   private ZonedDateTime insertDate;

   @Column(name = "UPDATE_DATE")
   private ZonedDateTime updateDate;

   @ManyToMany
   @JoinTable(
      name = "USER_AUTHORITY",
      joinColumns = {@JoinColumn(name = "MEMBER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
   private Set<Authority> authorities;
}
