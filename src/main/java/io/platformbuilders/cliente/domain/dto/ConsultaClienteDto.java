package io.platformbuilders.cliente.domain.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

public class ConsultaClienteDto {

	private String nome;
	private String cpf;
	private String rg;
	private LocalDate dataNascimento;
	private Pageable paginacao;

	public ConsultaClienteDto(String nome, String cpf, String rg, LocalDate dataNascimento, Pageable paginacao) {
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.paginacao = paginacao;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Pageable getPaginacao() {
		return paginacao;
	}

}
