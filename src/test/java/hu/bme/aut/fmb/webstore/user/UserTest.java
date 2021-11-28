package hu.bme.aut.fmb.webstore.user;

import hu.bme.aut.fmb.webstore.purchases.Purchase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserNameTest() {
        // given

        User user=new User();
        user.setUsername("tesztuser");
        entityManager.persist(user);
        entityManager.flush();


        // when
        Optional<User> foundopt = userRepository.findById(user.getUsername());

        // then
        foundopt.ifPresent(value -> assertThat(value.getUsername())
                .isEqualTo(user.getUsername()));
    }

    @Test
    public void getPasswordTest() {
        // given

        User user=new User();
        user.setUsername("tesztuser");
        user.setPassword("tesztjelszo");
        entityManager.persist(user);
        entityManager.flush();


        // when
        Optional<User> foundopt = userRepository.findById(user.getUsername());

        // then
        foundopt.ifPresent(value -> assertThat(value.getPassword())
                .isEqualTo(user.getPassword()));
    }

    @Test
    public void getEmailTest() {
        // given

        User user=new User();
        user.setUsername("tesztuser");
        user.setEmail("teszt@email.hu");
        entityManager.persist(user);
        entityManager.flush();


        // when
        Optional<User> foundopt = userRepository.findById(user.getUsername());

        // then
        foundopt.ifPresent(value -> assertThat(value.getEmail())
                .isEqualTo(user.getEmail()));
    }

    @Test
    public void getIsEnabledTest() {
        // given

        User user=new User();
        user.setUsername("tesztuser");
        user.setEnabled(true);
        entityManager.persist(user);
        entityManager.flush();


        // when
        Optional<User> foundopt = userRepository.findById(user.getUsername());

        // then
        foundopt.ifPresent(value -> assertThat(value.isEnabled())
                .isEqualTo(user.isEnabled()));
    }

    @Test
    public void getRolesTest() {
        // given

        User user=new User();
        user.setUsername("tesztuser");
        List<String> roles= Arrays.asList("ROLE_USER");
        user.setRoles(roles);
        entityManager.persist(user);
        entityManager.flush();


        // when
        Optional<User> foundopt = userRepository.findById(user.getUsername());

        // then
        foundopt.ifPresent(value -> assertThat(value.getRoles())
                .isEqualTo(user.getRoles()));
    }


}
