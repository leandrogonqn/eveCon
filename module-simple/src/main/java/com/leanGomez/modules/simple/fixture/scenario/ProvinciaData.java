package com.leanGomez.modules.simple.fixture.scenario;

import com.leanGomez.modules.simple.dom.provincia.Provincia;
import com.leanGomez.modules.simple.dom.provincia.ProvinciaMenu;

public enum ProvinciaData {

	Neuquen ("Neuquen"), 
	Rio_Negro("Rio Negro"), 
	Buenos_Aires("Buenos Aires"), 
	La_Pampa("La Pampa"), 
	Chubut("Chubut"), 
	Mendoza("Mendoza");
	
    private final String nombre;
    
	public String getNombre() {
		return nombre;
	}

	private ProvinciaData(String nom) {
		nombre = nom;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
	
    public Provincia createWith(final ProvinciaMenu menu) {
        return menu.crear(nombre);
    }
}
