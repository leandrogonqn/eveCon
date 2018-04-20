package com.leanGomez.modules.simple.dom.servicio;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio;
import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicioRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.ServicioMenu", repositoryFor = Servicio.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "10.5")
public class ServicioMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los servicios")
	@MemberOrder(sequence = "2")
	public List<Servicio> listar() {
		return serviciosRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Servicio Por Nombre")
	@MemberOrder(sequence = "5")
	public List<Servicio> buscarPorNombre(@ParameterLayout(named = "Nombre") final String servicioNombre) {
		return serviciosRepository.buscarPorNombre(servicioNombre);

	}

	@ActionLayout(named = "Crear Servicio")
	@MemberOrder(sequence = "1.2")
	public Servicio crear(@ParameterLayout(named = "Nombre") final String servicioNombre,
			@Nullable@ParameterLayout(named = "Observaciones",multiLine=10)@Parameter(optionality=Optionality.OPTIONAL) final String servicioObservaciones,
			@ParameterLayout(named="Precio") final Double servicioPrecio) {
		Servicio s = new Servicio(servicioNombre, servicioObservaciones);
		precioHistoricoServicioRepository.crear(s, servicioPrecio);
		return s;
	}
	
	public Double mostrarPrecioPorFecha(@ParameterLayout(named = "Servicio") final Servicio precioHistoricoServicioServicio,
			@ParameterLayout(named = "fecha") final Date fecha) {
		return precioHistoricoServicioRepository.mostrarPrecioPorFecha(precioHistoricoServicioServicio, fecha);
	}
	
	public List<Servicio> choices0MostrarPrecioPorFecha() {
		return serviciosRepository.listarActivos();
	}

	@javax.inject.Inject
	ServicioRepository serviciosRepository;
	
	@Inject
	PrecioHistoricoServicioRepository precioHistoricoServicioRepository;
	
}
