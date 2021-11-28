package hu.bme.aut.fmb.webstore.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;


    @Test
    public void addNewProduct_and_findByID_Test() {
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product("teszt", "man", "des", 10);
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));


        productService.addNewProduct(product);
        Optional<Product> foundopt = productService.findProductById(product.getId());
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(product.getId()));

        verify(productRepository, times(1)).save(product);
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    public void deleteProductByIdTest(){
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));
        when(productRepository.existsById(product.getId())).thenReturn(true);

        productService.addNewProduct(product);
        Optional<Product> foundopt = productService.findProductById(product.getId());
        productService.deleteProductById(product.getId());

        verify(productRepository, times(1)).existsById(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    public void updateProductTest_Null(){
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        Product newProduct=product;
        newProduct.setDescription(null);
        newProduct.setManufacturer(null);
        newProduct.setName(null);
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        productService.addNewProduct(product);
        productService.updateProduct(newProduct);

        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getName())
                .isEqualTo(product.getName()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getManufacturer())
                .isEqualTo(product.getManufacturer()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getDescription())
                .isEqualTo(product.getDescription()));
    }

    @Test
    public void updateProductTest_zeroLength(){
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        Product newProduct=product;
        newProduct.setDescription("");
        newProduct.setManufacturer("");
        newProduct.setName("");
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        productService.addNewProduct(product);
        productService.updateProduct(newProduct);

        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getName())
                .isEqualTo(product.getName()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getManufacturer())
                .isEqualTo(product.getManufacturer()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getDescription())
                .isEqualTo(product.getDescription()));
    }

    @Test
    public void updateProductTest_normal(){
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        Product newProduct=product;
        newProduct.setDescription("updated Desc");
        newProduct.setManufacturer("Updated Man");
        newProduct.setName("Updated Name");
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        productService.addNewProduct(product);
        productService.updateProduct(newProduct);

        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getName())
                .isEqualTo(newProduct.getName()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getManufacturer())
                .isEqualTo(newProduct.getManufacturer()));
        productService.findProductById(newProduct.getId()).ifPresent(value -> assertThat(value.getDescription())
                .isEqualTo(newProduct.getDescription()));
    }

    @Test
    public void getProductsTest(){
        productRepository=Mockito.mock(ProductRepository.class);
        productService=new ProductService(productRepository);
        Product product=new Product(1L,"teszt", "man", "des", 10);
        List<Product> productlist= Arrays.asList(product);
        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));
        when(productRepository.findAll()).thenReturn(productlist);

        productService.addNewProduct(product);
        List<Product> list= productService.getProducts();
        Long productId=null;
        for (Product s:list) {
            if (s.getId().equals(product.getId())){
                productId = s.getId();
            }
        }

        assertThat(productId).isEqualTo(product.getId());
    }
}
