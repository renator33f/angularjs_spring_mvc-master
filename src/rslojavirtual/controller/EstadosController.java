package rslojavirtual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rslojavirtual.dao.DaoImplementacao;
import rslojavirtual.dao.DaoInterface;
import rslojavirtual.model.Estados;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/estados")
public class EstadosController extends DaoImplementacao<Estados> implements
		DaoInterface<Estados> {

	public EstadosController(Class<Estados> persistenceClass) {
		super(persistenceClass);
	}
	
	/**
	 * Faz o carregamento da lista de Estados
	 * @return List<Estados> 
	 * @throws Exception
	**/
	
	@RequestMapping(value = "listar", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar() throws Exception {
		return new Gson().toJson(super.lista());
	}

}
