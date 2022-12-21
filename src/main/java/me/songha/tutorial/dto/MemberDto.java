package me.songha.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.songha.tutorial.entity.Member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

   @NotNull
   @Size(min = 3, max = 50)
   private String username;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   @NotNull
   @Size(min = 3, max = 100)
   private String password;

   @NotNull
   @Size(min = 1, max = 50)
   private String nickname;

//   private Set<AuthorityDto> authorityDtoSet;

   public static MemberDto from(Member member) {
      if(member == null) return null;

      return MemberDto.builder()
              .username(member.getUsername())
              .nickname(member.getNickname())
//              .authorityDtoSet(member.getMemberAuthorities().stream()
//                      .map(memberAuthority ->
//                              AuthorityDto.builder().authorityName(memberAuthority.getAuthority().getAuthorityName()).build())
//                      .collect(Collectors.toSet()))

//              .authorityDtoSet(member.getAuthorities().stream()
//                      .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
//                      .collect(Collectors.toSet()))
              .build();
   }
}