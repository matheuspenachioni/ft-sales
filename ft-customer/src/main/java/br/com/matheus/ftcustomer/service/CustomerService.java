package br.com.matheus.ftcustomer.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

	public Customer findCustomerById(Long idCustomer) {
		return customerRepository.findById(idCustomer).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Produto não encontrado!"));
	}

	public Customer saveCustomer(Customer customer) {
		if (validateCustomer(customer)) {
			return customerRepository.save(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"O preço de custo e preço de venda do produto são obrigatórios e devem ser maiores que 0 (zero)!");
		}
	}

	public Customer updateCustomer(Customer customer) {

		if (customer.getIdCustomer() == null || customer.getIdCustomer() <= 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"O ID do produto é obrigatório na atualização!");
		}

		if (validateCustomer(customer)) {
			return customerRepository.saveAndFlush(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"O preço de custo e preço de venda do produto são obrigatórios e devem ser maiores que 0 (zero)!");
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
		if (customer.getMonthlyIncomeCustomer() != null && customer.getMonthlyIncomeCustomer().compareTo(BigDecimal.valueOf(0)) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
