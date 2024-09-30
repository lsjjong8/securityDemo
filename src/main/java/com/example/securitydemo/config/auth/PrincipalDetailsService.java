package com.example.securitydemo.config.auth;

import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// @RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member entity = memberRepository.findByAccount(account)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
                });
        return new PrincipalDetails(entity);
    }
}