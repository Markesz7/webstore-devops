package hu.bme.aut.fmb.webstore.product;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageService;
import hu.bme.aut.fmb.webstore.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    private final ProductService productService;
    private final StorageService storageService;
    Logger logger =  LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping
    public List<Product> getProducts() {
        List<Product> list = productService.getProducts();
        logger.info(String.format("GET request for listing all products, there are %d product.",list.size()));
        return list;
    }

    @Secured(User.ROLE_MODERATOR)
    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        logger.info(String.format("POST request for adding new product: %s %s %s %s",product.getName(),product.getManufacturer(),product.getDescription(),product.getPrice()));
        boolean exists = productService.addNewProduct(product);
        if(!exists)
            storageService.addNewStorage(new Storage(product));
    }

    @Secured(User.ROLE_MODERATOR)
    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long id) {
        logger.info(String.format("DELETE request for id %s",id));
        storageService.deleteStorageByProductId(id);
        //productService.deleteProductById(id);
    }

    @Secured(User.ROLE_MODERATOR)
    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String manufacturer,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) Integer price) {
        String oldName="";
        String oldManufacturer="";
        String oldDescription="";
        int oldPrice=-1;
        if (price != null) {
            Product product = new Product(id, name, manufacturer, description, price);
            productService.updateProduct(product);
        }


        List<Product> list = productService.getProducts();
        for (Product p : list) {
            if (p.getId().equals(id)) {
                oldPrice = p.getPrice();
                oldName = p.getName();
                oldManufacturer = p.getManufacturer();
                oldDescription = p.getDescription();
                oldPrice = p.getPrice();
            }
        }
        if (name == null){ name = oldName; }
        if (manufacturer == null){manufacturer = oldManufacturer;}
        if (description ==null){description = oldDescription;}
        if (price == null){price = oldPrice;}
        logger.info(String.format("PUT request for products: \n\tName: %s --> %s \n\t %s --> %s\n\t %s --> %s \n\t %s --> %s",oldName,name,oldManufacturer,manufacturer,oldDescription,description,oldPrice,price));
        productService.updateProduct (new Product(id, name, manufacturer, description, price));
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void exportToCsv( ) throws IOException {
        String filename="product.csv";
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append("id");
        csvWriter.append(";");
        csvWriter.append("Name");
        csvWriter.append(";");
        csvWriter.append("Manufacturer");
        csvWriter.append(";");
        csvWriter.append("Description");
        csvWriter.append(";");
        csvWriter.append("Price");
        csvWriter.append("\n");
        List<Product> list = productService.getProducts();


        for (Product p: list){
            csvWriter.append(p.getId().toString());
            csvWriter.append(";");
            csvWriter.append(p.getName());
            csvWriter.append(";");
            csvWriter.append(p.getManufacturer());
            csvWriter.append(";");
            csvWriter.append(p.getDescription());
            csvWriter.append(";");
            csvWriter.append(String.valueOf(p.getPrice()));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Exported Products");
        logger.info("Backup save Products");
    }
}
