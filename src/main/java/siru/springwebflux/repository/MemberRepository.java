package siru.springwebflux.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import siru.springwebflux.domain.Member;

@Repository
public interface MemberRepository extends R2dbcRepository<Member, Long> {
}
