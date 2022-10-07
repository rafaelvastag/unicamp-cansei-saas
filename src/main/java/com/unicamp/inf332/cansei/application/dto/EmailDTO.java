package com.unicamp.inf332.cansei.application.dto;
import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="EmailDTO", description="E-mail")
public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "E-mail de cadastro.")
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	public EmailDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
