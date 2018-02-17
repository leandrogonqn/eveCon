package com.leanGomez.modules.simple.dom.cliente;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import com.leanGomez.modules.simple.dom.persona.Persona;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.cliente.Cliente "
			+ "WHERE personaNombre.toLowerCase().indexOf(:personaNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "buscarPorCuilCuit", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.cliente.Cliente " + "WHERE personaCuitCuil == :personaCuitCuil"),
	@javax.jdo.annotations.Query(
	        name = "listarActivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.leanGomez.modules.simple.dom.cliente.Cliente "
	                + "WHERE personaActivo == true "),
	@javax.jdo.annotations.Query(
	        name = "listarInactivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.leanGomez.modules.simple.dom.cliente.Cliente "
	                + "WHERE personaActivo == false ")
})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="clienteId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class Cliente extends Persona {

	public Cliente() {
		// TODO Auto-generated constructor stub
		super();
	}
	
}
