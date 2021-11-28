package hu.bme.aut.fmb.webstore.storage;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageController;
import hu.bme.aut.fmb.webstore.Storage.StorageService;
import hu.bme.aut.fmb.webstore.product.Product;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;



@SpringBootTest
@AutoConfigureMockMvc
public class StorageControllerTest {

    private StorageController storageController;
    private StorageService storageService;
    private Storage storage;

    @Test
    public void addNewStorageTest() {
        storageService = Mockito.mock(StorageService.class);
        storageController = new StorageController(storageService);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);
        ArgumentCaptor<Storage> argument = ArgumentCaptor.forClass(Storage.class);
        when(storageService.findStorageById(storage.getId())).thenReturn(java.util.Optional.ofNullable(storage));

        storageController.addNewStorage(storage);

        verify(storageService, times(1)).addNewStorage(storage);
        verify(storageService, times(0)).findStorageById(storage.getId());
    }

    @Test
    public void getProductsTest(){
        storageService = Mockito.mock(StorageService.class);
        storageController = new StorageController(storageService);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);

        storageController.getProducts();

        verify(storageService, times(1)).getStorage();
    }

    @Test
    public void deleteStorageTest(){
        storageService = Mockito.mock(StorageService.class);
        storageService = Mockito.mock(StorageService.class);
        storageController = new StorageController(storageService);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);

        storageController.addNewStorage(storage);
        storageController.deleteStorage(storage.getId());


        verify(storageService, times(1)).deleteStorageById(storage.getId());
    }

    @Test
    public void updateStorageTest(){
        storageService = Mockito.mock(StorageService.class);
        storageService = Mockito.mock(StorageService.class);
        storageController = new StorageController(storageService);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);

        storageController.updateProduct(storage.getId(),product.getId(),product, 11);

        verify(storageService, times(1)).updateStorage(storage.getId(),11);
    }
}
