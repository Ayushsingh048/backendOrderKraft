package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.ContractDTO;
import com.entity.Contract;
import com.entity.Supplier;
import com.repository.ContractRepository;
import com.repository.SupplierRepository;

@Service
public class ContractServiceImpl implements ContractService {
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Override
	public Contract createContract(ContractDTO contractdto) {
		 Optional<Supplier> supplierOpt = supplierRepository.findById(contractdto.getSupplier_id());

	        if (supplierOpt.isEmpty()) {
	            throw new RuntimeException("Supplier not found with ID: " + contractdto.getSupplier_id());
	        }

	        Contract contract = new Contract();
	        contract.setSupplier(supplierOpt.get());
	        contract.setStart_date(contractdto.getStart_date());
	        contract.setEnd_date(contractdto.getEnd_date());
	        contract.setTerms(contractdto.getTerms());

	        return contractRepository.save(contract);
	}

	@Override
	public List<Contract> getallContract() {
		// TODO Auto-generated method stub
		return contractRepository.findAll();
	}

	@Override
	public Optional<Contract> findContractById(Long id) {
		return contractRepository.findById(id);
               }

	@Override
	public void deleteContract(Long id) {
		// Delete contract
		contractRepository.deleteById(id);
		
	}

}
