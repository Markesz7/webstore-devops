package hu.bme.aut.fmb.webstore.user;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsImplTest {

    UserDetailsImpl userDetails;

    @Test
    public void getUserName() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        userDetails=new UserDetailsImpl(user);


        String foundName = userDetails.getUsername();


        assertThat(foundName).isEqualTo(user.getUsername());

    }

    @Test
    public void getUserPass() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        userDetails=new UserDetailsImpl(user);


        String foundpass = userDetails.getPassword();


        assertThat(foundpass).isEqualTo(user.getPassword());

    }

    @Test
    public void getUserNonExp() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        userDetails=new UserDetailsImpl(user);


        boolean found=userDetails.isAccountNonExpired();


        assertThat(found).isEqualTo(true);

    }

    @Test
    public void getUserNonLocked() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        userDetails=new UserDetailsImpl(user);


        boolean found=userDetails.isAccountNonLocked();


        assertThat(found).isEqualTo(true);

    }
    @Test
    public void getUserNonExpCred() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        userDetails=new UserDetailsImpl(user);


        boolean found=userDetails.isCredentialsNonExpired();


        assertThat(found).isEqualTo(true);

    }

    @Test
    public void getUserEnabled() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("teszt@email.hu");
        userDetails=new UserDetailsImpl(user);

        boolean found=userDetails.isEnabled();

        assertThat(found).isEqualTo(user.isEnabled());

    }


}
