package com.practice.domain.user.controller;

import com.practice.domain.user.dto.UserSignUpDto;
import com.practice.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionRegistry sessionRegistry;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/sessions")
    public String getSessionInfo(HttpSession session, Model model) {

        List<Object> principals = sessionRegistry.getAllPrincipals();
        log.info("sessionRegistry :: getAllPrincipals : {}" , sessionRegistry.getAllPrincipals());
        if (!principals.isEmpty()) {
            for (Object principal : principals) {
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
                    for (SessionInformation sessionInformation : sessions) {
                        System.out.println(userDetails.getUsername() + ": " + sessionInformation.getSessionId());
                    }
                }
            }
        } else {
            System.out.println("principals is null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        /*(SecurityContext) session.getAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);*/

        if (securityContext != null) {
            log.info("securityContext type: {}", securityContext.getClass().getSimpleName());
            log.info("securityContext value: {}", securityContext);
            Authentication auth = securityContext.getAuthentication();
            return auth.getName() + "의 권한: " + auth.getAuthorities();
        } else {
            log.info("securityContext is null");
        }

        return "세션에 인증 정보가 없습니다.";
    }
}
