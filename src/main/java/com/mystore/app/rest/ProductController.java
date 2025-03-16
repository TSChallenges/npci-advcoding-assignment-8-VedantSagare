package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Object> addProduct(@RequestBody @Valid Product product) {
        Product p = productService.addProduct(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping("")
    public  ResponseEntity<List<Product>> getAllProducts(
    		@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
    	List<Product> products = productService.getAllProducts(page,pageSize,sortBy,sortDir);
    	return new ResponseEntity<>(products, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Product p = productService.getProduct(id);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody Product product) {
        Product p = productService.updateProduct(id, product);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        String message = productService.deleteProduct(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // TODO: API to search products by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name){
    	List<Product> response = productService.findByName(name);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // TODO: API to filter products by category
    @GetMapping("/filter/category")
    public ResponseEntity<List<Product>> filterByCategory(@RequestParam String category){ 
    	List<Product> response = productService.findByCategory(category);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // TODO: API to filter products by price range
     @GetMapping("/filter/price")
     public ResponseEntity<List<Product>> filterByPrice(@RequestParam Double minPrice, @RequestParam Double maxPrice ){
        List<Product> response = productService.findByPriceRange(minPrice,maxPrice);
        return new ResponseEntity<>(response , HttpStatus.OK);
     }

    // TODO: API to filter products by stock quantity range
     @GetMapping("/filter/stock")
     public ResponseEntity<List<Product>> filterByStock(@RequestParam Integer minStock, @RequestParam Integer maxStock){
    	 List<Product> response = productService.findByStockRange(minStock, maxStock);
    	 return new ResponseEntity<>(response, HttpStatus.OK);
     }


}
