package io.platformbuilders.cliente.domain.dto.mapper;

import org.apache.commons.lang3.StringUtils;

import io.platformbuilders.cliente.domain.dto.AlteraClienteDto;
import io.platformbuilders.cliente.domain.entity.Cliente;
import io.platformbuilders.cliente.usecase.request.AlteraClienteRequest;
import io.platformbuilders.cliente.usecase.request.AtualizaClienteRequest;

public class AlteraClienteDtoMapper {

	private AlteraClienteDtoMapper() {
	}

	public static AlteraClienteDto toDto(AlteraClienteRequest request) {
		if (request == null) {
			return null;
		}

		return new AlteraClienteDto(request.getId(), request.getNome(), request.getRg(), request.getCpf(),
				request.getDataNascimento(), request.getTelefone());
	}

	public static AlteraClienteDto toDto(Cliente cliente, AtualizaClienteRequest request) {
		if (cliente == null || request == null) {
			return null;
		}

		var id = cliente.getId();
		var nome = cliente.getNome();
		var rg = cliente.getRg();
		var cpf = cliente.getCpf();
		var dataNascimento = cliente.getDataNascimento();
		var telefone = cliente.getTelefone();

		if (StringUtils.isNotEmpty(request.getNome())) {
			nome = request.getNome();
		}

		if (StringUtils.isNotEmpty(request.getRg())) {
			rg = request.getRg();
		}

		if (StringUtils.isNotEmpty(request.getCpf())) {
			cpf = request.getCpf();
		}

		if (request.getDataNascimento() != null) {
			dataNascimento = request.getDataNascimento();
		}

		if (StringUtils.isNotEmpty(request.getTelefone())) {
			telefone = request.getTelefone();
		}

		return new AlteraClienteDto(id, nome, rg, cpf, dataNascimento, telefone);
	}

}
