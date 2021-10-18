package io.platformbuilders.cliente.domain.dto.mapper;

import io.platformbuilders.cliente.domain.dto.CadastraClienteDto;
import io.platformbuilders.cliente.usecase.request.CadastraClienteRequest;

public class CadastraClienteDtoMapper {

	private CadastraClienteDtoMapper() {
	}

	public static CadastraClienteDto toDto(CadastraClienteRequest request) {
		if (request == null) {
			return null;
		}
		return new CadastraClienteDto(request.getNome(), request.getRg(), request.getCpf(), request.getDataNascimento(),
				request.getTelefone());
	}

}
