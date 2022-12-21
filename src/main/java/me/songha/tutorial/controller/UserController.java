package me.songha.tutorial.controller;

import lombok.RequiredArgsConstructor;
import me.songha.tutorial.dto.MemberDto;
import me.songha.tutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/hello-world")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello-world");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    /**
     * @Description :: 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@Valid @RequestBody MemberDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    /**
     * @Description :: Bean에서 사용자 정보 가져오기
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    /**
     * @Description :: Controller 에서 사용자 정보 가져오기
     * - @Controller로 선언된 bean 객체에서는 메서드 인자로 Principal 객체에 직접 접근할 수 있는 추가적인 옵션이 있다.
     */
    @GetMapping("/user/principal")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDto> getMyUserInfo(HttpServletRequest request, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

    /**
     * @Description :: @AuthenticationPrincipal 를 이용하여 CustomUser 객체를 인자에 넘겨줄 수 있다.
     * -  UserDetails 를 구현한 CustomUser 클래스가 있고, UserDetailsService 구현체에서 CustomUser 객체를 반환한다고 가정한다.
     */
    @GetMapping("/user/customuser")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDto> getMyUserInfo(HttpServletRequest request, @AuthenticationPrincipal User user) {
        String username = user.getUsername();
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

    /**
     * @Description :: admin role 소유 여부에 대한 테스트
     * hasAnyRole은 접두어 'ROLE_'을 붙여 조회한다.
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
