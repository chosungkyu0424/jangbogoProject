package backend.jangbogoProject.member.repository;

import backend.jangbogoProject.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository
{
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
}