package me.songha.tutorial.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "MEMBER_AUTHORITY")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthority {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "AUTHORITY_NAME", length = 50)
    private String authorityName;

    @CreatedDate
    @Column(name = "INSERT_DATE")
    private ZonedDateTime insertDate;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_NAME",insertable = false, updatable = false)
    private Authority authority;
}
