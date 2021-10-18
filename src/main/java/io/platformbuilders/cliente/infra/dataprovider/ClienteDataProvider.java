package io.platformbuilders.cliente.infra.dataprovider;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.platformbuilders.cliente.domain.dto.AlteraClienteDto;
import io.platformbuilders.cliente.domain.dto.CadastraClienteDto;
import io.platformbuilders.cliente.domain.dto.ConsultaClienteDto;
import io.platformbuilders.cliente.domain.entity.Cliente;
import io.platformbuilders.cliente.domain.entity.mapper.ClienteMapper;
import io.platformbuilders.cliente.domain.gateway.ClienteGateway;
import io.platformbuilders.cliente.infra.jpa.entity.ClienteJpa;
import io.platformbuilders.cliente.infra.jpa.entity.mapper.ClienteJpaMapper;
import io.platformbuilders.cliente.infra.jpa.repository.ClienteJpaRepository;

@Component
public class ClienteDataProvider implements ClienteGateway {

	private final ClienteJpaRepository repository;

	public ClienteDataProvider(final ClienteJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Cliente salvar(CadastraClienteDto dto) {
		final var jpa = ClienteJpaMapper.toJpa(dto);
		return salvar(jpa);
	}

	@Override
	public Set<Cliente> consultar(ConsultaClienteDto dto) {
		final var page = repository.consultarPor(dto.getNome(), dto.getRg(), dto.getCpf(), dto.getDataNascimento(),
				dto.getPaginacao());

		return ClienteMapper.toClientes(page);
	}

	@Override
	public Optional<Cliente> consultarPor(String id) {
		final var optional = repository.findById(id);
		return ClienteMapper.toCliente(optional);
	}

	@Override
	public Cliente alterar(AlteraClienteDto dto) {
		final var jpa = ClienteJpaMapper.toJpa(dto);
		return salvar(jpa);
	}

	private Cliente salvar(final ClienteJpa jpa) {
		final var entity = repository.save(jpa);
		return ClienteMapper.toCliente(entity);
	}

	@Override
	public boolean jaCadastrado(String cpf) {
		var optional = consultarPorCpf(cpf);
		return optional.isPresent();
	}

	@Override
	public Optional<Cliente> consultarPorCpf(String cpf) {
		final var optional = repository.findByCpf(cpf);
		return ClienteMapper.toCliente(optional);
	}

}
