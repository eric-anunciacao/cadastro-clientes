package io.platformbuilders.cliente.domain.dto;

import java.time.LocalDate;

public class CadastraClienteDto {

	private String nome;
	private String rg;
	private String cpf;
	private LocalDate dataNascimento;
	private String telefone;

	public CadastraClienteDto(String nome, String rg, String cpf, LocalDate dataNascimento, String telefone) {
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
