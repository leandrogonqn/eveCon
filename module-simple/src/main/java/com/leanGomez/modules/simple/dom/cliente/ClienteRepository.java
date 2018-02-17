package com.leanGomez.modules.simple.dom.cliente;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature =NatureOfService.DOMAIN, repositoryFor = Cliente.class)
public class ClienteRepository {
	
	public List<Cliente> buscarPorNombre(final String clienteNombre) {
		return repositoryService.allMatches(
				new QueryDefault<>(Cliente.class, "buscarPorNombre", "clienteNombre", clienteNombre.toLowerCase()));
	}

	public List<Cliente> buscarPorCuilCuit(final int personaCuitCuil) {
		return repositoryService
				.allMatches(new QueryDefault<>(Cliente.class, "buscarPorCuilCuit", "personaCuitCuil", personaCuitCuil));
	}

	public List<Cliente> listar() {
		return repositoryService.allInstances(Cliente.class);
	}

	public List<Cliente> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Cliente.class, "listarActivos"));
	}

	public List<Cliente> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Cliente.class, "listarInactivos"));
	}

	@Inject
	RepositoryService repositoryService;

}
