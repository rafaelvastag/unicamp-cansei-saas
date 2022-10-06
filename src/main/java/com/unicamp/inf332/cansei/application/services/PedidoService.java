package com.unicamp.inf332.cansei.application.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.unicamp.inf332.cansei.application.services.exceptions.AuthorizationException;
import com.unicamp.inf332.cansei.application.services.exceptions.ObjectNotFoundException;
import com.unicamp.inf332.cansei.crosscutting.security.UserSS;
import com.unicamp.inf332.cansei.domain.entities.Cliente;
import com.unicamp.inf332.cansei.domain.entities.ItemPedido;
import com.unicamp.inf332.cansei.domain.entities.PagamentoComBoleto;
import com.unicamp.inf332.cansei.domain.entities.Pedido;
import com.unicamp.inf332.cansei.domain.entities.enums.EstadoPagamento;
import com.unicamp.inf332.cansei.domain.repositories.ItemPedidoRepository;
import com.unicamp.inf332.cansei.domain.repositories.PagamentoRepository;
import com.unicamp.inf332.cansei.domain.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private IEmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}

	public Pedido updatePagamentoStatus(Integer id, int status) {

		Optional<Pedido> obj = repo.findById(id);
		var pedido = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));

		pedido.getPagamento().setEstado(EstadoPagamento.toEnum(status));
		clienteService.updatePontos(pedido);
		return repo.save(pedido);
	}
}
