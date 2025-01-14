package backend.jangbogoProject.member.repository;

import backend.jangbogoProject.member.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        System.out.println("findByEmail : " + email);
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Optional<Member> findById(Long id) {
        System.out.println("findByEmail : " + id);
        return store.values().stream()
                .filter(member -> member.getEmail().equals(id))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
