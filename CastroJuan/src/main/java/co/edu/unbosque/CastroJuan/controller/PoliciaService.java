package co.edu.unbosque.CastroJuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.CastroJuan.model.Incidente;
import co.edu.unbosque.CastroJuan.model.Policia;
import co.edu.unbosque.CastroJuan.repository.PoliciaRepository;

import java.util.List;

@Service
public class PoliciaService {
	@Autowired
    private static PoliciaRepository policiaRepository;

    @Autowired
    public PoliciaService(PoliciaRepository policiaRepository) {
        this.policiaRepository = policiaRepository;
    }

    public List<Policia> obtenerAgentes() {
    	return (List<Policia>) policiaRepository.findAll();
    }
    
    public static List<Policia> obtenerTodo() {

		return (List<Policia>) policiaRepository.findAll();
	}
    

}
