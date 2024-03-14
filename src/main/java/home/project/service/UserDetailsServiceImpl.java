package home.project.service;

import home.project.domain.Member;
import home.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> { throw new IllegalStateException(email+"로 가입된 회원이 없습니다."); });
        return new org.springframework.security.core.userdetails.User(
                member.getEmail(), member.getPassword(), new ArrayList<>());
    }

}
