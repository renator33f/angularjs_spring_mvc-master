package rslojavirtual.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rslojavirtual.dao.DaoImplementacao;
import rslojavirtual.dao.DaoInterface;
import rslojavirtual.model.Cliente;
import rslojavirtual.model.Produto;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/produto")
public class ProdutoController extends DaoImplementacao<Produto> implements
		DaoInterface<Produto> {

	public ProdutoController(Class<Produto> persistenceClass) {
		super(persistenceClass);
	}
	

	 @RequestMapping(value="salvar", method= RequestMethod.POST)
	 @ResponseBody
	 public ResponseEntity salvar(@RequestBody String jsonProduto) throws Exception {
		 Produto produto = new Gson().fromJson(jsonProduto, Produto.class);
		 
		 super.salvarOuAtualizar(produto);
		 return new ResponseEntity(HttpStatus.CREATED);
		 
	 }
	
	
	/**
	  * Retorna a lista de Produto cadastrados
	  * @return JSON String de Produto
	  * @throws Exception
	**/
	@RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina)); 
	}
	
	
	@RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String totalPagina() throws Exception {
		return ""+super.quantidadePagina(); 
	}
	 
	/**
	 * Delete o Produto informado
	 * @param codProduto
	 * @return String vazia como resposta
	 * @throws Exception
	**/
	@RequestMapping(value="deletar/{codProduto}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codProduto") String codProduto) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codProduto)));
		return "";
	}
	
	
	/**
	 * Consulta e retorna o Produto com o codigo informado
	 * @param codProduto
	 * @return JSON Produto pesquisado
	 * @throws Exception
	**/
	@RequestMapping(value="buscarproduto/{codProduto}", method=RequestMethod.GET)
	public  @ResponseBody String buscarProduto (@PathVariable("codProduto") String codProduto) throws Exception {
		Produto objeto = super.loadObjeto(Long.parseLong(codProduto));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}
	
	
	/**
	 * Consulta e retorna o produto com o nome  informado
	 * @param nomeProduto
	 * @return JSON produto pesquisado
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscarnome/{nomeProduto}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarNome (@PathVariable("nomeProduto") String nomeProduto) throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		produtos = super.listaLikeExpression("modelo", nomeProduto);

		if (produtos == null || produtos.isEmpty() ) {
			return "{}".getBytes("UTF-8");
		}
		
		return new Gson().toJson(produtos).getBytes("UTF-8");
	}

	
	
	@RequestMapping(value="autenticar", method=RequestMethod.GET)
	public  @ResponseBody String autenticar () throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("permissao", true);
		map.put("user", authentication.getName());
		
		List<String> permissoes = new ArrayList<String>();
		
		Iterator<GrantedAuthority> iterator = (Iterator<GrantedAuthority>) authentication.getAuthorities().iterator();
		
		while (iterator.hasNext()){
			permissoes.add(iterator.next().getAuthority());
		}
		
		map.put("permissoes", permissoes);
		
		return new Gson().toJson(map);
	}
	
	

}












/*
 * 	Map<String, Object> map = new HashMap<String, Object>();
		List<String> permiStrings = new ArrayList<String>();
		map.put("permissao", true);
		map.put("user", authentication.getName());
		
		Iterator<GrantedAuthority>   iterator  = (Iterator<GrantedAuthority>) authentication.getAuthorities().iterator();
		
		while (iterator.hasNext()) {
			permiStrings.add(iterator.next().getAuthority());
			
		}
		
		map.put("permissoes", permiStrings);
 * */
 