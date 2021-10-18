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
	 * Método responsável por cadastrar um cliente na base de dados.
	 * 
	 * @param request contendo os dados cadastrais do cliente.
	 * @return Código 201 (Created) retornando os dados cadastrais que foram
	 *         informados na requisição. Caso haja algum problema, será retornado o
	 *         código 422.
	 */
	@PostMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody final CadastraClienteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.cadastrar(request));
	}

	/**
	 * Método que consulta os clientes, permitindo consultá-los por nome, cpf, rg e
	 * data de nascimento.
	 * 
	 * A consulta lista os clientes de forma paginada, ordenando por padrão pelo
	 * campo "id". Caso haja necessidade de alterar o campo de ordenação, pode-se
	 * informar o nome do campo utilizando o parâmetro "sort" na URL.
	 * 
	 * Os parâmetros "page" e "size" podem ser utilizados para alterar a página e a
	 * quantidade de registros, respectivamente.
	 * 
	 * @param nome           string com o nome do cliente a ser consultado (pode ser
	 *                       passado o nome completo ou parte dele).
	 * @param cpf            string contendo o CPF do cliente.
	 * @param rg             string contendo o RG do cliente.
	 * @param dataNascimento string contendo a data de nascimento do cliente no
	 *                       formato "yyyy-MM-dd"
	 * @param paginacao      informações da paginação do Spring
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
	 * Método que consulta um determinado cliente por código único.
	 * 
	 * @param id string contendo o código único do cliente.
	 * @return dados cadastrais do cliente.
	 */
	@GetMapping(path = "/clientes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPor(@PathVariable("id") String id) {
		return ResponseEntity.ok(consultarClientePorIdUseCase.consultarPor(id));
	}

	/**
	 * Método que realiza a atualização de dados do cliente por completo.
	 * 
	 * @param id      string contendo o código único do cliente
	 * @param request que contém os dados do cliente que serão alterados por
	 *                completo
	 * @return Se o recurso de destino não tem uma representação atual e a
	 *         requisição PUT foi criada com sucesso, então o servidor deve informar
	 *         o agente de usuário enviando uma resposta 201 (Created).
	 * 
	 *         Se o recurso de destino tem uma representação atual e essa
	 *         representação é modificada com sucesso de acordo com o estado de
	 *         representação em anexo, então o servidor deve enviar uma resposta 200
	 *         (OK) ou 204 (No Content) para indicar a conclusão da requisição.
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
	 * Método que realiza a atualização dos dados do cliente de forma granular.
	 * 
	 * @param id      string contendo o código único do cliente.
	 * @param request que contém os dados que devem ser atualizados.
	 * @return resposta 204 (No content) quando a inclusão for realizada com
	 *         sucesso. Em caso de erro de validação, será retornado o código padrão
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
