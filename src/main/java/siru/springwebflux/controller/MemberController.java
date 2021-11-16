package siru.springwebflux.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import siru.springwebflux.domain.Address;
import siru.springwebflux.dto.JoinMemberRequest;
import siru.springwebflux.service.MemberService;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public Flux<MemberResponse> getMembers() {
        return Flux.just();
    }

    @PostMapping("/members")
    public Mono<ResponseEntity<Void>> joinMember(@RequestBody JoinMemberRequest joinMemberRequest) {
        Mono<Long> userIdMono = memberService.joinMember(joinMemberRequest);
        return userIdMono.map(userId -> {
            URI uri = URI.create(String.format("/members/%d", userId));
            return ResponseEntity.created(uri).build();
        });
    }
    @Data
    static class MemberResponse {
        private long userId;
        private String name;
        private Address address;
    }
}
