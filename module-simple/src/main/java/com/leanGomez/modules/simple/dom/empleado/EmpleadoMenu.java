package com.leanGomez.modules.simple.dom.empleado;

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

import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.empleado.EmpleadoRepository;
import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;
import com.leanGomez.modules.simple.dom.ocupacion.OcupacionRepository;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.EmpleadoMenu", repositoryFor = Empleado.class)
@DomainServiceLayout(named = "Empleados", menuOrder = "10.1")
public class EmpleadoMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Por DNI")
	@MemberOrder(sequence = "1.3")
	public List<Empleado> buscarPorDNI(@ParameterLayout(named = "DNI") final int empleadoDni) {
		return empleadoRepository.buscarPorDNI(empleadoDni);
	}

	public List<Localidad> choices4Crear() {
		return localidadesRepository.listarActivos();
	}

	@ActionLayout(named = "Crear Empleado")
	@MemberOrder(sequence = "1.2")
	public Empleado crear(@ParameterLayout(named = "Nombre y apellido") final String personaNombre,
			@ParameterLayout(named = "Tipo de Documento") final TipoDeDocumento empleadoTipoDocumento,
			@ParameterLayout(named = "Numero de Documento") final int empleadoNumeroDocumento,
			@ParameterLayout(named = "Sexo") final Sexo empleadoSexo,
			@ParameterLayout(named = "Localidad") final Localidad personaLocalidad,
			@Nullable @ParameterLayout(named = "Dirección") @Parameter(optionality=Optionality.OPTIONAL) final String personaDireccion,
			@Nullable @ParameterLayout(named = "Teléfono") @Parameter(optionality = Optionality.OPTIONAL) final String personaTelefono,
			@Nullable @ParameterLayout(named = "E-Mail") @Parameter(optionality = Optionality.OPTIONAL) final String personaMail,
			@Nullable @ParameterLayout(named = "Fecha de Nacimiento") @Parameter(optionality = Optionality.OPTIONAL) final Date empleadoFechaNacimiento,
			@ParameterLayout(named = "Notif. Cumpleaños") final boolean empleadoNotificacionCumpleanios) {
		return empleadoRepository.crear(personaNombre, empleadoTipoDocumento, empleadoNumeroDocumento, empleadoSexo, personaLocalidad, personaDireccion, personaTelefono, personaMail, empleadoFechaNacimiento, empleadoNotificacionCumpleanios);
				
	}
	
	public String validateCrear(final String empleadoNombre, final TipoDeDocumento empleadoTipoDocumento, 
			final int empleadoNumeroDocumento, final Sexo empleadoSexo,
			final Localidad personaLocalidad, final String personaDireccion, final String personaTelefono,
			final String personaMail, final Date empleadoFechaNacimiento, final boolean empleadoNotificacionCumpleanios) {
		if ((empleadoFechaNacimiento == null) & (empleadoNotificacionCumpleanios == true)) {
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		Date hoy = new Date();
		if (empleadoFechaNacimiento!=null) {
			if (empleadoFechaNacimiento.after(hoy)) {
				return "La fecha de nacimiento es mayor a la fecha actual";
			}
		}

		return "";
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todos los Empleados")
	@MemberOrder(sequence = "2")
	public List<Empleado> listar() {
		return empleadoRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Empleado Por Nombre")
	@MemberOrder(sequence = "1.1")
	public List<Empleado> buscarPorNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		return empleadoRepository.buscarPorNombre(personaNombre);
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Listar Empleado Por Ocupacion")
	@MemberOrder(sequence = "1.1")
	public List<Empleado> listarEmpleadosPorOcupacion(@ParameterLayout(named = "Ocupacion") final Ocupacion ocupacion) {
		return empleadoRepository.listarEmpleadosPorOcupacion(ocupacion);
	}
	
	public List<Ocupacion> choices0ListarEmpleadosPorOcupacion(){
		return ocupacionRepository.listarActivos();
	}

	@Inject
	EmpleadoRepository empleadoRepository;
	
	@javax.inject.Inject
	LocalidadRepository localidadesRepository;
	
	@Inject
	OcupacionRepository ocupacionRepository;
	
}
