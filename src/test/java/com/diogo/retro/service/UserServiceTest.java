package com.diogo.retro.service;

import com.diogo.retro.model.User;
import com.diogo.retro.model.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void init() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        User user = new User();
        user.setUsername("shazin");
        user.setPassword("sha908");
        user.setRole("USER");

        when(userRepository.findByUsername("shazin")).thenReturn(user);

        // When
        UserDetails actual = userService.loadUserByUsername("shazin");

        // Then
        verify(userRepository, times(1)).findByUsername("shazin");
    }

}
