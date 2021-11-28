package hu.bme.aut.fmb.webstore.user;

import hu.bme.aut.fmb.webstore.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtUtil jwtTokenUtil;
    UserDetailsServiceImpl userDetailsService;
    AuthenticationManager authenticationManager;
    UserController userController;

    @Test
    public void registerUserTest() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findById(user.getUsername());

    }

    @Test
    public void registerUserTest_registered() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));


        userController.registerUser(user);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(2)).findById(user.getUsername());

    }

    @Test
    public void updateUserTest() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));


        user.setPassword("ujpass");
        userController.updateUser(user.getUsername(), user, new Principal() {
            @Override
            public String getName() {
                return user.getUsername();
            }
        });


        verify(userRepository, times(2)).findById(user.getUsername());
        verify(userRepository, times(2)).save(user);

    }

    @Test
    public void updateUserTest_notfound() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");


        user.setPassword("ujpass");
        userController.updateUser(user.getUsername(), user, new Principal() {
            @Override
            public String getName() {
                return user.getUsername();
            }
        });


        verify(userRepository, times(1)).findById(user.getUsername());
        verify(userRepository, times(0)).save(user);

    }

    @Test
    public void updateUserTest_principalwrongname() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));


        user.setPassword("ujpass");
        userController.updateUser(user.getUsername(), user, new Principal() {
            @Override
            public String getName() {
                return null;
            }
        });


        verify(userRepository, times(2)).findById(user.getUsername());
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void deleteUserTest() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));


        userController.delete(user.getUsername(), new Principal() {
            @Override
            public String getName() {
                return user.getUsername();
            }
        });

        verify(userRepository, times(2)).findById(user.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).deleteById(user.getUsername());

    }

    @Test
    public void deleteUserTest_usernull() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");


        userController.delete(user.getUsername(), new Principal() {
            @Override
            public String getName() {
                return user.getUsername();
            }
        });


        verify(userRepository, times(1)).findById(user.getUsername());
        verify(userRepository, times(0)).save(user);
        verify(userRepository, times(0)).deleteById(user.getUsername());

    }

    @Test
    public void deleteUserTest_principalwrong() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
               .thenReturn(Optional.of(user));


        userController.delete(user.getUsername(), new Principal() {
            @Override
            public String getName() {
                return null;
            }
        });


        verify(userRepository, times(2)).findById(user.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(0)).deleteById(user.getUsername());

    }

    @Test
    public void getAllUser() {

        userRepository= Mockito.mock(UserRepository.class);
        passwordEncoder=Mockito.mock(BCryptPasswordEncoder.class);
        jwtTokenUtil= Mockito.mock(JwtUtil.class);
        userDetailsService= Mockito.mock(UserDetailsServiceImpl.class);
        authenticationManager= Mockito.mock(AuthenticationManager.class);
        userController=new UserController(userRepository,passwordEncoder,jwtTokenUtil,userDetailsService,authenticationManager);
        User user=new User();
        user.setUsername("testuser");
        user.setPassword("pass");

        List<User> userlist= Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userlist);

        userController.registerUser(user);

        when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));


        List<User> userlistfound=userController.getAll();

        assertThat(userlistfound).isEqualTo(userlist);
        verify(userRepository, times(1)).findById(user.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(2)).findAll();

    }
}
