package com.sumanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
