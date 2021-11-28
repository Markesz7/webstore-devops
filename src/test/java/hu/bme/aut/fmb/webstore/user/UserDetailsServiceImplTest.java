package hu.bme.aut.fmb.webstore.user;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsServiceImplTest {
    UserRepository repository;
    UserDetailsServiceImpl userDetailsservice;
    @Test
    public void getUserNameTest() {
        repository= Mockito.mock(UserRepository.class);
        userDetailsservice=new UserDetailsServiceImpl(repository);
        User user = new User();
        user.setUsername("tesztuser");
        UserDetailsImpl userDetails=new UserDetailsImpl(user);

        when(repository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetailsImpl details= (UserDetailsImpl) userDetailsservice.loadUserByUsername(user.getUsername());

        assertThat(details.getUsername()).isEqualTo(userDetails.getUsername());
        verify(repository, times(1)).findById(user.getUsername());

    }

    @Test
    public void getUserNameTest_notFound() {
        repository= Mockito.mock(UserRepository.class);
        userDetailsservice=new UserDetailsServiceImpl(repository);
        User user = new User();
        user.setUsername("tesztuser");
        UserDetailsImpl userDetails=new UserDetailsImpl(user);

        when(repository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetailsImpl details= (UserDetailsImpl) userDetailsservice.loadUserByUsername(user.getUsername());

        Throwable exception = assertThrows(UsernameNotFoundException.class, () ->  userDetailsservice.loadUserByUsername(null));
        assertEquals("null is an invalid username", exception.getMessage());
        verify(repository, times(1)).findById(user.getUsername());

    }
}
