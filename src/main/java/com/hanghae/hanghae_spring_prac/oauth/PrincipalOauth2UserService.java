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

import java.util.Map;

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
        OAuth2UserInfo userInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            userInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            userInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("우리는 구글과 네이버만 지원");
        }

        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
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
            System.out.println("이미 회원이 만들어졌다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
