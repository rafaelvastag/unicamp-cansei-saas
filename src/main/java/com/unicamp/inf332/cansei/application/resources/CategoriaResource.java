package com.unicamp.inf332.cansei.application.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unicamp.inf332.cansei.application.dto.CategoriaDTO;
import com.unicamp.inf332.cansei.application.services.CategoriaService;
import com.unicamp.inf332.cansei.domain.entities.Categoria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Categorias")
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@ApiOperation(value = "Buscar categoria por ID.", httpMethod = "GET")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(
			@PathVariable @ApiParam(name="id", value="ID da categoria.", required=true) Integer id
	) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Cadastrar nova categoria.", httpMethod = "POST")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(
			@Valid @RequestBody @ApiParam(name="categoria", value="Informações da categoria a ser cadastrada.", required=true) CategoriaDTO objDto
	) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar categoria por ID.", httpMethod = "PUT")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@Valid @RequestBody @ApiParam(name="categoria", value="Informações da categoria a ser atualizada.", required=true) CategoriaDTO objDto,
			@PathVariable @ApiParam(name="id", value="ID da categoria.", required=true) Integer id
	) {
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Remover categoria por ID.", httpMethod = "DELETE")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(
			@PathVariable  @ApiParam(name="id", value="ID da categoria.", required=true) Integer id
	) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Listar categorias por critério.")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")           @ApiParam(name="page", value="Página (para paginação). Valor padrão = 0.", required = false, defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")  @ApiParam(name="linesPerPage", value="Limite da pagina. Valor padrão = 24.", required = false) Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")     @ApiParam(name="orderBy", value="Critério de ordenação dos produtos. Valor padrão='nome'.", required=false) String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")    @ApiParam(name="direction", value="Direção de ordenação dos produtos. Valor padrão='ASC'.", required=false) String direction
	) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
}
