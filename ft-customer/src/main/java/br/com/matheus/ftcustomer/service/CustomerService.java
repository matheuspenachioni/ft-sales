package br.com.matheus.ftcustomer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.com.matheus.ftcustomer.entity.Customer;
import br.com.matheus.ftcustomer.entity.dto.AddressDTO;
import br.com.matheus.ftcustomer.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
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
			customer.setPasswordCustomer(encoder.encode(customer.getPasswordCustomer()));
			
			for (int i = 0; i < customer.getAddresses().size(); i++) {
				AddressDTO addressDTO = searchCep(customer.getAddresses().get(i).getCep());
				
				customer.getAddresses().get(i).setLogradouro(addressDTO.getLogradouro());
				customer.getAddresses().get(i).setComplemento(addressDTO.getComplemento());
				customer.getAddresses().get(i).setBairro(addressDTO.getBairro());
				customer.getAddresses().get(i).setLocalidade(addressDTO.getLocalidade());
				customer.getAddresses().get(i).setUf(addressDTO.getUf());
			}
			
			return customerRepository.saveAndFlush(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"One or more fields are not valid!");
		}
	}

	public Customer updateCustomer(Customer customer) {
		if (customer.getIdCustomer() == null || customer.getIdCustomer() <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Customer ID is required in the update!");
		}
		
		if (validateCustomer(customer)) {
			encryptPassword(customer);
			customer.setDateUpdatedCustomer(LocalDateTime.now());
			return customerRepository.saveAndFlush(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"One or more fields are not valid!");
		}
	}

	public HashMap<String, Object> deleteCustomer(Long idCustomer) {
		Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(idCustomer).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found!")));

		customerRepository.delete(customer.get());

		HashMap<String, Object> result = new  HashMap<String, Object> ();
		result.put("result", "Customer: "+ customer.get().getFirstNameCustomer() +" "+ customer.get().getLastNameCustomer() +" was successfully deleted!");

		return result;
	}

	public Boolean validateCustomer (Customer customer) {
		if (customer.getMonthlyIncomeCustomer() != null && 
			customer.getMonthlyIncomeCustomer().compareTo(BigDecimal.valueOf(0)) == 1
		   ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void encryptPassword (Customer customer) {
		Optional<Customer> oldOBJ = customerRepository.findById(customer.getIdCustomer());
		
		if (!customer.getPasswordCustomer().equals(oldOBJ.get().getPasswordCustomer())) {
			customer.setPasswordCustomer(encoder.encode(customer.getPasswordCustomer()));
		} 
	}
	
	public AddressDTO searchCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", AddressDTO.class).getBody();
	}

}
