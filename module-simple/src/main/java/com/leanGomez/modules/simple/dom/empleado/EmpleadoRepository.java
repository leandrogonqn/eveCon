package com.leanGomez.modules.simple.dom.empleado;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.cliente.Cliente;
import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;
import com.leanGomez.modules.simple.dom.pagodecliente.PagoDeCliente;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@DomainService(nature =NatureOfService.DOMAIN, repositoryFor = Empleado.class)
public class EmpleadoRepository {

	public Empleado crear(final String personaNombre,TipoDeDocumento empleadoTipoDocumento, int numeroDocumento, 
			Sexo empleadoSexo, Localidad personaLocalidad, final String personaDireccion, final String personaTelefono, 
			final String personaMail, final Date fechaNacimiento, final boolean empleadoNotificacionCumpleanios) {
		final Empleado object = new Empleado(personaNombre, personaLocalidad, personaDireccion, personaTelefono, personaMail, empleadoSexo, fechaNacimiento, empleadoTipoDocumento, numeroDocumento, empleadoNotificacionCumpleanios);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}
	
	public List<Empleado> buscarPorDNI(final int empleadoNumeroDocumento) {
		return repositoryService
				.allMatches(new QueryDefault<>(Empleado.class, "buscarPorDNI", "empleadoNumeroDocumento", empleadoNumeroDocumento));
	}

	public List<Empleado> listar() {
		return repositoryService.allInstances(Empleado.class);
	}

	public List<Empleado> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Empleado.class, "listarActivos"));
	}

	public List<Empleado> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Empleado.class, "listarInactivos"));
	}
	
	public List<Empleado> buscarPorNombre(final String personaNombre) {
		return repositoryService.allMatches(
				new QueryDefault<>(Empleado.class, "buscarPorNombre", "personaNombre", personaNombre.toLowerCase()));
	}
	
	public List<Empleado> listarEmpleadosPorOcupacion(Ocupacion ocupacion) {
		// TODO Auto-generated method stub
		List<Empleado> empleados = listarActivos();
		Iterator<Empleado> it = empleados.iterator();
		while (it.hasNext()) {
			Empleado lista = it.next();
			if(!lista.getEmpleadoListaDeOcupaciones().isEmpty()){	
				if(!lista.getEmpleadoListaDeOcupaciones().contains(ocupacion)){
					it.remove();
				}
			}
		}
		return empleados;
	}
	
	@Inject
	RepositoryService repositoryService;
	@Inject 
	ServiceRegistry2 serviceRegistry;
	
}
