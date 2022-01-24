package com.hanghae.hanghae_spring_prac.oauth;

import com.hanghae.hanghae_spring_prac.auth.PrincipalDetails;
import com.hanghae.hanghae_spring_prac.user.User;
import com.hanghae.hanghae_spring_prac.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        System.out.println(super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 강제로 회원가입 진행
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider+"_"+providerId; // google_12432185730587349
        String password = passwordEncoder.encode("겟인데어"); // 사용하진 않을 것
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        // 이미 회원가입이 되어있는지 확인
        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            // 강제 회원가입
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .role(role)
                    .build();

            userRepository.save(userEntity);
        } else {

        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
