package com.hanghae.hanghae_spring_prac.auth;

import com.hanghae.hanghae_spring_prac.user.User;
import com.hanghae.hanghae_spring_prac.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if (userEntity != null)
            return new PrincipalDetails(userEntity);
            // 리턴하게 되면 Authentication (내부에 UserDetails)가 시큐리티 세션 내부에 들어간다.

        return null;
    }
}
