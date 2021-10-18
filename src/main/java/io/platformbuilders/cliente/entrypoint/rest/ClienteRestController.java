package io.platformbuilders.cliente.entrypoint.rest;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.platformbuilders.cliente.usecase.AlterarClienteUseCase;
import io.platformbuilders.cliente.usecase.AtualizarClienteUseCase;
import io.platformbuilders.cliente.usecase.CadastrarClienteUseCase;
import io.platformbuilders.cliente.usecase.ConsultarClientePorIdUseCase;
import io.platformbuilders.cliente.usecase.ConsultarClienteUseCase;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;
import io.platformbuilders.cliente.usecase.request.ConsultaClienteRequest;

@RestController
public class ClienteRestController {

	private final CadastrarClienteUseCase cadastrarClienteUseCase;
	private final ConsultarClienteUseCase consultarClienteUseCase;
	private final AlterarClienteUseCase alterarClienteUseCase;
	private final ConsultarClientePorIdUseCase consultarClientePorIdUseCase;
	private final AtualizarClienteUseCase atualizarClienteUseCase;

	public ClienteRestController(final CadastrarClienteUseCase cadastrarClienteUseCase,
			final ConsultarClienteUseCase consultarClienteUseCase, final AlterarClienteUseCase alterarClienteUseCase,
			final ConsultarClientePorIdUseCase consultarClientePorIdUseCase,
			final AtualizarClienteUseCase atualizarClienteUseCase) {
		this.cadastrarClienteUseCase = cadastrarClienteUseCase;
		this.consultarClienteUseCase = consultarClienteUseCase;
		this.alterarClienteUseCase = alterarClienteUseCase;
		this.consultarClientePorIdUseCase = consultarClientePorIdUseCase;
		this.atualizarClienteUseCase = atualizarClienteUseCase;
	}

	@PostMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody final CadastraClienteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.salvar(request));
	}

	@GetMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "rg", required = false) String rg,
			@RequestParam(value = "data_nascimento", required = false) String dataNascimento,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		var request = new ConsultaClienteRequest(nome, cpf, rg, dataNascimento, paginacao);
		return ResponseEntity.ok(consultarClienteUseCase.consultar(request));
	}

	@GetMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPor(@PathVariable("id") String id) {
		return ResponseEntity.ok(consultarClientePorIdUseCase.consultarPor(id));
	}

	/**
	 * Se o recurso de destino não tem uma representação atual e a requisição PUT
	 * foi criada com sucesso, então o servidor original deve informar o agente de
	 * usuário enviando uma resposta 201 (Created).
	 * 
	 * Se o recurso de destino tem uma representação atual e essa representação é
	 * modificada com sucesso de acordo com o estado de representação em anexo,
	 * então o servidor original deve enviar também uma resposta 200 (OK) or a 204
	 * (No Content) para indicar a conclusão da requisição.
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") String id,
			@Valid @RequestBody final AlteraClienteRequest request) {
		request.setId(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(alterarClienteUseCase.alterar(request));
	}

	@PatchMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
			@RequestBody final AtualizaClienteRequest request) {
		request.setId(id);
		atualizarClienteUseCase.atualizar(request);
		return ResponseEntity.noContent().build();
	}
}
