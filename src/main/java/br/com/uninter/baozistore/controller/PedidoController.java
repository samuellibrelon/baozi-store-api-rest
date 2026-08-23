package br.com.uninter.baozistore.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.uninter.baozistore.model.Pedido;
import br.com.uninter.baozistore.repository.ClienteRepository;
import br.com.uninter.baozistore.repository.PedidoRepository;
import br.com.uninter.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        validarReferencias(pedido);
        pedido.setId(null);
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.created(URI.create("/pedidos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido consultarPorId(@PathVariable Long id) {
        return buscarPedido(id);
    }

    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        Pedido pedido = buscarPedido(id);
        validarReferencias(dados);
        pedido.setClienteId(dados.getClienteId());
        pedido.setProdutoId(dados.getProdutoId());
        pedido.setQuantidade(dados.getQuantidade());
        return pedidoRepository.save(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        Pedido pedido = buscarPedido(id);
        pedidoRepository.delete(pedido);
        return ResponseEntity.noContent().build();
    }

    private Pedido buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    private void validarReferencias(Pedido pedido) {
        if (!clienteRepository.existsById(pedido.getClienteId())) {
            throw new ResponseStatusException(BAD_REQUEST, "O cliente informado não existe");
        }
        if (!produtoRepository.existsById(pedido.getProdutoId())) {
            throw new ResponseStatusException(BAD_REQUEST, "O produto informado não existe");
        }
    }
}
