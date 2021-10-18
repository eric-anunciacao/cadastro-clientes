package io.platformbuilders.cliente.infra.jpa.entity.builder;

import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;

public class ClienteJpaBuilder {

	private String id;
	private String nome;
	private String rg;
	private String cpf;
	private LocalDate dataNascimento;
	private String telefone;

	public ClienteJpaBuilder id(String id) {
		this.id = id;
		return this;
	}

	public ClienteJpaBuilder nome(String nome) {
		this.nome = nome;
		return this;
	}

	public ClienteJpaBuilder rg(String rg) {
		this.rg = rg;
		return this;
	}

	public ClienteJpaBuilder cpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

	public ClienteJpaBuilder dataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
	}

	public ClienteJpaBuilder telefone(String telefone) {
		this.telefone = telefone;
		return this;
	}

	public ClienteJpa build() {
		if (StringUtils.isEmpty(this.id)) {
			this.id = UUID.randomUUID().toString();
		}

		return new ClienteJpa(id, nome, rg, cpf, dataNascimento, telefone);
	}

}
