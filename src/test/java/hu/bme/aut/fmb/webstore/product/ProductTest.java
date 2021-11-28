package hu.bme.aut.fmb.webstore.product;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindById_thenReturnProduct() {
        // given
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();


        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(product.getId()));
    }
    @Test
    public void whenFindByName_thenReturnProduct() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getName())
                .isEqualTo(product.getName()));
    }@Test
    public void whenFindByMan_thenReturnProduct() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getManufacturer())
                .isEqualTo(product.getManufacturer()));
    }
    @Test
    public void whenFindByDesc_thenReturnProduct() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getDescription())
                .isEqualTo(product.getDescription()));
    }
    @Test
    public void whenFindByPrice_thenReturnProduct() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getPrice())
                .isEqualTo(product.getPrice()));
    }



    @Test
    public void whenFindByName_thenReturnProduct_withset() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        product.setName("Proba2");
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then

        foundopt.ifPresent(value -> assertThat(value.getName())
                .isEqualTo(product.getName()));
    }@Test
    public void whenFindByMan_thenReturnProduct_withset() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        product.setManufacturer("UjMan");
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getManufacturer())
                .isEqualTo(product.getManufacturer()));
    }
    @Test
    public void whenFindByDesc_thenReturnProduct_withset() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        product.setDescription("UjDesc");
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getDescription())
                .isEqualTo(product.getDescription()));
    }
    @Test
    public void whenFindByPrice_thenReturnProduct_withset() {
        // given
        Product product = new Product("Proba", "TestMan", "TesztDesc", 100);
        product.setPrice(200);
        entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> foundopt = productRepository.findById(product.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getPrice())
                .isEqualTo(product.getPrice()));
    }

}
