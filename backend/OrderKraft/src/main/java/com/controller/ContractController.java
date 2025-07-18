package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ContractDTO;
import com.entity.Contract;
import com.service.ContractService;

@RestController
@RequestMapping("/contract")
public class ContractController {
	
	@Autowired
	ContractService contractservice;
	
	@PostMapping("/add")
	public ResponseEntity<Contract> createContract(@RequestBody ContractDTO contractdto)
	{
		return ResponseEntity.ok(contractservice.createContract(contractdto));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Contract>> fetchAll()
	{
		return ResponseEntity.ok(contractservice.getallContract());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Contract>> getContractById(@PathVariable Long id)
	{
		return ResponseEntity.ok(contractservice.findContractById(id));
//		return null;
	}
}
