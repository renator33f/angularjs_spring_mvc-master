package rslojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rslojavirtual.dao.DaoImplementacao;
import rslojavirtual.dao.DaoInterface;
import rslojavirtual.model.Devolucao;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/devolucao")
public class DevolucaoController extends DaoImplementacao<Devolucao> implements 
		DaoInterface<Devolucao> {

	public DevolucaoController(Class<Devolucao> persistenceClass) { 
		super(persistenceClass); 
	}
	  
	/**
	 * Salva devolucao do Produto
	 * @param jsonDevolucao
	 * @return ResponseEntity
	 * @throws Exception
	**/
	
	@RequestMapping(value="salvar", method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity salvar (@RequestBody String jsonDevolucao) throws Exception {
		Devolucao devolucao = new Gson().fromJson(jsonDevolucao, Devolucao.class);
		 
		 if (devolucao != null && devolucao.getQuantidade() == null){
			 devolucao.setQuantidade(0L);
		 }
		 
		 super.salvarOuAtualizar(devolucao);
		 return new ResponseEntity(HttpStatus.CREATED);
		 
	}
	
	
	/**
	  * Retorna a lista de Devolucões realizadas
	  * @return JSON String de Devolucao
	  * @throws Exception
	**/
	 
	@RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public byte[] listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina)).getBytes("UTF-8");
	}
	
	
	@RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String totalPagina() throws Exception {
		return ""+super.quantidadePagina(); 
	}
	 
	/**
	 * Deleta a Devolucao informada
	 * @param codEstoqueProduto
	 * @return String vazia como resposta
	 * @throws Exception
	**/
	
	@RequestMapping(value="deletar/{codDevolucao}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codDevolucao") String codDevolucao) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codDevolucao)));
		return "";
	}
	
	
	/**
	 * Consulta e retorna a Devolucao do Produto com o codigo informado
	 * @param codDevolucao
	 * @return JSON Devolucao pesquisada
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscardevolucao/{codDevolucao}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarDevolucao (@PathVariable("codDevolucao") String codDevolucao) throws Exception {
		Devolucao objeto = super.loadObjeto(Long.parseLong(codDevolucao));
		if (objeto == null) {
			return "{}".getBytes("UTF-8");
		}
		return new Gson().toJson(objeto).getBytes("UTF-8");
	}
	
	
	/**
	 * Consulta e retorna a Devolucao do Produto com o nome  informado
	 * @param nomeDevolucao
	 * @return JSON Devolucao Produto pesquisado
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscarnome/{nomeDevolucao}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarNome (@PathVariable("nomeDevolucao") String nomeDevolucao) throws Exception {
		List<Devolucao> devolucoes = new ArrayList<Devolucao>();
		devolucoes = super.listaLikeExpression("modelo", nomeDevolucao);

		if (devolucoes == null || devolucoes.isEmpty() ) {
			return "{}".getBytes("UTF-8");
		}
		
		return new Gson().toJson(devolucoes).getBytes("UTF-8");
	}


}
