package eightseconds.global.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        saveOrUpdate(oAuth2User);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        OAuth2User userDetails = new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "key");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "key");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(authentication);

        writeTokenResponse(response, jwt);
    }

    private void writeTokenResponse(HttpServletResponse response, String jwt)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Authorization", "Bearer " + jwt);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwt));
        writer.flush();
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = userRepository.findByEmail((String) attributes.get("email"))
                .map(entity -> entity.update((String)attributes.get("name"), (String)attributes.get("picture")))
                .orElse(User.toEntity(oAuth2User));
        return userRepository.save(user);
    }
}
