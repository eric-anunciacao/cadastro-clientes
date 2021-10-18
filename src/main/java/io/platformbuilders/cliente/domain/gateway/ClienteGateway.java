package io.platformbuilders.cliente.domain.gateway;

import java.util.Optional;
import java.util.Set;

import io.platformbuilders.cliente.domain.dto.AlteraClienteDto;
import io.platformbuilders.cliente.domain.dto.CadastraClienteDto;
import io.platformbuilders.cliente.domain.dto.ConsultaClienteDto;
import io.platformbuilders.cliente.domain.entity.Cliente;

public interface ClienteGateway {

	Cliente salvar(CadastraClienteDto dto);

	Set<Cliente> consultar(ConsultaClienteDto dto);

	Optional<Cliente> consultarPor(String id);

	Cliente alterar(AlteraClienteDto dto);

	boolean jaCadastrado(String cpf);

	Optional<Cliente> consultarPorCpf(String cpf);

}
