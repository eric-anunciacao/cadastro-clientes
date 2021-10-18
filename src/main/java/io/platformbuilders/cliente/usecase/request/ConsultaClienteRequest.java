package io.platformbuilders.cliente.usecase.request;

import org.springframework.data.domain.Pageable;

public class ConsultaClienteRequest {

	private String nome;
	private String cpf;
	private String rg;
	private String dataNascimento;
	private Pageable paginacao;

	public ConsultaClienteRequest(String nome, String cpf, String rg, String dataNascimento, Pageable paginacao) {
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public Pageable getPaginacao() {
		return paginacao;
	}

}
