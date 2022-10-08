package com.unicamp.inf332.cansei.application.dto;

import java.io.Serializable;

import com.unicamp.inf332.cansei.domain.entities.Estado;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="EstadoDTO", description="Estado")
public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Identificação do estado.", example="1")
	private Integer id;
	@ApiModelProperty(value = "Nome do estado.")
	private String nome;
	
	public EstadoDTO() {
	}

	public EstadoDTO(Estado obj) {
		id = obj.getId();
		nome = obj.getNome();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
