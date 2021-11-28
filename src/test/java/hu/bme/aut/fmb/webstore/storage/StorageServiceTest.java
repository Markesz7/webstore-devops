package hu.bme.aut.fmb.webstore.storage;


import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageRepository;
import hu.bme.aut.fmb.webstore.Storage.StorageService;
import hu.bme.aut.fmb.webstore.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class StorageServiceTest {
    private StorageRepository storageRepository;

    private StorageService storageService;

    @Test
    public void addNewStorage_and_findByID_Test() {
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product();
        Storage storage=new Storage(product);

        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));

        storageService.addNewStorage(storage);
        Optional<Storage> foundopt = storageService.findStorageById(storage.getId());

        foundopt.ifPresent(value -> System.out.println(value.getId()));
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(storage.getId()));
        verify(storageRepository, times(1)).save(storage);
        verify(storageRepository, times(1)).findById(storage.getId());

    }

    @Test
    public void findByID_null_Test() {
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);

        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));

        storageService.addNewStorage(storage);
        Optional<Storage> foundopt = storageService.findStorageById(null);

        assertNull(foundopt);
        verify(storageRepository, times(1)).save(storage);
        verify(storageRepository, times(1)).findById(null);

    }

    @Test
    public void deleteStorageByIdTest(){
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product();
        Storage storage=new Storage(1L, product, 2);
        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));
        when(storageRepository.existsById(storage.getId())).thenReturn(true);


        storageService.addNewStorage(storage);
        Optional<Storage> foundopt = storageService.findStorageById(storage.getId());
        foundopt.ifPresent(value -> System.out.println(value.getId()));
        storageService.deleteStorageById(storage.getId());


        verify(storageRepository, times(1)).existsById(storage.getId());
        verify(storageRepository, times(1)).deleteById(storage.getId());

    }
    @Test
    public void deleteStorageByProductTest(){
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);
        List<Storage> storagelist= Arrays.asList(storage);
        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));
        when(storageRepository.findAll()).thenReturn(storagelist);


        storageService.addNewStorage(storage);
        Optional<Storage> foundopt = storageService.findStorageById(storage.getId());
        storageService.deleteStorageByProductId(product.getId());

        verify(storageRepository, times(1)).deleteById(storage.getId());

    }

    @Test
    public void getStoragesTest(){
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);
        List<Storage> storagelist= Arrays.asList(storage);
        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));
        when(storageRepository.findAll()).thenReturn(storagelist);
        storageService.addNewStorage(storage);

        List<Storage> list= storageService.getStorage();
        Long storageId=null;


        for (Storage s:list) {
            System.out.println(s.getId());
            if (s.getId().equals(storage.getId())){
                storageId = s.getId();
            }
        }

        assertThat(storageId).isEqualTo(storage.getId());
    }

    @Test
    public void updateStorageTest(){
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);
        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));

        storageService.addNewStorage(storage);
        storageService.updateStorage(storage.getId(),3);
        Optional<Storage> foundopt = storageService.findStorageById(storage.getId());

        foundopt.ifPresent(value -> assertThat(value.getQuantity())
                .isEqualTo(3));
    }

    @Test
    public void deleteStorageTest_exception(){
        storageRepository=Mockito.mock(StorageRepository.class);
        storageService=new StorageService(storageRepository);
        Product product=new Product(2L,"teszt","man","desc",10);
        Storage storage=new Storage(1L, product, 2);
        when(storageRepository.findById(storage.getId()))
                .thenReturn(Optional.of(storage));


        Throwable exception = assertThrows(IllegalStateException.class, () ->   storageService.deleteStorageById(null));
        assertEquals("no product with this id: null", exception.getMessage());
    }
}
