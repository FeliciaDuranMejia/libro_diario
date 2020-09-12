package mx.com.felicia.libro_diario.dal.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the empresas database table.
 * 
 */
@Entity
@NamedQuery(name="Empresas.findAll", query="SELECT e FROM Empresas e")
public class Empresas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_empresa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpresa;

	@Column(name="nombre_empresa")
	private String nombreEmpresa;

	@Column(name="telefono")
	private String telefono;

	public Empresas() {
	}

	public int getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreEmpresa() {
		return this.nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}