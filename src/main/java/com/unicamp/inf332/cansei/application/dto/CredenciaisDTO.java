package com.unicamp.inf332.cansei.application.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="CredenciaisDTO", description="Credenciais")
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "E-mail de cadastro.")
	private String email;

	@ApiModelProperty(value = "Senha.")
	private String senha;
	
	public CredenciaisDTO() {
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
