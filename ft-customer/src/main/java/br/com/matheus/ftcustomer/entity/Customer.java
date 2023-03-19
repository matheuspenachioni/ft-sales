
package br.com.matheus.ftcustomer.entity;

import java.math.BigDecimal;
import java.time.*;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

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
    
	@CPF
	@Column(name = "cpf_customer", nullable = false, unique = true)
    private String cpfCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birth_date_customer")
    private LocalDate birthDateCustomer;
    
    @Column(name = "monthly_income_customer", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyIncomeCustomer;
    
    @Column(name = "email_customer", nullable = false, unique = true)
    private String emailCustomer;
    
    @Column(name = "password_customer", nullable = false)
    private String passwordCustomer;
    
    @Column(name = "status_customer")
    private Boolean statusCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_created_customer")
    private LocalDateTime dateCreatedCustomer;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_updated_customer")
    private LocalDateTime dateUpdatedCustomer;
    
    @PrePersist
    public void prePersist() {
    	setDateCreatedCustomer(LocalDateTime.now());
    	setDateUpdatedCustomer(LocalDateTime.now());
    	setStatusCustomer(true);
    }

}
