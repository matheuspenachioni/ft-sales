package br.com.matheus.ftcustomer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.matheus.ftcustomer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByCpfCustomer(String cpfCustomer);
	Optional<Customer> findByEmailCustomer(String emailCustomer);
	
	@Query(value = "SELECT * FROM customer c WHERE c.status_customer = ?", nativeQuery = true)
	List<Customer> findAllCustomersByStatus(Boolean statusCustomer);
}