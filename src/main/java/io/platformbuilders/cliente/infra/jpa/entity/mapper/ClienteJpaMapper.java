package io.platformbuilders.cliente.infra.jpa.entity.mapper;

import io.platformbuilders.cliente.domain.dto.AlteraClienteDto;
import io.platformbuilders.cliente.domain.dto.CadastraClienteDto;
import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;

public class ClienteJpaMapper {

	private ClienteJpaMapper() {
	}

	public static ClienteJpa toJpa(CadastraClienteDto dto) {
		if (dto == null) {
			return null;
		}

		return ClienteJpa.builder()
						.nome(dto.getNome())
						.rg(dto.getRg())
						.cpf(dto.getCpf())
						.dataNascimento(dto.getDataNascimento())
						.telefone(dto.getTelefone())
						.build();
	}

	public static ClienteJpa toJpa(AlteraClienteDto dto) {
		if (dto == null) {
			return null;
		}

		return ClienteJpa.builder()
						.id(dto.getId())
						.nome(dto.getNome())
						.rg(dto.getRg())
						.cpf(dto.getCpf())
						.dataNascimento(dto.getDataNascimento())
						.telefone(dto.getTelefone())
						.build();
	}

}
