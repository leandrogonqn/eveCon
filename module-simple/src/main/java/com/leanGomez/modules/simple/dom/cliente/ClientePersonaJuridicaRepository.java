package com.leanGomez.modules.simple.dom.cliente;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.localidad.Localidad;

@DomainService(nature =NatureOfService.DOMAIN, repositoryFor = ClientePersonaJuridica.class)
public class ClientePersonaJuridicaRepository {

	public ClientePersonaJuridica crear(final String personaNombre, final String personaCuitCuil, Localidad personaLocalidad, 
			final String personaDireccion, final String personaTelefono, final String personaMail) {
		final ClientePersonaJuridica object = new ClientePersonaJuridica(personaNombre, personaCuitCuil, personaLocalidad, personaDireccion, personaTelefono, personaMail);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	public List<ClientePersonaJuridica> buscarPorCUIT(final String personaCuitCuil) {
		return repositoryService
				.allMatches(new QueryDefault<>(ClientePersonaJuridica.class, "buscarPorCUIT", "personaCuitCuil", personaCuitCuil));
	}

	public List<ClientePersonaJuridica> listar() {
		return repositoryService.allInstances(ClientePersonaJuridica.class);
	}

	public List<ClientePersonaJuridica> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(ClientePersonaJuridica.class, "listarActivos"));
	}

	public List<ClientePersonaJuridica> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(ClientePersonaJuridica.class, "listarInactivos"));
	}

	@Inject
	RepositoryService repositoryService;
	@Inject 
	ServiceRegistry2 serviceRegistry;
	
}
