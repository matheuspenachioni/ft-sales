package br.com.matheus.ftcustomer.entity;

import br.com.matheus.ftcustomer.entity.dto.AddressDTO;
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
	private Long id;
	
	@Column(name = "cep_address")
	private String cep;
	
	@Column(name = "patio_address")
	private String logradouro;
	
	@Column(name = "complement_address")
	private String complemento;
	
	@Column(name = "district_address")
	private String bairro;
	
	@Column(name = "locality_address")
	private String localidade;
	
	@Column(name = "uf_address")
	private String uf;
	
	public Address(AddressDTO obj) {
		super();
		this.id = obj.getId();
		this.cep = obj.getCep();
		this.logradouro = obj.getLogradouro();
		this.complemento = obj.getComplemento();
		this.bairro = obj.getBairro();
		this.localidade = obj.getLocalidade();
		this.uf = obj.getUf();
	}
	
}
