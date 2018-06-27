package com.leanGomez.modules.simple.dom.empleado;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;
import com.leanGomez.modules.simple.dom.ocupacion.OcupacionRepository;
import com.leanGomez.modules.simple.dom.pagoempleado.PagoEmpleado;
import com.leanGomez.modules.simple.dom.pagoempleado.PagoEmpleadoRepository;
import com.leanGomez.modules.simple.dom.persona.GenerarCuit;
import com.leanGomez.modules.simple.dom.persona.Persona;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.empleado.Empleado "
			+ "WHERE personaNombre.toLowerCase().indexOf(:personaNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "buscarPorCuilCuit", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.empleado.Empleado " + "WHERE personaCuitCuil == :personaCuitCuil"),
	@javax.jdo.annotations.Query(
	        name = "listarActivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.leanGomez.modules.simple.dom.empleado.Empleado "
	                + "WHERE personaActivo == true "),
	@javax.jdo.annotations.Query(
	        name = "listarInactivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.leanGomez.modules.simple.dom.empleado.Empleado "
	                + "WHERE personaActivo == false "),
	@javax.jdo.annotations.Query(name = "buscarPorDNI", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.empleado.Empleado " + "WHERE empleadoNumeroDocumento == :empleadoNumeroDocumento")
})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="empleadoId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Empleado extends Persona{

	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Empleado: {personaNombre}", "personaNombre",
				getPersonaNombre() + " Cuit/Cuil: " + getPersonaCuitCuil());
	}
	// endregion

	public String iconName() {
		return (getEmpleadoSexo() == Sexo.Femenino) ? "Femenino" : "Masculino";
	}
	
	//Constructores
	public Empleado() {
		// TODO Auto-generated constructor stub
	}
	
	public Empleado(String personaNombre, Localidad personaLocalidad, 
			String personaDireccion, String personaTelefono, String personaMail,
			Sexo empleadoSexo, Date empleadoFechaNacimiento, 
			TipoDeDocumento empleadoTipoDocumento, int empleadoNumeroDocumento, boolean empleadoNotificacionCumpleanios) {
		super();
		setEmpleadoSexo(empleadoSexo);
		setEmpleadoFechaNacimiento(empleadoFechaNacimiento);
		setEmpleadoTipoDocumento(empleadoTipoDocumento);
		setEmpleadoNumeroDocumento(empleadoNumeroDocumento);
		setPersonaNombre(personaNombre);
		setPersonaLocalidad(personaLocalidad);
		setPersonaDireccion(personaDireccion);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setEmpleadoNotificacionCumpleanios(empleadoNotificacionCumpleanios);
		setPersonaActivo(true);
		setPersonaCuitCuil(GenerarCuit.generar(empleadoSexo, empleadoNumeroDocumento));
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Sexo")
	private Sexo empleadoSexo;
	public Sexo getEmpleadoSexo() {
		return empleadoSexo;
	}
	public void setEmpleadoSexo(Sexo empleadoSexo) {
		this.empleadoSexo = empleadoSexo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Fecha de Nacimiento")
	private Date empleadoFechaNacimiento;
	public Date getEmpleadoFechaNacimiento() {
		return empleadoFechaNacimiento;
	}
	public void setEmpleadoFechaNacimiento(Date empleadoFechaNacimiento) {
		this.empleadoFechaNacimiento = empleadoFechaNacimiento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Tipo de Documento")
	private TipoDeDocumento empleadoTipoDocumento;
	public TipoDeDocumento getEmpleadoTipoDocumento() {
		return empleadoTipoDocumento;
	}
	public void setEmpleadoTipoDocumento(TipoDeDocumento empleadoTipoDocumento) {
		this.empleadoTipoDocumento = empleadoTipoDocumento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Numero de Documento")
	private int empleadoNumeroDocumento;
	public int getEmpleadoNumeroDocumento() {
		return empleadoNumeroDocumento;
	}
	public void setEmpleadoNumeroDocumento(int empleadoNumeroDocumento) {
		this.empleadoNumeroDocumento = empleadoNumeroDocumento;
	}
	
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@CollectionLayout(named="Ocupaciones")
	private List<Ocupacion> empleadoListaDeOcupaciones;
	public List<Ocupacion> getEmpleadoListaDeOcupaciones() {
		return empleadoListaDeOcupaciones;
	}
	public void setEmpleadoListaDeOcupaciones(List<Ocupacion> empleadoListaDeOcupaciones) {
		this.empleadoListaDeOcupaciones = empleadoListaDeOcupaciones;
	}
	
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Lista de Pagos")
	private List<PagoEmpleado> empleadoPagoEmpleado;
	public List<PagoEmpleado> getEmpleadoPagoEmpleado() {
		return empleadoPagoEmpleado;
	}
	public void setEmpleadoPagoEmpleado(List<PagoEmpleado> empleadoPagoEmpleado) {
		this.empleadoPagoEmpleado = empleadoPagoEmpleado;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Notificacion cumpleaños")
	private boolean empleadoNotificacionCumpleanios;

	public boolean getEmpleadoNotificacionCumpleanios() {
		return empleadoNotificacionCumpleanios;
	}

	public void setEmpleadoNotificacionCumpleanios(boolean empleadoNotificacionCumpleanios) {
		this.empleadoNotificacionCumpleanios = empleadoNotificacionCumpleanios;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED, hidden = Where.EVERYWHERE)
	@PropertyLayout(named = "Aviso")
	private boolean empleadoAviso;

	public boolean getEmpleadoAviso() {
		return empleadoAviso;
	}

	public void setEmpleadoAviso(boolean empleadoAviso) {
		this.empleadoAviso = empleadoAviso;
	}
	
	public Empleado modificarLocalidad(@ParameterLayout(named = "Localidad") final Localidad name) {
		setPersonaLocalidad(name);
		return this;
	}

	public List<Localidad> choices0ModificarLocalidad() {
		return localidadRepository.listarActivos();
	}

	public Localidad default0ModificarLocalidad() {
		return getPersonaLocalidad();
	}

	public Empleado modificarSexo(@ParameterLayout(named = "Sexo") final Sexo empleadoSexo) {
		setPersonaCuitCuil(GenerarCuit.generar(empleadoSexo, getEmpleadoNumeroDocumento()));
		setEmpleadoSexo(empleadoSexo);
		return this;
	}

	public Empleado modificarDni(@ParameterLayout(named = "Numero de Documento") final int empleadoDni) {
		setPersonaCuitCuil(GenerarCuit.generar(getEmpleadoSexo(), empleadoDni));
		setEmpleadoNumeroDocumento(empleadoDni);
		return this;
	}

	public Sexo default0ModificarSexo() {
		return getEmpleadoSexo();
	}

	public int default0ModificarDni() {
		return getEmpleadoNumeroDocumento();
	}

	public Empleado modificarNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		setPersonaNombre(personaNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getPersonaNombre();
	}

	public Empleado modificarTipoDocumento(
			@ParameterLayout(named = "Tipo de Documento") final TipoDeDocumento empleadoTipoDocumento) {
		setEmpleadoTipoDocumento(empleadoTipoDocumento);
		return this;
	}

	public TipoDeDocumento default0ModificarTipoDocumento() {
		return getEmpleadoTipoDocumento();
	}

	public Empleado modificarDireccion(@ParameterLayout(named = "Direccion") final String personaDireccion) {
		setPersonaDireccion(personaDireccion);
		return this;
	}

	public String default0ModificarDireccion() {
		return getPersonaDireccion();
	}

	public Empleado modificarTelefono(@ParameterLayout(named = "Telefono") final String personaTelefono) {
		setPersonaTelefono(personaTelefono);
		return this;
	}

	public String default0ModificarTelefono() {
		return getPersonaTelefono();
	}

	public Empleado modificarMail(@ParameterLayout(named = "Mail") final String personaMail) {
		setPersonaMail(personaMail);
		return this;
	}

	public String default0ModificarMail() {
		return getPersonaMail();
	}

	public Empleado modificarFechaNacimiento(
			@Nullable @ParameterLayout(named = "Fecha de Nacimiento") @Parameter(optionality = Optionality.OPTIONAL) final Date empleadoFechaNacimiento) {
		if ((empleadoFechaNacimiento == null)) {
			setEmpleadoNotificacionCumpleanios(false);
		}
		setEmpleadoFechaNacimiento(empleadoFechaNacimiento);
		return this;
	}

	public Date default0ModificarFechaNacimiento() {
		return getEmpleadoFechaNacimiento();
	}

	public Empleado modificarNotificacionCumpleanios(
			@ParameterLayout(named = "Notificacion Cumpleaños") final boolean empleadoNotificacionCumpleanios) {
		setEmpleadoNotificacionCumpleanios(empleadoNotificacionCumpleanios);
		return this;
	}

	public String validateModificarNotificacionCumpleanios(final boolean empleadoNotificacionCumpleanios) {
		if ((empleadoFechaNacimiento == null) & (empleadoNotificacionCumpleanios == true)) {
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		return "";
	}

	public boolean default0ModificarNotificacionCumpleanios() {
		return getEmpleadoNotificacionCumpleanios();
	}

	public Empleado modificarActivo(@ParameterLayout(named = "Activo") final boolean personaActivo) {
		setPersonaActivo(personaActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isPersonaActivo();
	}

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarEmpleado() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPersonaActivo(false);
	}
	// endregion
	
	@ActionLayout(named = "Agregar Ocupacion")
	public Empleado agregarOcupacion(@ParameterLayout(named = "Ocupacion") final Ocupacion ocupacion) {
		if (this.getEmpleadoListaDeOcupaciones().contains(ocupacion)) {
			messageService.warnUser("ERROR: La ocupacion ya está agregada en la lista");
		} else {
			this.getEmpleadoListaDeOcupaciones().add(ocupacion);
			this.setEmpleadoListaDeOcupaciones(this.getEmpleadoListaDeOcupaciones());
		}
		return this;
	}

	public List<Ocupacion> choices0AgregarOcupacion() {
		return ocupacionRepository.listarActivos();
	}

	@ActionLayout(named = "Quitar Ocupacion")
	public Empleado quitarOcupacion(@ParameterLayout(named = "Ocupacion") Ocupacion ocupacion) {
		Iterator<Ocupacion> it = getEmpleadoListaDeOcupaciones().iterator();
		while (it.hasNext()) {
			Ocupacion lista = it.next();
			if (lista.equals(ocupacion))
				it.remove();
		}
		return this;
	}

	public List<Ocupacion> choices0QuitarOcupacion() {
		return getEmpleadoListaDeOcupaciones();
	}
	
	@ActionLayout(named="Crear Pago")
	public Empleado crearPago(@ParameterLayout(named = "Fecha") Date pagoEmpleadoFecha,
								@ParameterLayout(named = "Monto") Double pagoEmpleadoMonto,
								@Nullable @ParameterLayout(named = "Observaciones",multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) String pagoEmpleadoObservaciones) {
		this.getEmpleadoPagoEmpleado().add(pagoEmpleadoRepository.crear(this, pagoEmpleadoMonto, 
				pagoEmpleadoFecha, pagoEmpleadoObservaciones));
		this.setEmpleadoPagoEmpleado(this.getEmpleadoPagoEmpleado());
		return this;
	}
	
	@ActionLayout(named = "Quitar Pago")
	public Empleado quitarPago(@ParameterLayout(named = "Pago") PagoEmpleado pagoEmpleado) {
		Iterator<PagoEmpleado> it = getEmpleadoPagoEmpleado().iterator();
		while (it.hasNext()) {
			PagoEmpleado lista = it.next();
			if (lista.equals(pagoEmpleado))
				it.remove();
		}
		return this;
	}
	
	public List<PagoEmpleado> choices0QuitarPago() {
		return getEmpleadoPagoEmpleado();
	}

	@javax.inject.Inject
	LocalidadRepository localidadRepository;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	OcupacionRepository ocupacionRepository;
	
	@Inject
	PagoEmpleadoRepository pagoEmpleadoRepository;
	
	// endregion
	
	
}
