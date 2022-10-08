package com.unicamp.inf332.cansei.application.dto;

import java.io.Serializable;

import com.unicamp.inf332.cansei.domain.entities.Produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ProdutoDTO", description="Produto")
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Identificação do produto.", example="1")
	private Integer id;
	@ApiModelProperty(value = "Nome do produto.")
	private String nome;
	@ApiModelProperty(value = "Valor do produto em BRL.", example="123.45")
	private Double preco;
	@ApiModelProperty(value = "Vendedor do produto.")
	private String vendedor;
	@ApiModelProperty(value = "Pontos de carbono.", example="100")
	private int pontosCarbono;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();

		if (obj.getVendedor() == null) {
			this.vendedor = "Vendedor não informado";
		} else {
			this.vendedor = obj.getVendedor().getNome();
		}
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

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
