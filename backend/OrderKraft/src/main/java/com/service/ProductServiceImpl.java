package com.service;

import com.dto.ProductDTO;
import com.dto.ProductDTOCustom;
import com.entity.BOM;
import com.entity.Category;
import com.entity.Product;
import com.mapper.ProductMapper;
import com.repository.BOMRepository;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BOMRepository bomRepo;

    @Override
    public Product createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setUnit_price(dto.getUnit_price());

        // Set Category if provided
        if (dto.getCategory_id() != null && dto.getCategory_id() != 0) {
            Optional<Category> category = categoryRepo.findById(dto.getCategory_id());
            category.ifPresent(product::setCategory);
        }

        // Set BOM if provided
        if (dto.getBom_id() != null && dto.getBom_id() != 0) {
            Optional<BOM> bom = bomRepo.findById(dto.getBom_id());
            bom.ifPresent(product::setBom);
        }

        return productRepo.save(product);
    }

    @Override
    public List<ProductDTOCustom> getAllProducts() {
    	 return productRepo.findAll().stream()
    	            .map(ProductMapper::toProductDTOCustom)
    	            .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategory_id(Long category_id) {
        return productRepo.findByCategory_CategoryId(category_id);
    }
}
