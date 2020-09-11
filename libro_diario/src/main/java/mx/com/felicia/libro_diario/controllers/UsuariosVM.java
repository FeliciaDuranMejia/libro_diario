package mx.com.felicia.libro_diario.controllers;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import mx.com.felicia.libro_diario.dal.models.Usuarios;
import mx.com.felicia.libro_diario.dal.repositories.UsuariosRepository;
import mx.com.felicia.libro_diario.controllers.UsuarioStatus;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsuariosVM {

	@WireVariable
	private UsuariosRepository usuariosRepository;
	@Wire
	private List<Usuarios> listUsuario = new ArrayList<>();
	@Wire
	private List<UsuarioStatus> listAux = new ArrayList<>();
	@Wire
	private boolean addUsuario = false;
	@Wire
	private boolean toUpdate = true;
	@Wire
	private Usuarios currentUsuarios;
	@Wire
	private UsuarioStatus usuarioStatus;
	
	@Init
	public void init() {
		listAux = generateStatusList(getListUsuario());
		currentUsuarios = new Usuarios();
	}

	@NotifyChange({ "currentUsuarios", "addUsuario" })
	@Command
	public void showOrHiddeAddUser(@BindingParam("selection") int selection) {

		if (selection == 1) {
			addUsuario = true;
		} else {
			addUsuario = false;
			currentUsuarios = new Usuarios();
		}
	}

	@NotifyChange({ "currentUsuarios", "addUsuario", "listAux", "listUsuario", "usuarioStatus" })
	@Command
	public void insertAndSave() {
		if (validateInsert()) {
			usuariosRepository.save(currentUsuarios);
			currentUsuarios = new Usuarios();
			usuarioStatus = new UsuarioStatus(null, false);
			addUsuario = false;
		} else {
			Messagebox.show("No se permiten campos vacios");
		}
	}

	@NotifyChange({ "listAux", "listUsuario", "usuarioStatus" })
	@Command
	public void delete(@BindingParam("each") Usuarios each ) {

		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					deleteBD(each);
				}
			}
		};
		Messagebox.show("¿Do you want to delete " + each.getNombreCompleto() + "?", "Message",
				new Messagebox.Button[] { Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION,
				clickListener);
		
	}
	
	private void deleteBD(Usuarios each) {
		usuariosRepository.delete(each);
		BindUtils.postNotifyChange(null, null, this, "listAux");
		usuarioStatus = new UsuarioStatus(null, false);
		BindUtils.postNotifyChange(null, null, this, "usuarioStatus");
	}

	@NotifyChange({ "listAux", "usuarioStatus", "listUsuario" })
	@Command
	public void changeEditableStatus(@BindingParam("usuarioStatus") UsuarioStatus usuarioStatus) {
		usuarioStatus.setStatus(!usuarioStatus.getStatus());
		//refreshRowTemplate(usuarioStatus);
	}
	
	@NotifyChange({ "listAux", "listUsuario", "usuarioStatus" })
	@Command
	public void confirm(@BindingParam("usuarioStatus") UsuarioStatus usuarioStatus) {
		usuariosRepository.save(usuarioStatus.getUsuario());
		changeEditableStatus(usuarioStatus);
		//refreshRowTemplate(usuarioStatus);
	}

	@NotifyChange({ "listAux" })
	public void refreshRowTemplate(UsuarioStatus usuarioStatus) {
		// replace the element in the collection by itself to trigger a model update
		listAux.set(listAux.indexOf(usuarioStatus), usuarioStatus);
	}

	public boolean validateInsert() {
		if (!currentUsuarios.getNombreCompleto().isEmpty() && !currentUsuarios.getContrasena().isEmpty()
				&& !currentUsuarios.getUsuario().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static List<UsuarioStatus> generateStatusList(List<Usuarios> usuarios ){
		List<UsuarioStatus> listAux = new ArrayList<UsuarioStatus>();		///////////
		for (Usuarios user : usuarios) {
			listAux.add(new UsuarioStatus(user, false));
		}
		return listAux;
	}

	////////// SETTERS & GETTERS ///////////
	public List<Usuarios> getListUsuario() {
		listUsuario = usuariosRepository.findAll();
		return listUsuario;
	}

	@NotifyChange("listAux")
	public void setListUsuario(List<Usuarios> listUsuario) {
		this.listUsuario = listUsuario;
	}
	
	@NotifyChange("listAux")
	public List<UsuarioStatus> getListAux() {
		listAux = generateStatusList(getListUsuario());
		return listAux;
	}

	public void setListAux(List<UsuarioStatus> listAux) {
		this.listAux = listAux;
	}
	
	@NotifyChange("usuarioStatus")
	public UsuarioStatus getUsuarioStatus() {
		return usuarioStatus;
	}

	@NotifyChange("usuarioStatus")
	public void setUsuarioStatus(UsuarioStatus usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

	public boolean isAddUsuario() {
		return addUsuario;
	}

	public void setAddUsuario(boolean addUsuario) {
		this.addUsuario = addUsuario;
	}
	
	public boolean isToUpdate() {
		return toUpdate;
	}

	@NotifyChange({ "listAux", "toUpdate", "listUsuario" })
	public void setToUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}

	public Usuarios getCurrentUsuarios() {
		return currentUsuarios;
	}

	public void setCurrentUsuarios(Usuarios currentUsuarios) {
		this.currentUsuarios = currentUsuarios;
	}

}