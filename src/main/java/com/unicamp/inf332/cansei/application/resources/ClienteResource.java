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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unicamp.inf332.cansei.application.dto.ClienteDTO;
import com.unicamp.inf332.cansei.application.dto.ClienteNewDTO;
import com.unicamp.inf332.cansei.application.services.ClienteService;
import com.unicamp.inf332.cansei.domain.entities.Cliente;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Clientes")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@ApiOperation(value = "Buscar cliente por ID.", httpMethod = "GET")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(
			@PathVariable @ApiParam(name="id", value="ID do cliente.", required=true) Integer id
	) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Buscar pontos de carbono do cliente por ID.", httpMethod = "GET")
	@RequestMapping(value = "/{id}/pontosdecarbono", method = RequestMethod.GET)
	public ResponseEntity<Integer> getPontosDeCarbono(
			@PathVariable @ApiParam(name="id", value="ID do cliente.", required=true) Integer id
	) {
		var obj = service.findPontosDeCarbonoBy(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Buscar cliente por E-mail de cadastro.", httpMethod = "GET")
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(
			@RequestParam(value = "value") @ApiParam(name="email", value="E-mail do cliente.", required=true) String email
	) {
		Cliente obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Cadastrar novo cliente.", httpMethod = "POST")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(
			@Valid @RequestBody @ApiParam(name="cliente", value="Informações do cliente ser cadastrado.", required=true) ClienteNewDTO objDto
	) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar cliente por ID.", httpMethod = "PUT")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@Valid @RequestBody @ApiParam(name="cliente", value="Informações do cliente ser atualizado.", required=true) ClienteDTO objDto,
			@PathVariable @ApiParam(name="id", value="ID do cliente.", required=true) Integer id
	) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Remover cliente por ID.", httpMethod = "DELETE")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(
			@PathVariable @ApiParam(name="id", value="ID do cliente.", required=true) Integer id
	) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Listar clientes por critério.")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")           @ApiParam(name="page", value="Página (para paginação). Valor padrão = 0.", required = false, defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")  @ApiParam(name="linesPerPage", value="Limite da pagina. Valor padrão = 24.", required = false) Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")     @ApiParam(name="orderBy", value="Critério de ordenação dos produtos. Valor padrão='nome'.", required=false) String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")    @ApiParam(name="direction", value="Direção de ordenação dos produtos. Valor padrão='ASC'.", required=false) String direction
	) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Cadastrar foto de perfil.")
	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(
			@RequestParam(name = "file") MultipartFile file
	) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
