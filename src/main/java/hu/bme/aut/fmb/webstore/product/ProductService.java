package hu.bme.aut.fmb.webstore.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public boolean addNewProduct(Product product) {
        List<Product> products = productRepository.findAll();
        for (Product p : products)
        {
            if(p.getName().equals(product.getName()))
                return true;
        }
        productRepository.save(product);
        return false;
    }


    public void deleteProductById(Long id) {
        boolean exists = productRepository.existsById(id);
        if (exists){
            productRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("no product with this id: "+id);
        }
    }

    @Transactional
    public void updateProduct(Product product) {
        System.out.println(product.getName() +" "+product.getManufacturer()+" "+product.getDescription()+" "+product.getPrice());
        Product toUpdate = productRepository.findById(product.getId()).orElseThrow(()->new IllegalStateException("product with id: "+product.getId()+" dont exists"));
        if (product.getName() != null && product.getName().length()>0 && !Objects.equals(toUpdate.getName(),product.getName())){
            toUpdate.setName(product.getName());
        }

        if (product.getManufacturer() != null && product.getManufacturer().length()>0 && !Objects.equals(toUpdate.getManufacturer(),product.getManufacturer())){
            toUpdate.setManufacturer(product.getManufacturer());
        }

        if (product.getDescription() != null && product.getDescription().length()>0 && !Objects.equals(toUpdate.getDescription(),product.getDescription())){
            toUpdate.setDescription(product.getDescription());
        }
        toUpdate.setPrice(product.getPrice());
    }

    public Optional<Product> findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            return null;
        }
        else {
            return product;
        }
    }


}
