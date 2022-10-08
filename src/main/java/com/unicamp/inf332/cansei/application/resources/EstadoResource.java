package com.unicamp.inf332.cansei.application.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.inf332.cansei.application.dto.CidadeDTO;
import com.unicamp.inf332.cansei.application.dto.EstadoDTO;
import com.unicamp.inf332.cansei.application.services.CidadeService;
import com.unicamp.inf332.cansei.application.services.EstadoService;
import com.unicamp.inf332.cansei.domain.entities.Cidade;
import com.unicamp.inf332.cansei.domain.entities.Estado;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Estados")
@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;

	@ApiOperation(value = "Listar todas as entidades federativas.")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = service.findAll();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Listar todas as cidades de uma entidade federativa.")
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(
			@PathVariable @ApiParam(name="estadoId", value="ID do estado que deseja listar as cidades.", required=true, example = "1") Integer estadoId
	) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}
