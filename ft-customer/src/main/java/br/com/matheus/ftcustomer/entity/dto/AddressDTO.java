package br.com.matheus.ftcustomer.entity.dto;

import java.io.Serializable;

import br.com.matheus.ftcustomer.entity.Address;
import lombok.*;

@Data
@NoArgsConstructor
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String cep;
	protected String logradouro;
	protected String complemento;
	protected String bairro;
	protected String localidade;
	protected String uf;
	
	public AddressDTO(Address obj) {
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
