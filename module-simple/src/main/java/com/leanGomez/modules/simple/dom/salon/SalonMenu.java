package com.leanGomez.modules.simple.dom.salon;

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

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.SalonMenu", repositoryFor = Salon.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "10.5")
public class SalonMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los salones")
	@MemberOrder(sequence = "2")
	public List<Salon> listar() {
		return salonesRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Salon Por Nombre")
	@MemberOrder(sequence = "5")
	public List<Salon> buscarPorNombre(@ParameterLayout(named = "Nombre") final String salonNombre) {
		return salonesRepository.buscarPorNombre(salonNombre);

	}

	@ActionLayout(named = "Crear Salon")
	@MemberOrder(sequence = "1.2")
	public Salon crear(@ParameterLayout(named = "Nombre") final String salonNombre,
			@Nullable @ParameterLayout(named = "Direccion") @Parameter(optionality=Optionality.OPTIONAL) final String salonDireccion,
			@ParameterLayout(named = "Localidad") final Localidad salonLocalidad,
			@Nullable @ParameterLayout(named = "Telefono") @Parameter(optionality=Optionality.OPTIONAL) final String salonTelefono,
			@Nullable @ParameterLayout(named = "Responsable") @Parameter(optionality=Optionality.OPTIONAL) final String salonResponsable) {
		return salonesRepository.crear(salonNombre, salonDireccion, salonLocalidad, salonTelefono, salonResponsable);
	}
	
	public List<Localidad> choices2Crear() {
		return localidadRepository.listarActivos();
	}

	@javax.inject.Inject
	SalonRepository salonesRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
}
