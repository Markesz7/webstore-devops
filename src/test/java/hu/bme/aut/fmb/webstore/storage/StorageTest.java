package hu.bme.aut.fmb.webstore.storage;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageRepository;
import hu.bme.aut.fmb.webstore.product.Product;
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
public class StorageTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StorageRepository storageRepository;

    @Test
    public void whenFindById_thenReturnStorage() {
        // given
        Storage storage = new Storage(1L,0);
        entityManager.persist(storage);
        entityManager.flush();


        // when
        Optional<Storage> foundopt = storageRepository.findById(storage.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(storage.getId()));
    }

    @Test
    public void whenFindByQuantity_thenReturnStorage() {
        // given
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();
        Storage storage = new Storage(product);
        entityManager.persist(storage);
        entityManager.flush();


        // when
        Optional<Storage> foundopt = storageRepository.findById(storage.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getQuantity())
                .isEqualTo(storage.getQuantity()));
    }

    @Test
    public void whenFindByProduct_thenReturnStorage() {
        // given
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();
        Storage storage = new Storage(product);
        entityManager.persist(storage);
        entityManager.flush();


        // when
        Optional<Storage> foundopt = storageRepository.findById(storage.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getProduct())
                .isEqualTo(storage.getProduct()));
    }

    @Test
    public void whenFindByQuantity_thenReturnStorage_withSet() {
        // given
        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();
        Storage storage = new Storage(product);
        storage.setQuantity(3);
        entityManager.persist(storage);
        entityManager.flush();


        // when
        Optional<Storage> foundopt = storageRepository.findById(storage.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getQuantity())
                .isEqualTo(storage.getQuantity()));
    }

    @Test
    public void whenFindByProduct_thenReturnStorage_withSet() {
        // given

        Product product = new Product();
        entityManager.persist(product);
        entityManager.flush();
        Product newProduct = new Product();
        entityManager.persist(newProduct);
        entityManager.flush();
        Storage storage = new Storage(product);
        storage.setProduct(newProduct);
        entityManager.persist(storage);
        entityManager.flush();


        // when
        Optional<Storage> foundopt = storageRepository.findById(storage.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getProduct())
                .isEqualTo(storage.getProduct()));
    }
}
