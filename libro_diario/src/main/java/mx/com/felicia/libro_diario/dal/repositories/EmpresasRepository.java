package mx.com.felicia.libro_diario.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.felicia.libro_diario.dal.models.Empresas;

@Repository
public interface EmpresasRepository extends JpaRepository<Empresas, Integer>{

}
