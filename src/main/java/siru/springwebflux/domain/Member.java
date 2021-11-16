package siru.springwebflux.domain;

import lombok.*;
import siru.springwebflux.dto.JoinMemberRequest;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @Embedded
    private Address address;

    public static Member createMember(JoinMemberRequest joinMemberRequest) {
        return Member.builder()
                .email(joinMemberRequest.getEmail())
                .password(joinMemberRequest.getPassword())
                .address(new Address(joinMemberRequest.getCity(), joinMemberRequest.getStreet(), joinMemberRequest.getZipcode()))
                .build();
    }

}