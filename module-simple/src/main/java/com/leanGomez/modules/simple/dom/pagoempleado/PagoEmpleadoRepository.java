package com.leanGomez.modules.simple.dom.pagoempleado;

import java.util.Date;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import com.leanGomez.modules.simple.dom.empleado.Empleado;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PagoEmpleado.class)
public class PagoEmpleadoRepository {

	public PagoEmpleado crear(Empleado pagoEmpleadoEmpleado, double pagoEmpleadoMonto, Date pagoEmpleadoFecha, String pagoEmpleadoObservaciones) {
		final PagoEmpleado object = new PagoEmpleado(pagoEmpleadoEmpleado, pagoEmpleadoMonto, pagoEmpleadoFecha, pagoEmpleadoObservaciones);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
