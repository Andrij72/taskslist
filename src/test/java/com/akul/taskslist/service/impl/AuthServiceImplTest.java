package com.akul.taskslist.service.impl;

import com.akul.taskslist.config.TestConfig;
import com.akul.taskslist.web.security.JwtTokenProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthServiceImpl authService;

    @MockBean
    private UserServiceImpl userService;

   /* @Test
    void login() {
        Long userId = 1L;
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setRoles(roles);
        Mockito.when(userService.getByUsername(username)).thenReturn(user);
        Mockito.when(jwtTokenProvider.createAccessToken(userId, username, roles)).thenReturn(accessToken);
        Mockito.when(jwtTokenProvider.createRefreshToken(userId, username)).thenReturn(refreshToken);
        JwtResponse jwtResponse = authService.login(jwtRequest);
        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername(),
                jwtRequest.getPassword()
        ));
        Assertions.assertEquals(username, jwtResponse.getUsername());
        Assertions.assertEquals(userId, jwtResponse.getId());
        Assertions.assertNotNull(jwtResponse.getAccessToken());
        Assertions.assertNotNull(jwtResponse.getRefreshToken());
    }

    @Test
    void loginWhenIncorrectUsername() {
        String username = "username";
        String password = "password";
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);
        Mockito.when(userService.getByUsername(username)).thenThrow(ResourceNotFoundException.class);
        Mockito.verifyNoInteractions(jwtTokenProvider);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> authService.login(jwtRequest));
    }

    @Test
    void refreshToken() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(newRefreshToken);
        Mockito.when(jwtTokenProvider.refreshUserTokens(refreshToken)).thenReturn(jwtResponse);
        JwtResponse testJwtResponse = authService.refresh(refreshToken);
        Mockito.verify(jwtTokenProvider).refreshUserTokens(refreshToken);
        Assertions.assertEquals(jwtResponse, testJwtResponse);
    }*/

}

