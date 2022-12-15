package me.songha.tutorial.controller;

import lombok.RequiredArgsConstructor;
import me.songha.tutorial.dto.LoginDto;
import me.songha.tutorial.dto.TokenDto;
import me.songha.tutorial.jwt.JwtFilter;
import me.songha.tutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * @Description :: 로그인 요청
     */
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        // 로그인 정보를 이용해 authenticationToken 객체를 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticate(authenticationToken) 메소드가 실행이 될 때
        // CustomUserDetailsService.loadUserByUsername() 메소드가 실행된다.
        // 그리하여 생성된 authentication 객체를 SecurityContextHolder 에 set 한다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 그리고 인증정보를 통해 jwt 토큰을 생성한다.
        String jwt = tokenProvider.createToken(authentication);

        // jwt 토큰을 Header에도 넣고 Response Body 에도 넣어서 리턴한다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
