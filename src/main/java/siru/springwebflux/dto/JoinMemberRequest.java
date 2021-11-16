package siru.springwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinMemberRequest {
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
