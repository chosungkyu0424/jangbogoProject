package backend.jangbogoProject.config;

import backend.jangbogoProject.repository.*;
import backend.jangbogoProject.member.service.MemberService;
import backend.jangbogoProject.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

   // @Bean
   // public MemberRepository memberRepository(){

   //     return new JpaMemberRepository(em);
   // }
}
