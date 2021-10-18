package io.platformbuilders.cliente.infra.jpa.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.platformbuilders.cliente.infra.jpa.entity.builder.ClienteJpaBuilder;

@Entity
@Table(name = "CLIENTE")
public class ClienteJpa implements Serializable {

	private static final long serialVersionUID = 2017401555296518182L;

	@Id
	private String id;
	private String nome;
	private String rg;
	private String cpf;
	private LocalDate dataNascimento;
	private String telefone;

	public static ClienteJpaBuilder builder() {
		return new ClienteJpaBuilder();
	}

	public ClienteJpa() {
	}

	public ClienteJpa(String id, String nome, String rg, String cpf, LocalDate dataNascimento, String telefone) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
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

	public String getTelefone() {
		return telefone;
	}

}
