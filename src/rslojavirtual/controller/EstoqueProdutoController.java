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
import rslojavirtual.model.EstoqueProduto;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/estoqueproduto")
public class EstoqueProdutoController extends DaoImplementacao<EstoqueProduto> implements 
		DaoInterface<EstoqueProduto> {

	public EstoqueProdutoController(Class<EstoqueProduto> persistenceClass) { 
		super(persistenceClass); 
	}
	  
	/**
	 * Salva ou atualiza o Estoque do Produto
	 * @param jsonEstoqueProduto
	 * @return ResponseEntity
	 * @throws Exception
	**/
	
	@RequestMapping(value="salvar", method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity salvar (@RequestBody String jsonEstoqueProduto) throws Exception {
		EstoqueProduto estoqueproduto = new Gson().fromJson(jsonEstoqueProduto, EstoqueProduto.class);
		 
		 if (estoqueproduto != null && estoqueproduto.getQuantidade() == null){
			 estoqueproduto.setQuantidade(0L);
		 }
		 
		 super.salvarOuAtualizar(estoqueproduto);
		 return new ResponseEntity(HttpStatus.CREATED);
		 
	}
	
	
	/**
	  * Retorna a lista de Estoque do Produto cadastrados
	  * @return JSON String de Estoque do Produto
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
	 * Delete o Estoque do Produto informado
	 * @param codEstoqueProduto
	 * @return String vazia como resposta
	 * @throws Exception
	**/
	
	@RequestMapping(value="deletar/{codEstoqueProduto}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codEstoqueProduto") String codEstoqueProduto) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codEstoqueProduto)));
		return "";
	}
	
	
	/**
	 * Consulta e retorna o Estoque do Produto com o codigo informado
	 * @param codEstoqueProduto
	 * @return JSON Estoque Produto pesquisado
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscarestoqueproduto/{codEstoqueProduto}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarEstoqueProduto (@PathVariable("codEstoqueProduto") String codEstoqueProduto) throws Exception {
		EstoqueProduto objeto = super.loadObjeto(Long.parseLong(codEstoqueProduto));
		if (objeto == null) {
			return "{}".getBytes("UTF-8");
		}
		return new Gson().toJson(objeto).getBytes("UTF-8");
	}
	
	
	/**
	 * Consulta e retorna o Estoque Produto com o nome  informado
	 * @param nomeEstoqueProduto
	 * @return JSON Estoque Produto pesquisado
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscarnome/{nomeEstoqueProduto}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarNome (@PathVariable("nomeEstoqueProduto") String nomeEstoqueProduto) throws Exception {
		List<EstoqueProduto> estoqueprodutos = new ArrayList<EstoqueProduto>();
		estoqueprodutos = super.listaLikeExpression("modelo", nomeEstoqueProduto);

		if (estoqueprodutos == null || estoqueprodutos.isEmpty() ) {
			return "{}".getBytes("UTF-8");
		}
		
		return new Gson().toJson(estoqueprodutos).getBytes("UTF-8");
	}


}
