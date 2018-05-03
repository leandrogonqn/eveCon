package com.leanGomez.modules.simple.dom.estado;

import com.leanGomez.modules.simple.dom.evento.Evento;

public interface IEstado {

	public void agregarPago(Evento evento);
	public void quitarPago(Evento evento);
	public void actualizarEstado(Evento evento);
	public void anulacion(Evento evento);
	public void cambiarFechaEvento(Evento evento);
}
