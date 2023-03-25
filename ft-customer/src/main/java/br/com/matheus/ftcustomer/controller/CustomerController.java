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
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1/customers")
@Api(value = "REST API to Customer Management")
@CrossOrigin("*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@ApiOperation(value = "List of all existing Customers")
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
	
	@ApiOperation(value = "Fetches all Customers with 'true' or 'false' status by QUERY PARAM")
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
	
	@ApiOperation(value = "Search a Customer’s information by ID")
	@GetMapping(value = "/{idCustomer}/details")
	public ResponseEntity<Object> getCustomerById(@PathVariable Long idCustomer) {
		Customer result = customerService.findCustomerById(idCustomer);

		result.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(result.getIdCustomer())).withSelfRel());
		result.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
		
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@ApiOperation(value = "Saves the information of a new Customer")
	@PostMapping(value = "/save")
	public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
		Customer result = customerService.saveCustomer(customer);
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@ApiOperation(value = "Updates the information of an existing Customer by ID")
	@PutMapping(value = "/{idCustomer}/update")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer) {
		Customer result = customerService.updateCustomer(customer);
		return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
	
	@ApiOperation(value = "Delete a Customer by ID")
	@DeleteMapping(value = "/{idCustomer}/delete")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long idCustomer) {
		HashMap<String, Object> result = customerService.deleteCustomer(idCustomer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
	}
}
