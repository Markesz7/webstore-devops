package hu.bme.aut.fmb.webstore.product;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.*;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;
    private StorageService storageService;
    private Product product;

    @Test
    public void getProductsTest() throws Exception {
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        List<Product> list = productService.getProducts();
        when(productService.getProducts()).thenReturn(list);

        productController.getProducts();

        verify(productService, times(2)).getProducts();

    }

    @Test
    public void addNewProductTest(){
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        product= new Product();
        ArgumentCaptor<Storage> argument = ArgumentCaptor.forClass(Storage.class);
        when(productService.findProductById(product.getId())).thenReturn(java.util.Optional.ofNullable(product));

        productController.addNewProduct(product);

        verify(productService, times(1)).addNewProduct(product);
        verify(storageService, times(1)).addNewStorage(argument.capture());


    }


    @Test
    public void deleteProductTest(){
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        product= new Product();

        productController.addNewProduct(product);
        productController.deleteProduct(product.getId());

        verify(storageService, times(1)).deleteStorageByProductId(product.getId());
        verify(productService, times(1)).deleteProductById(product.getId());
    }

    @Test
    public void updateProductTest_priceNotNull(){
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        List<Product> productlist= Arrays.asList(product);
        when(productService.getProducts()).thenReturn(productlist);


        productController.addNewProduct(product);
        productController.updateProduct(product.getId(), "szalamisPizza", "Pizzaforte", "finom", 300);

        verify(productService, times(2)).updateProduct(argument.capture());

    }

    @Test
    public void updateProductTest_priceNull(){
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        List<Product> productlist= Arrays.asList(product);
        when(productService.getProducts()).thenReturn(productlist);

        productController.addNewProduct(product);
        productController.updateProduct(product.getId(), "szalamisPizza", "Pizzaforte", "finom", null);

        verify(productService, times(1)).updateProduct(argument.capture());

    }

    @Test
    public void updateProductTest_everythingNull(){
        productService = Mockito.mock(ProductService.class);
        storageService = Mockito.mock(StorageService.class);
        productController = new ProductController(productService,storageService);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        List<Product> productlist= Arrays.asList(product);
        when(productService.getProducts()).thenReturn(productlist);

        productController.addNewProduct(product);
        productController.updateProduct(product.getId(), null, null, null, null);

        verify(productService, times(1)).updateProduct(argument.capture());

    }
}