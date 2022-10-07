package com.unicamp.inf332.cansei.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.inf332.cansei.application.dto.ProdutoDTO;
import com.unicamp.inf332.cansei.application.dto.ProdutoPontosCarbonoDTO;
import com.unicamp.inf332.cansei.application.resources.utils.URL;
import com.unicamp.inf332.cansei.application.services.ProdutoService;
import com.unicamp.inf332.cansei.domain.entities.Produto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@Api(tags="Produtos")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@ApiOperation(value = "Buscar produto por ID.", httpMethod = "GET")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(
			@PathVariable @ApiParam(name="id", value="ID do produto.", required=true) Integer id
	) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Buscar pontos de carbono por ID do produto.")
	@RequestMapping(value = "/{id}/pontosdecarbono", method = RequestMethod.GET)
	public ResponseEntity<ProdutoPontosCarbonoDTO> findPontosDeCarbonoByProdutoId(
			@PathVariable @ApiParam(name="id", value="ID do produto.", required=true) Integer id
	) {
		Produto obj = service.findPontosDeCarbonoBy(id);
		return ResponseEntity.ok().body(new ProdutoPontosCarbonoDTO(obj));
	}

	@ApiOperation(value = "Listar produtos por critério.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "")            @ApiParam(name="nome", value="Nome do produto.", required=false) String nome,
			@RequestParam(value = "categorias", defaultValue = "")      @ApiParam(name="categorias", value="Categorias do produto.", required=false) String categorias,
			@RequestParam(value = "page", defaultValue = "0")           @ApiParam(name="page", value="Página (para paginação). Valor padrão = 0.", required = false, defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")  @ApiParam(name="linesPerPage", value="Limite da pagina. Valor padrão = 24.", required = false) Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")     @ApiParam(name="orderBy", value="Critério de ordenação dos produtos. Valor padrão='nome'.", required=false) String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")    @ApiParam(name="direction", value="Direção de ordenação dos produtos. Valor padrão='ASC'.", required=false) String direction
	) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}
}
