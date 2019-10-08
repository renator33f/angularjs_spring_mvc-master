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
import rslojavirtual.model.Categoria;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/categoria")
public class CategoriaController extends DaoImplementacao<Categoria> implements 
DaoInterface<Categoria> {

     public CategoriaController(Class<Categoria> persistenceClass) { 
       super(persistenceClass);
     }

     
     /**
     * Salva ou atualiza a Categoria
     * @param jsonMarca
     * @return ResponseEntity
     * @throws Exception
     **/
     @RequestMapping(value="salvar", method= RequestMethod.POST)
     @ResponseBody
     public ResponseEntity salvar (@RequestBody String jsonCategoria) throws Exception {
    	 Categoria categoria = new Gson().fromJson(jsonCategoria, Categoria.class);

        if (categoria != null && categoria.getNome() == null){
        categoria.setNome("");
        }

        super.salvarOuAtualizar(categoria);
        return new ResponseEntity(HttpStatus.CREATED);
     }


     /**
     * Retorna a lista de Categoria cadastradas
     * @return JSON String de Categoria
     * @throws Exception
     **/
     @RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
     @ResponseBody
     public byte[] listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
        return new Gson().toJson(super.consultaPaginada(numeroPagina)).getBytes("UTF-8");
     }
     
     @RequestMapping(value = "listartodos", method = RequestMethod.GET, headers = "Accept=application/json")
 	 @ResponseBody
 	 public String listartodos()
 			throws Exception {
 		return new Gson().toJson(super.lista());
 	 }

     @RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
     @ResponseBody
     public String totalPagina() throws Exception {
        return ""+super.quantidadePagina(); 
     }


     /**
     * Delete a Categoria informada
     * @param codCategoria
     * @return String vazia como resposta
     * @throws Exception
     **/
     @RequestMapping(value="deletar/{codCategoria}", method=RequestMethod.DELETE)
     public  @ResponseBody String deletar (@PathVariable("codCategoria") String codCategoria) throws Exception {
        super.deletar(loadObjeto(Long.parseLong(codCategoria)));
        return "";
     }


     /**
     * Consulta e retorna a Categoria com o codigo informado
     * @param codCategoria
     * @return JSON Categoria pesquisada
     * @throws Exception
     **/
     @RequestMapping(value="buscarcategoria/{codCategoria}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarCategoria (@PathVariable("codCategoria") String codCategoria) throws Exception {
    	 Categoria objeto = super.loadObjeto(Long.parseLong(codCategoria));
        if (objeto == null) {
           return "{}".getBytes("UTF-8");
        }
           return new Gson().toJson(objeto).getBytes("UTF-8");
     }


     /**
     * Consulta e retorna a Categoria com o nome  informado
     * @param nomeCategoria
     * @return JSON Categoria pesquisado
     * @throws Exception
     **/
     @RequestMapping(value="buscarnome/{nomeCategoria}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarNome (@PathVariable("nomeCategoria") String nomeCategoria) throws Exception {
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias = super.listaLikeExpression("nome", nomeCategoria);

        if (categorias == null || categorias.isEmpty() ) {
           return "{}".getBytes("UTF-8");
        }

           return new Gson().toJson(categorias).getBytes("UTF-8");
     }

}
