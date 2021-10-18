package io.platformbuilders.cliente.usecase.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlteraClienteRequest {

	private String id;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String rg;

	@NotEmpty
	private String cpf;

	@NotNull
	private LocalDate dataNascimento;

	private String telefone;

	public AlteraClienteRequest(String nome, String rg, String cpf, LocalDate dataNascimento, String telefone) {
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
