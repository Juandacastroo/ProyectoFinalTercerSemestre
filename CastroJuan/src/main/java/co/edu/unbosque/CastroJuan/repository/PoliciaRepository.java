package co.edu.unbosque.CastroJuan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.CastroJuan.model.Policia;

public interface PoliciaRepository extends CrudRepository<Policia, Integer>  {
	public Optional<Policia> findById(long policia_id);
	public List<Policia> findAll();
}
