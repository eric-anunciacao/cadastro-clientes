package io.platformbuilders.cliente.domain.dto.mapper;

import io.platformbuilders.cliente.cross.Formatador;
import io.platformbuilders.cliente.domain.dto.ConsultaClienteDto;
import io.platformbuilders.cliente.usecase.request.ConsultaClienteRequest;

public class ConsultaClienteDtoMapper {

	private ConsultaClienteDtoMapper() {
	}

	public static ConsultaClienteDto toDto(ConsultaClienteRequest request) {
		if (request == null) {
			return null;
		}
		var dataNascimento = Formatador.toLocalDate(request.getDataNascimento());
		return new ConsultaClienteDto(request.getNome(), request.getCpf(), request.getRg(), dataNascimento,
				request.getPaginacao());
	}

}
