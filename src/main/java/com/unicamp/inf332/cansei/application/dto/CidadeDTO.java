package com.unicamp.inf332.cansei.application.dto;
import java.io.Serializable;

import com.unicamp.inf332.cansei.domain.entities.Cidade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="CidadeDTO", description="Cidade")
public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Identificação da cidade.", example="1")
	private Integer id;
	@ApiModelProperty(value = "Nome da cidade.")
	private String nome;
	
	public CidadeDTO() {
	}

	public CidadeDTO(Cidade obj) {
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
