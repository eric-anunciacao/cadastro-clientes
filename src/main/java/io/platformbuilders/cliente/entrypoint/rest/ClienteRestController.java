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

	/**
	 * M??todo respons??vel por cadastrar um cliente na base de dados.
	 * 
	 * @param request contendo os dados cadastrais do cliente.
	 * @return C??digo 201 (Created) retornando os dados cadastrais que foram
	 *         informados na requisi????o. Caso haja algum problema, ser?? retornado o
	 *         c??digo 422.
	 */
	@PostMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody final CadastraClienteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.cadastrar(request));
	}

	/**
	 * M??todo que consulta os clientes, permitindo consult??-los por nome, cpf, rg e
	 * data de nascimento.
	 * 
	 * A consulta lista os clientes de forma paginada, ordenando por padr??o pelo
	 * campo "id". Caso haja necessidade de alterar o campo de ordena????o, pode-se
	 * informar o nome do campo utilizando o par??metro "sort" na URL.
	 * 
	 * Os par??metros "page" e "size" podem ser utilizados para alterar a p??gina e a
	 * quantidade de registros, respectivamente.
	 * 
	 * @param nome           string com o nome do cliente a ser consultado (pode ser
	 *                       passado o nome completo ou parte dele).
	 * @param cpf            string contendo o CPF do cliente.
	 * @param rg             string contendo o RG do cliente.
	 * @param dataNascimento string contendo a data de nascimento do cliente no
	 *                       formato "yyyy-MM-dd"
	 * @param paginacao      informa????es da pagina????o do Spring
	 * @return
	 */
	@GetMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "rg", required = false) String rg,
			@RequestParam(value = "data_nascimento", required = false) String dataNascimento,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		var request = new ConsultaClienteRequest(nome, cpf, rg, dataNascimento, paginacao);
		return ResponseEntity.ok(consultarClienteUseCase.consultar(request));
	}

	/**
	 * M??todo que consulta um determinado cliente por c??digo ??nico.
	 * 
	 * @param id string contendo o c??digo ??nico do cliente.
	 * @return dados cadastrais do cliente.
	 */
	@GetMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPor(@PathVariable("id") String id) {
		return ResponseEntity.ok(consultarClientePorIdUseCase.consultarPor(id));
	}

	/**
	 * M??todo que realiza a atualiza????o de dados do cliente por completo.
	 * 
	 * @param id      string contendo o c??digo ??nico do cliente
	 * @param request que cont??m os dados do cliente que ser??o alterados por
	 *                completo
	 * @return Se o recurso de destino n??o tem uma representa????o atual e a
	 *         requisi????o PUT foi criada com sucesso, ent??o o servidor deve informar
	 *         o agente de usu??rio enviando uma resposta 201 (Created).
	 * 
	 *         Se o recurso de destino tem uma representa????o atual e essa
	 *         representa????o ?? modificada com sucesso de acordo com o estado de
	 *         representa????o em anexo, ent??o o servidor deve enviar uma resposta 200
	 *         (OK) ou 204 (No Content) para indicar a conclus??o da requisi????o.
	 */
	@PutMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterar(@PathVariable("id") String id,
			@Valid @RequestBody final AlteraClienteRequest request) {
		request.setId(id);
		var response = alterarClienteUseCase.alterar(request);

		if (response.isNovo()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response.getClienteResponse());
		} else {
			return ResponseEntity.ok(response.getClienteResponse());
		}
	}

	/**
	 * M??todo que realiza a atualiza????o dos dados do cliente de forma granular.
	 * 
	 * @param id      string contendo o c??digo ??nico do cliente.
	 * @param request que cont??m os dados que devem ser atualizados.
	 * @return resposta 204 (No content) quando a inclus??o for realizada com
	 *         sucesso. Em caso de erro de valida????o, ser?? retornado o c??digo padr??o
	 *         422.
	 */
	@PatchMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
			@RequestBody final AtualizaClienteRequest request) {
		request.setId(id);
		atualizarClienteUseCase.atualizar(request);
		return ResponseEntity.noContent().build();
	}
}
