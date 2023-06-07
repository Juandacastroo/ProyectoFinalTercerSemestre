package co.edu.unbosque.CastroJuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.CastroJuan.model.Incidente;
import co.edu.unbosque.CastroJuan.model.Policia;
import co.edu.unbosque.CastroJuan.repository.IncidenteRepository;
import co.edu.unbosque.CastroJuan.repository.PoliciaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenteService {
	private static IncidenteRepository incidenteRepository;
	@Autowired
	private static PoliciaRepository policiaRepository;

	@Autowired
	public void OperacionService(IncidenteRepository incidenteRepository) {
		this.incidenteRepository = incidenteRepository;
	}

	public static List<Incidente> obtenerOperacionesPorAgente(long agenteId) {
		Optional<Policia> policias = policiaRepository.findById(agenteId);
		Policia policia = policias.get();

		return incidenteRepository.findByPolicia(policia);

	}

	public static List<Incidente> obtenerTodo() {

		return (List<Incidente>) incidenteRepository.findAll();
	}

	public static List<Policia> listp(Policia p) {
		return incidenteRepository.findAllByPolicia(p);

	}
}