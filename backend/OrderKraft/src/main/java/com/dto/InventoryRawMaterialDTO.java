package com.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class InventoryRawMaterialDTO {

    private Long inventory_rawmaterial_id;
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate last_updated;

    private Long category_id;
    private Long inventory_manager_id;
    private int quantity;

    public InventoryRawMaterialDTO() {}

    public InventoryRawMaterialDTO(Long inventory_rawmaterial_id, String name, String description, LocalDate last_updated,
                                   Long category_id, Long inventory_manager_id, int quantity) {
        this.inventory_rawmaterial_id = inventory_rawmaterial_id;
        this.name = name;
        this.description = description;
        this.last_updated = last_updated;
        this.category_id = category_id;
        this.inventory_manager_id = inventory_manager_id;
        this.quantity = quantity;
    }

    public Long getInventory_rawmaterial_id() { return inventory_rawmaterial_id; }
    public void setInventory_rawmaterial_id(Long inventory_rawmaterial_id) { this.inventory_rawmaterial_id = inventory_rawmaterial_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getLast_updated() { return last_updated; }
    public void setLast_updated(LocalDate last_updated) { this.last_updated = last_updated; }

    public Long getCategory_id() { return category_id; }
    public void setCategory_id(Long category_id) { this.category_id = category_id; }

    public Long getInventory_manager_id() { return inventory_manager_id; }
    public void setInventory_manager_id(Long inventory_manager_id) { this.inventory_manager_id = inventory_manager_id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
