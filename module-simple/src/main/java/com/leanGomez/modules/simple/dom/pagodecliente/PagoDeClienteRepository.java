package com.leanGomez.modules.simple.dom.pagodecliente;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.evento.Evento;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PagoDeCliente.class)
public class PagoDeClienteRepository {

	public PagoDeCliente crear(Evento pagoDeClienteEvento, double pagoDeClienteMonto, Date pagoDeClienteFecha, String pagoDeClienteObservaciones) {
		final PagoDeCliente object = new PagoDeCliente(pagoDeClienteEvento, pagoDeClienteMonto, pagoDeClienteFecha, pagoDeClienteObservaciones);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
