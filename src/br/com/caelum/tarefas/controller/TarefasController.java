package br.com.caelum.tarefas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {

	private JdbcTarefaDao dao;

	@Autowired
	public TarefasController(JdbcTarefaDao dao) {
		this.dao = dao;
	}

	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}

	@RequestMapping("adicionarTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		if (result.hasFieldErrors("descricao")) {
			return "tarefa/formulario";
		}
		dao.adiciona(tarefa);
		return "tarefa/adicionada";
	}

	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		model.addAttribute("tarefas", dao.lista());
		return "tarefa/lista";
	}

	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) {
		dao.remove(tarefa);
		return "redirect:listaTarefas";
	}

	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/mostra";
	}

	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}

	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model) {
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/finalizada";
	}
}

//<Resource name="jdbc/TestDB" auth="Container" type="javax.sql.DataSource"
//maxActive="100" maxIdle="30" maxWait="10000" username="root"
//password="" driverClassName="com.mysql.jdbc.Driver"
//url="jdbc:mysql://localhost/fj21" />
