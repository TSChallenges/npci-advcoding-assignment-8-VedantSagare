package com.mystore.app.service;

import com.mystore.app.entity.Product;
import com.mystore.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private Integer currentId = 1;

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        product.setId(currentId++);
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts(int page, int pageSize,String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        List<Product> products = productRepository.findAll(pageable).getContent();
        return productRepository.findAll(pageable).getContent();
    }

    public Product getProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.get();
    }

    public Product updateProduct(Integer id, Product product) {
        Product p = productRepository.findById(id).get();
        if (p == null) return null;
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setCategory(product.getCategory());
        p.setStockQuantity(product.getStockQuantity());
        productRepository.save(p);
        return p;
    }

    public String deleteProduct(Integer id) {
        Product p = productRepository.findById(id).get();
        if (p == null) return "Product Not Found";
        productRepository.delete(p);
        return "Product Deleted Successfully";
    }

	

    // TODO: Method to search products by name
    public List<Product> findByName(String name) {
		return productRepository.findByNameIgnoreCaseContaining(name);
	}

	


    // TODO: Method to filter products by category
    public List<Product> findByCategory(String category) {
		return productRepository.findByCategoryIgnorecaseContaining(category);
	}

	

    // TODO: Method to filter products by price range
    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
		// TODO Auto-generated method stub
		return   productRepository.findByPriceBetween(minPrice, maxPrice);
	}

	


    // TODO: Method to filter products by stock quantity range
    public List<Product> findByStockRange(Integer minStock, Integer maxStock) {
		// TODO Auto-generated method stub
	return productRepository.findBystockQuantityBetween(minStock,  maxStock);
	}


}
