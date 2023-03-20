package br.com.matheus.ftcustomer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.matheus.ftcustomer.entity.Customer;
import br.com.matheus.ftcustomer.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}
	
	public List<Customer> findAllCustomersByStatus(Boolean statusCustomer) {
		return customerRepository.findAllCustomersByStatus(statusCustomer);
	}

	public Customer findCustomerById(Long idCustomer) {
		return customerRepository.findById(idCustomer).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Customer not found!"));
	}

	public Customer saveCustomer(Customer customer) {
		if (validateCustomer(customer)) {
			return customerRepository.saveAndFlush(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"One or more fields are not valid!");
		}
	}

	public Customer updateCustomer(Customer customer) {
		if (customer.getIdCustomer() == null || customer.getIdCustomer() <= 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Customer ID is required in the update!");
		}

		if (validateCustomer(customer)) {
			//--
			Customer oldCustomer = findCustomerById(customer.getIdCustomer());
			customer.setDateCreatedCustomer(oldCustomer.getDateCreatedCustomer());
			customer.setDateUpdatedCustomer(LocalDateTime.now());
			//---
			return customerRepository.saveAndFlush(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"One or more fields are not valid!");
		}
	}

	public HashMap<String, Object> deleteCustomer(Long idCustomer) {
		Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(idCustomer).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!")));

		customerRepository.delete(customer.get());

		HashMap<String, Object> result = new  HashMap<String, Object> ();
		result.put("result", "Customer: "+ customer.get().getFirstNameCustomer() +" "+ customer.get().getLastNameCustomer() +" was successfully deleted!");

		return result;
	}

	public Boolean validateCustomer (Customer customer) {
		Optional<Customer> objCpf = customerRepository.findByCpfCustomer(customer.getCpfCustomer());
		Optional<Customer> objEmail = customerRepository.findByEmailCustomer(customer.getEmailCustomer());
		
		if (customer.getMonthlyIncomeCustomer() != null && 
			customer.getMonthlyIncomeCustomer().compareTo(BigDecimal.valueOf(0)) == 1
		   ) {
			return true;
		} else if (objCpf.isPresent() && objCpf.get().getIdCustomer() != customer.getIdCustomer() &&
				   objEmail.isPresent() && objEmail.get().getIdCustomer() != customer.getIdCustomer()) { 
			return false;
		} else {
			return false;
		}
	}

}
