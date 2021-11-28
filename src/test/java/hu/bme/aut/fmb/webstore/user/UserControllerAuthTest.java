package hu.bme.aut.fmb.webstore.user;



import hu.bme.aut.fmb.webstore.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerAuthTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtTokenUtil;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserController userController;





    @Test
    public void authUserTest() throws Exception {
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        user.setEnabled(true);
        List<String> roles= Arrays.asList("ROLE_MODERATOR");
        user.setRoles(roles);


        Map<String, String> map2 = new HashMap<>();
        map2.put("username", "testuser");
        map2.put("password", "pass");

        userController.registerUser(user);


        assertThat(userController.createJWTtoken(map2).getStatusCode().toString()).isEqualTo("200 OK");

    }

    @Test
    public void authUserTest_Exception() throws Exception {
        User user=new User();
        user.setUsername("rossznev");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        user.setEnabled(true);
        List<String> roles= Arrays.asList("ROLE_MODERATOR");
        user.setRoles(roles);


        Map<String, String> map2 = new HashMap<>();
        map2.put("username", "jonev");
        map2.put("password", "pass");

        userController.registerUser(user);

        Throwable exception = assertThrows(Exception.class, () -> userController.createJWTtoken(map2));
        assertEquals("Incorrect username or password", exception.getMessage());


    }
}
