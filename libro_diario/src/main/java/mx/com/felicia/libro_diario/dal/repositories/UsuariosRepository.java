package mx.com.felicia.libro_diario.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.felicia.libro_diario.dal.models.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
	
	Usuarios findByIdUsuario(int idUsuario);

}
