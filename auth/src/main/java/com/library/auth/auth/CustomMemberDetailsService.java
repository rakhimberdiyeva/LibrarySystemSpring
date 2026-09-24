package com.library.auth.auth;

import com.library.auth.auth.exceptions.UsernameNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomMemberDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFound{
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFound("username not found " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPasswordHash(),
                Collections.emptyList()
        );
    }
}