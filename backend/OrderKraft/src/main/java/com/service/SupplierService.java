package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.SupplierDTO;
import com.entity.Supplier;

public interface SupplierService {
Supplier createSupplier(SupplierDTO supplierdto);
List<Supplier> getAllSupplier();
Optional<Supplier> getSupplierById(Long id);
void deleteSupplier(Long id);
Optional<Supplier> getSupplierByUserId(Long userId);
}
