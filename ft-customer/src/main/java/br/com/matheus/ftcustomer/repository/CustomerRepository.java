package br.com.matheus.ftcustomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matheus.ftcustomer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
