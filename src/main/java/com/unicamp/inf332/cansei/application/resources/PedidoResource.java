package com.unicamp.inf332.cansei.application.resources;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unicamp.inf332.cansei.application.services.PedidoService;
import com.unicamp.inf332.cansei.domain.entities.Pedido;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Pedidos")
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	@ApiOperation(value = "Buscar pedido por ID.")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable @ApiParam(name="id", value="ID do pedido.", required=true) Integer id) {
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Atualizar status do pagamento do pedido por ID.")
	@RequestMapping(value = "/{id}/pagamento", method = RequestMethod.PATCH)
	public ResponseEntity<Pedido> atualizaStatusPagamento(
			@PathVariable @ApiParam(name="id", value="ID do pedido.", required=true) Integer id,
			@RequestBody  @ApiParam(name="status", value="Status do pagamento do pedido.", required=true) Map<String, String> status
	) {
		Pedido obj = service.updatePagamentoStatus(id, Integer.valueOf(status.get("status")));
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Cadastrar novo pedido.")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody @ApiParam(name="pedido", value="Informações do pedido a ser cadastrado.", required=true) Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Listar pedidos por critério.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value = "page", defaultValue = "0")           @ApiParam(name="page", value="Página (para paginação). Valor padrão = 0.", required = false, defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")  @ApiParam(name="linesPerPage", value="Limite da pagina. Valor padrão = 24.", required = false) Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") @ApiParam(name="orderBy", value="Critério de ordenação dos produtos. Valor padrão='instante'.", required=false) String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC")   @ApiParam(name="direction", value="Direção de ordenação dos produtos. Valor padrão='DESC'.", required=false) String direction
	) {
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
