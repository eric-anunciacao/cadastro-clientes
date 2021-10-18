package io.platformbuilders.cliente.usecase.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CadastraClienteRequest {

	@NotEmpty(message = "Campo 'nome' não pode estar vazio")
	private String nome;

	@NotEmpty(message = "Campo 'rg' não pode estar vazio")
	private String rg;

	@NotEmpty(message = "Campo 'cpf' não pode estar vazio")
	private String cpf;

	@NotNull(message = "Campo 'data_nascimento' não pode estar vazio")
	private LocalDate dataNascimento;

	private String telefone;

	public CadastraClienteRequest(String nome, String rg, String cpf, LocalDate dataNascimento, String telefone) {
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public String getRg() {
		return rg;
	}

	public String getCpf() {
		return cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

}
