package co.edu.unbosque.CastroJuan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.CastroJuan.model.Incidente;
import co.edu.unbosque.CastroJuan.model.Policia;


public interface IncidenteRepository extends CrudRepository<Incidente, Integer> {
    List<Incidente> findByPolicia(Policia policia_id);
    List<Policia> findAllByPolicia(Policia policia_id);



}
