package com.jama.api.service;

import java.util.List;

import com.jama.api.model.Hospitalizacion;

public interface HospitalizacionService {
	public Hospitalizacion newHospitalizacion(Hospitalizacion hospitalizacion);

	public Hospitalizacion getHospitalizacion(int hospitalizacionId);

	public Hospitalizacion updateGuarderia(Hospitalizacion hospitalizacion, Hospitalizacion bodyHospitalizacion);

	public List<Hospitalizacion> getHospitalizaciones();
}
