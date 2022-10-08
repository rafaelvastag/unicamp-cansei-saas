package com.unicamp.inf332.cansei.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.unicamp.inf332.cansei.application.services.validation.ClienteInsert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ClienteNewDTO", description="Cadastro de novo cliente.")
@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Nome do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@ApiModelProperty(value = "E-mail do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;

	@ApiModelProperty(value = "CPF ou CNPJ do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpfOuCnpj;

	@ApiModelProperty(value = "Tipo do cliente.", example="1")
	private Integer tipo;

	@ApiModelProperty(value = "Senha.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	@ApiModelProperty(value = "Logradouro do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String logradouro;

	@ApiModelProperty(value = "Número do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String numero;

	@ApiModelProperty(value = "Complemento do cliente.")
	private String complemento;

	@ApiModelProperty(value = "Bairro do cliente.")
	private String bairro;

	@ApiModelProperty(value = "Cep do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cep;

	@ApiModelProperty(value = "Telefone 1 de contato do cliente.", allowEmptyValue = false)
	@NotEmpty(message = "Preenchimento obrigatório")
	private String telefone1;

	@ApiModelProperty(value = "Telefone 2 de contato do cliente.")
	private String telefone2;

	@ApiModelProperty(value = "Telefone 3 de contato do cliente.")
	private String telefone3;

	@ApiModelProperty(value = "ID da cidade do cliente.", example="1")
	private Integer cidadeId;

	@ApiModelProperty(value = "Pontos do cliente.")
	private int pontos;

	public ClienteNewDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
