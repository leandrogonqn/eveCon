package com.leanGomez.modules.simple.dom.cliente;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
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

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.persona.GenerarCuit;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorDNI", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.cliente.Cliente " + "WHERE clienteNumeroDocumento == :clienteNumeroDocumento")})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="clientePersonaFisicaId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class ClientePersonaFisica extends Cliente {
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Cliente: {personaNombre}", "personaNombre",
				getPersonaNombre() + " Cuit/Cuil: " + getPersonaCuitCuil());
	}
	// endregion

	public String iconName() {
		return (getClienteSexo() == Sexo.Femenino) ? "Femenino" : "Masculino";
	}

	public ClientePersonaFisica(String personaNombre, Localidad personaLocalidad, 
			String personaDireccion, String personaTelefono, String personaMail,
			Sexo clienteSexo, Date clienteFechaNacimiento, 
			TipoDeDocumento clienteTipoDocumento, int clienteNumeroDocumento) {
		super();
		setClienteSexo(clienteSexo);
		setClienteFechaNacimiento(clienteFechaNacimiento);
		setClienteTipoDocumento(clienteTipoDocumento);
		setClienteNumeroDocumento(clienteNumeroDocumento);
		setPersonaNombre(personaNombre);
		setPersonaLocalidad(personaLocalidad);
		setPersonaDireccion(personaDireccion);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaActivo(true);
		setPersonaCuitCuil(GenerarCuit.generar(clienteSexo, clienteNumeroDocumento));
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Sexo")
	private Sexo clienteSexo;
	public Sexo getClienteSexo() {
		return clienteSexo;
	}
	public void setClienteSexo(Sexo clienteSexo) {
		this.clienteSexo = clienteSexo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Fecha de Nacimiento")
	private Date clienteFechaNacimiento;
	public Date getClienteFechaNacimiento() {
		return clienteFechaNacimiento;
	}
	public void setClienteFechaNacimiento(Date clienteFechaNacimiento) {
		this.clienteFechaNacimiento = clienteFechaNacimiento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Tipo de Documento")
	private TipoDeDocumento clienteTipoDocumento;
	public TipoDeDocumento getClienteTipoDocumento() {
		return clienteTipoDocumento;
	}
	public void setClienteTipoDocumento(TipoDeDocumento clienteTipoDocumento) {
		this.clienteTipoDocumento = clienteTipoDocumento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Numero de Documento")
	private int clienteNumeroDocumento;
	public int getClienteNumeroDocumento() {
		return clienteNumeroDocumento;
	}
	public void setClienteNumeroDocumento(int clienteNumeroDocumento) {
		this.clienteNumeroDocumento = clienteNumeroDocumento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Notificacion cumpleaños")
	private boolean clienteNotificacionCumpleanios;

	public boolean getClienteNotificacionCumpleanios() {
		return clienteNotificacionCumpleanios;
	}

	public void setClienteNotificacionCumpleanios(boolean clienteNotificacionCumpleanios) {
		this.clienteNotificacionCumpleanios = clienteNotificacionCumpleanios;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED, hidden = Where.EVERYWHERE)
	@PropertyLayout(named = "Aviso")
	private boolean clienteAviso;

	public boolean getClienteAviso() {
		return clienteAviso;
	}

	public void setClienteAviso(boolean clienteAviso) {
		this.clienteAviso = clienteAviso;
	}
	
	public Cliente actualizarLocalidad(@ParameterLayout(named = "Localidad") final Localidad name) {
		setPersonaLocalidad(name);
		return this;
	}

	public List<Localidad> choices0ActualizarLocalidad() {
		return localidadRepository.listarActivos();
	}

	public Localidad default0ActualizarLocalidad() {
		return getPersonaLocalidad();
	}

	public Cliente actualizarSexo(@ParameterLayout(named = "Sexo") final Sexo clienteSexo) {
		setPersonaCuitCuil(GenerarCuit.generar(clienteSexo, getClienteNumeroDocumento()));
		setClienteSexo(clienteSexo);
		return this;
	}

	public Cliente actualizarDni(@ParameterLayout(named = "Numero de Documento") final int clienteDni) {
		setPersonaCuitCuil(GenerarCuit.generar(getClienteSexo(), clienteDni));
		setClienteNumeroDocumento(clienteDni);
		return this;
	}

	public Sexo default0ActualizarSexo() {
		return getClienteSexo();
	}

	public int default0ActualizarDni() {
		return getClienteNumeroDocumento();
	}

	public Cliente actualizarNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		setPersonaNombre(personaNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getPersonaNombre();
	}

	public Cliente actualizarTipoDocumento(
			@ParameterLayout(named = "Tipo de Documento") final TipoDeDocumento clienteTipoDocumento) {
		setClienteTipoDocumento(clienteTipoDocumento);
		return this;
	}

	public TipoDeDocumento default0ActualizarTipoDocumento() {
		return getClienteTipoDocumento();
	}

	public Cliente actualizarDireccion(@ParameterLayout(named = "Direccion") final String personaDireccion) {
		setPersonaDireccion(personaDireccion);
		return this;
	}

	public String default0ActualizarDireccion() {
		return getPersonaDireccion();
	}

	public Cliente actualizarTelefono(@ParameterLayout(named = "Telefono") final String personaTelefono) {
		setPersonaTelefono(personaTelefono);
		return this;
	}

	public String default0ActualizarTelefono() {
		return getPersonaTelefono();
	}

	public Cliente actualizarMail(@ParameterLayout(named = "Mail") final String personaMail) {
		setPersonaMail(personaMail);
		return this;
	}

	public String default0ActualizarMail() {
		return getPersonaMail();
	}

	public Cliente actualizarFechaNacimiento(
			@Nullable @ParameterLayout(named = "Fecha de Nacimiento") @Parameter(optionality = Optionality.OPTIONAL) final Date clienteFechaNacimiento) {
		if ((clienteFechaNacimiento == null)) {
			setClienteNotificacionCumpleanios(false);
		}
		setClienteFechaNacimiento(clienteFechaNacimiento);
		return this;
	}

	public Date default0ActualizarFechaNacimiento() {
		return getClienteFechaNacimiento();
	}

	public Cliente actualizarNotificacionCumpleanios(
			@ParameterLayout(named = "Notificacion Cumpleaños") final boolean clienteNotificacionCumpleanios) {
		setClienteNotificacionCumpleanios(clienteNotificacionCumpleanios);
		return this;
	}

	public String validateActualizarNotificacionCumpleanios(final boolean clienteNotificacionCumpleanios) {
		if ((clienteFechaNacimiento == null) & (clienteNotificacionCumpleanios == true)) {
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		return "";
	}

	public boolean default0ActualizarNotificacionCumpleanios() {
		return getClienteNotificacionCumpleanios();
	}

	public Cliente actualizarActivo(@ParameterLayout(named = "Activo") final boolean personaActivo) {
		setPersonaActivo(personaActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return isPersonaActivo();
	}

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarCliente() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPersonaActivo(false);
	}
	// endregion

	@javax.inject.Inject
	LocalidadRepository localidadRepository;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	// endregion
	
}
