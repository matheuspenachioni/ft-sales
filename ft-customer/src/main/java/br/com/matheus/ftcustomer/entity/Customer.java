package br.com.matheus.ftcustomer.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_customer")
	private Long idCustomer;
	
	@Column(name = "first_name_customer", nullable = false, length = 300)
    private String firstNameCustomer;
    
	@Column(name = "last_name_customer", nullable = false, length = 300)
    private String lastNameCustomer;
    
	@Column(name = "cpf_customer", nullable = false, unique = true)
    private String cpfCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "birth_date_customer", nullable = false)
    private LocalDate birthDateCustomer;
    
    @Column(name = "monthly_income_customer", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyIncomeCustomer;
    
    @Column(name = "email_customer", nullable = false, unique = true)
    private String emailCustomer;
    
    @Column(name = "password_customer", nullable = false)
    private String passwordCustomer;
    
    @Column(name = "status_customer", nullable = false)
    private Boolean statusCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_created_customer")
    private LocalDateTime dateCreatedCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_updated_customer")
    private LocalDateTime dateUpdatedCustomer;
    
    @PrePersist
    private void prePersist() {
    	setDateCreatedCustomer(LocalDateTime.now());
    	setDateUpdatedCustomer(LocalDateTime.now());
    	setStatusCustomer(true);
    }

}
