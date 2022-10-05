package com.unicamp.inf332.cansei.application.dto;

import com.unicamp.inf332.cansei.domain.entities.Produto;

public class ProdutoPontosCarbonoDTO {

	private Integer id;
	private String nome;
	private int pontosCarbono;

	public ProdutoPontosCarbonoDTO() {
	}

	public ProdutoPontosCarbonoDTO(Produto obj) {
		id = obj.getId();
		nome = obj.getNome();
		this.pontosCarbono = obj.getCarbonoPontos();
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

	public int getPontosCarbono() {
		return pontosCarbono;
	}

	public void setPontosCarbono(int pontosCarbono) {
		this.pontosCarbono = pontosCarbono;
	}
}
