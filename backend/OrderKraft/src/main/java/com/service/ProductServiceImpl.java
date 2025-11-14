package com.service;

import com.dto.BOMDTO;
import com.dto.BOMMaterialDTO;
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

//    @Override
//    public List<ProductDTOCustom> getAllProducts() {
//    	 return productRepo.findAll().stream()
//    	            .map(ProductMapper::toProductDTOCustom)
//    	            .collect(Collectors.toList());
//    }

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
    
    @Override
    public List<ProductDTOCustom> getAllProducts() {

        return productRepo.findAll().stream().map(product -> {

            // CATEGORY
            Long categoryId = null;
            String categoryName = null;

            if (product.getCategory() != null) {
                categoryId = product.getCategory().getCategoryId();
                categoryName = product.getCategory().getName();
            }

            // BOM
            BOMDTO bomDTO = null;

            if (product.getBom() != null) {
                BOM bom = product.getBom();

                bomDTO = new BOMDTO();
                bomDTO.setBomId(bom.getBom_id());
                bomDTO.setBomName(bom.getbomName());
                bomDTO.setRemark(bom.getRemark());

                // ⭐ Load BOM Material list
                if (bom.getMaterials() != null) {

                    List<BOMMaterialDTO> materials = bom.getMaterials().stream().map(mat -> {

                        BOMMaterialDTO dto = new BOMMaterialDTO();

                        dto.setMaterialId(mat.getMaterialId());
                        dto.setBomId(bom.getBom_id());

                        // raw material entity
                        dto.setRawMaterialId(mat.getRawmaterial().getInventory_rawmaterial_id());
                        dto.setRawMaterialName(mat.getRawmaterial().getName());

                        // qty per unit
                        dto.setQntPerUnit(mat.getQntperunit());

                        return dto;

                    }).toList();

                    bomDTO.setMaterials(materials);
                }
            }

            // FINAL DTO
            return new ProductDTOCustom(
                    product.getProduct_id(),
                    product.getName(),
                    product.getDescription(),
                    product.getUnit_price(),
                    categoryId,
                    categoryName,
                    bomDTO
            );

        }).toList();
    }

    
//    @Override
//    public List<ProductDTOCustom> getAllProducts() {
//
//        return productRepo.findAll().stream().map(product -> {
//
//            ProductDTOCustom dto = new ProductDTOCustom();
//
//            dto.setProductId(product.getProduct_id());
//            dto.setName(product.getName());
//            dto.setDescription(product.getDescription());
//            dto.setUnitPrice(product.getUnit_price());
//
//            // Map Category
//            if (product.getCategory() != null) {
//                dto.setCategoryId(product.getCategory().getCategoryId());
//                dto.setCategoryName(product.getCategory().getName());
//            }
//
//            // Map BOM (nested)
//            if (product.getBom() != null) {
//                BOMDTO bom = new BOMDTO();
//                bom.setBomId(product.getBom().getBom_id());
//                bom.setBomName(product.getBom().getbomName());
//                //bom.setBomDescription(product.getBom().getBomDescription());
//                dto.setBom(bom);
//            }
//
//            return dto;
//
//        }).collect(Collectors.toList());
//    }
    
    

}
