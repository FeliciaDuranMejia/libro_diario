package mx.com.felicia.libro_diario.controllers;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import mx.com.felicia.libro_diario.dal.models.Empresas;
import mx.com.felicia.libro_diario.dal.repositories.EmpresasRepository;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EmpresasVM {
	
	@WireVariable
	private EmpresasRepository empresasRepository;
	@Wire
	private ListModelList<Empresas> todoListModel;
	@Wire
	private Empresas selectedEmpresa;
	@Wire
	private List<Empresas> todoList = new ArrayList<>();
	
	@Init
	public void init() {
		todoList = empresasRepository.findAll();
	}

	public ListModelList<Empresas> getTodoListModel() {
		return todoListModel;
	}

	public Empresas getSelectedEmpresa() {
		return selectedEmpresa;
	}

	public void setSelectedEmpresa(Empresas selectedEmpresa) {
		this.selectedEmpresa = selectedEmpresa;
	}

	public List<Empresas> getTodoList() {
		return todoList;
	}

	public void setTodoList(List<Empresas> todoList) {
		this.todoList = todoList;
	}
	
	
	
	
}
