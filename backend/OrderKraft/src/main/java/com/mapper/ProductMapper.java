package com.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.dto.*;
import com.entity.*;

public class ProductMapper {

    public static ProductDTOCustom toProductDTOCustom(Product product) {
        if (product == null) return null;

        BOMDTO bomDTO = toBOMDTO(product.getBom());

        return new ProductDTOCustom(
                product.getProduct_id(),
                product.getName(),
                product.getDescription(),
                product.getUnit_price(),
                product.getCategory() != null ? product.getCategory().getCategoryId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                bomDTO
        );
    }

    private static BOMDTO toBOMDTO(BOM bom) {
        if (bom == null) return null;

        List<BOMMaterialDTO> materials = null;
        if (bom.getMaterials() != null) {
            materials = bom.getMaterials().stream()
                    .map(ProductMapper::toBOMMaterialDTO)
                    .collect(Collectors.toList());
        }

        return new BOMDTO(
                bom.getBom_id(),
                bom.getbomName(),
                bom.getRemark(),
                materials
        );
    }

    private static BOMMaterialDTO toBOMMaterialDTO(BOM_Material material) {
        if (material == null) return null;

        return new BOMMaterialDTO(
                material.getMaterialId(),
                material.getBom() != null ? material.getBom().getBom_id() : null,
                material.getRawmaterial() != null ? material.getRawmaterial().getInventory_rawmaterial_id() : null,
                material.getRawmaterial() != null ? material.getRawmaterial().getName() : null, // ✅ Fetching from InventoryRawMaterial table
                material.getQntperunit()
            );
    }
}
