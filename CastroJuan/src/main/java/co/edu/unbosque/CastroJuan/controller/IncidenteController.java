package co.edu.unbosque.CastroJuan.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import co.edu.unbosque.CastroJuan.model.Incidente;
import co.edu.unbosque.CastroJuan.model.Policia;
import co.edu.unbosque.CastroJuan.repository.IncidenteRepository;
import co.edu.unbosque.CastroJuan.repository.PoliciaRepository;
import jakarta.transaction.Transactional;

@Transactional
@Controller
@CrossOrigin(origins = "")
@RequestMapping
public class IncidenteController {
	@Autowired
	private IncidenteRepository inop;
	@Autowired
	private PoliciaRepository polirep;

	@GetMapping("/form")
	public String mostrarFormulario(Model model) {
		model.addAttribute("incidente", new Incidente());
		return "incidente-form";
	}

	@PostMapping("/guardar-incidente")
	public String agregar(@RequestParam String empresaAfectada, @RequestParam String tiempoAfectado,
			@RequestParam Integer personasAfectadas, @RequestParam String fecha, @RequestParam String lugar,
			@RequestParam Integer policia_id, Model model) {
		Optional<Policia> policias = polirep.findById(policia_id);
		if (policias.isPresent()) {
			Incidente temp = new Incidente();
			Policia policia = policias.get();
			temp.setEmpresaAfectada(empresaAfectada);
			temp.setTiempoAfectado(tiempoAfectado);
			temp.setPersonasAfectadas(personasAfectadas);
			temp.setFecha(fecha);
			temp.setLugar(lugar);
			temp.setPolicia(policia);

			inop.save(temp);

			return ("index");
		} else {
			return ("index");
		}
	}

}
