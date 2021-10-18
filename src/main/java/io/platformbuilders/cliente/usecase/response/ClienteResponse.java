package io.platformbuilders.cliente.usecase.response;

import java.time.LocalDate;

public class ClienteResponse {

	private String id;
	private String nome;
	private String rg;
	private String cpf;
	private LocalDate dataNascimento;
	private int idade;
	private String telefone;

	public ClienteResponse(String id, String nome, String rg, String cpf, LocalDate dataNascimento, int idade,
			String telefone) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.idade = idade;
		this.telefone = telefone;
	}

	public String getId() {
		return id;
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

	public int getIdade() {
		return idade;
	}

	public String getTelefone() {
		return telefone;
	}

}
