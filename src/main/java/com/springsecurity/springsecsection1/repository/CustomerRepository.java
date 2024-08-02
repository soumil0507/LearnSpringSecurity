package com.springsecurity.springsecsection1.repository;

import com.springsecurity.springsecsection1.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

//    defining abstract method to fetch users by email

    Optional<Customer> findByEmail(String email);
}

