package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract,Long > {

}
