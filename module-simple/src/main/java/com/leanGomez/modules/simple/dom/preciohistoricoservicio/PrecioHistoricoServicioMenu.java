package com.leanGomez.modules.simple.dom.preciohistoricoservicio;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.leanGomez.modules.simple.dom.servicio.Servicio;
import com.leanGomez.modules.simple.dom.servicio.ServicioRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.PrecioHistoricoServicioMenu", repositoryFor = Servicio.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "30.3")
public class PrecioHistoricoServicioMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar historial completo")
	@MemberOrder(sequence = "2")
	public List<PrecioHistoricoServicio> listar() {
		return precioHistoricoServicioRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Mostrar precio de servicio por fecha")
	@MemberOrder(sequence = "1")
	public Double mostrarPrecioPorFecha(@ParameterLayout(named = "Servicio") final Servicio precioHistoricoServicioServicio,
			@ParameterLayout(named = "fecha") final Date fecha) {
		return precioHistoricoServicioRepository.mostrarPrecioPorFecha(precioHistoricoServicioServicio, fecha);
	}
	
	public List<Servicio> choices0MostrarPrecioPorFecha() {
		return serviciosRepository.listarActivos();
	}

	@Inject
	PrecioHistoricoServicioRepository precioHistoricoServicioRepository;
	
	@javax.inject.Inject
	ServicioRepository serviciosRepository;
	
}
