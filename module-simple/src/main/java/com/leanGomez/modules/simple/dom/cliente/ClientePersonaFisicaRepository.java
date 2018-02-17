package com.leanGomez.modules.simple.dom.cliente;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@DomainService(nature =NatureOfService.DOMAIN, repositoryFor = ClientePersonaFisica.class)
public class ClientePersonaFisicaRepository {

	public ClientePersonaFisica crear(final String personaNombre,TipoDeDocumento clienteTipoDocumento, int numeroDocumento, 
			Sexo clienteSexo, Localidad personaLocalidad, final String personaDireccion, 
			final String personaTelefono, final String personaMail,
			final Date fechaNacimiento) {
		final ClientePersonaFisica object = new ClientePersonaFisica(personaNombre, personaLocalidad, personaDireccion, personaTelefono, personaMail, clienteSexo, fechaNacimiento, clienteTipoDocumento, numeroDocumento);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	public List<ClientePersonaFisica> buscarPorDNI(final int clienteNumeroDocumento) {
		return repositoryService
				.allMatches(new QueryDefault<>(ClientePersonaFisica.class, "buscarPorDNI", "clienteNumeroDocumento", clienteNumeroDocumento));
	}

	public List<ClientePersonaFisica> listar() {
		return repositoryService.allInstances(ClientePersonaFisica.class);
	}

	public List<ClientePersonaFisica> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(ClientePersonaFisica.class, "listarActivos"));
	}

	public List<ClientePersonaFisica> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(ClientePersonaFisica.class, "listarInactivos"));
	}

	@Inject
	RepositoryService repositoryService;
	@Inject 
	ServiceRegistry2 serviceRegistry;
	
}
