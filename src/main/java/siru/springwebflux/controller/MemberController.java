package siru.springwebflux.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import siru.springwebflux.domain.Address;


@RestController
public class MemberController {

    @GetMapping("/members")
    public Flux<MemberResponse> getMembers() {
        return Flux.just();
    }

    @Data
    static class MemberResponse {
        private long userId;
        private String name;
        private Address address;
    }
}
