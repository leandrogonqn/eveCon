package com.leanGomez.modules.simple.dom.cliente;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorCUIT", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.cliente.Cliente " + "WHERE personaCuitCuil == :personaCuitCuil")})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="clientePersonaJuridicaId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class ClientePersonaJuridica extends Cliente {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Cliente Empresa: {personaNombre}", "personaNombre",
				getPersonaNombre() + " Cuit/Cuil: " + getPersonaCuitCuil());
	}
	// endregion
	
	public ClientePersonaJuridica(String personaNombre, String personaCuitCuil, Localidad personaLocalidad, 
			String personaDireccion, String personaTelefono, String personaMail) {
		super();
		setPersonaNombre(personaNombre);
		setPersonaLocalidad(personaLocalidad);
		setPersonaDireccion(personaDireccion);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaActivo(true);
		setPersonaCuitCuil(personaCuitCuil);
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

	public Cliente actualizarNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		setPersonaNombre(personaNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getPersonaNombre();
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
