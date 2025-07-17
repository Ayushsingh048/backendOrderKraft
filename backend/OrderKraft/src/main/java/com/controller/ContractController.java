package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
