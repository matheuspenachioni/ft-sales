package br.com.matheus.ftcustomer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_address")
	private Long idAddress;
	
	@Column(name = "cep_address")
	private String cepAddress;
	
	@Column(name = "patio_address")
	private String patioAddress;
	
	@Column(name = "complement_address")
	private String complementAddress;
	
	@Column(name = "district_address")
	private String districtAddress;
	
	@Column(name = "locality_address")
	private String localityAddress;
	
	@Column(name = "uf_address")
	private String ufAddress;
	
}
