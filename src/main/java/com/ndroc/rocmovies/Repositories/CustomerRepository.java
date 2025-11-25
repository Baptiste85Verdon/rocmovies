package com.ndroc.rocmovies.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ndroc.rocmovies.Entity.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
}
