package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.ContractDTO;
import com.entity.Contract;

public interface ContractService {
Contract createContract(ContractDTO contractdto);
List<Contract> getallContract();
Optional<Contract> findContractId(Long id);
void deleteContract(Long id);
}
