package siru.springwebflux.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import siru.springwebflux.domain.Member;
import siru.springwebflux.dto.JoinMemberRequest;
import siru.springwebflux.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Mono<Long> joinMember(JoinMemberRequest joinMemberInfo) {
        Member member = Member.createMember(joinMemberInfo);
        return memberRepository.save(member).map(Member::getId);
    }

}
