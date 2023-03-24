package br.com.matheus.ftcustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import br.com.matheus.ftcustomer.entity.Customer;
import br.com.matheus.ftcustomer.entity.dto.AddressDTO;
import br.com.matheus.ftcustomer.exception.ResponseGenericException;
import br.com.matheus.ftcustomer.service.CustomerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/v1/customers")
@Api(value = "REST API to Customer Management")
@CrossOrigin("*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value = "/list")
	public ResponseEntity<Object> getAllCustomers() {
		List<Customer> result = customerService.findAllCustomers();

		int i = 0;
		for(Customer customer : result) {
			customer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customer.getIdCustomer())).withSelfRel());
			i++;
		}

		return ResponseEntity.ok().body(ResponseGenericException.response(result, i));
	}
	
	@GetMapping(value = "/results")
	public ResponseEntity<Object> getAllActiveCustomers(@RequestParam Boolean statusCustomer) {
		List<Customer> result = customerService.findAllCustomersByStatus(statusCustomer);

		int i = 0;
		for(Customer customer : result) {
			customer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customer.getIdCustomer())).withSelfRel());
			i++;
		}

		return ResponseEntity.ok().body(ResponseGenericException.response(result, i));
	}
	
	@GetMapping(value = "/{cep}/info")
	public ResponseEntity<Object> getInfoCep(@PathVariable String cep) {
		AddressDTO result = customerService.searchCep(cep);
		
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@GetMapping(value = "/{idCustomer}/details")
	public ResponseEntity<Object> getCustomerById(@PathVariable Long idCustomer) {
		Customer result = customerService.findCustomerById(idCustomer);

		result.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(result.getIdCustomer())).withSelfRel());
		result.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
		
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
		Customer result = customerService.saveCustomer(customer);
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@PutMapping(value = "/{idCustomer}/update")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer) {
		Customer result = customerService.updateCustomer(customer);
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@DeleteMapping(value = "/{idCustomer}/delete")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long idCustomer) {
		HashMap<String, Object> result = customerService.deleteCustomer(idCustomer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
}
