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
import rslojavirtual.model.Marca;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/marca")
public class MarcaController extends DaoImplementacao<Marca> implements 
DaoInterface<Marca> {

     public MarcaController(Class<Marca> persistenceClass) { 
       super(persistenceClass);
     }

     
     /**
     * Salva ou atualiza a Marca
     * @param jsonMarca
     * @return ResponseEntity
     * @throws Exception
     **/
     @RequestMapping(value="salvar", method= RequestMethod.POST)
     @ResponseBody
     public ResponseEntity salvar (@RequestBody String jsonMarca) throws Exception {
	    Marca marca = new Gson().fromJson(jsonMarca, Marca.class);

        if (marca != null && marca.getNome() == null){
        marca.setNome("");
        }

        super.salvarOuAtualizar(marca);
        return new ResponseEntity(HttpStatus.CREATED);
     }


     /**
     * Retorna a lista de Marcas cadastradas
     * @return JSON String de Marcas
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
     * Delete a Marca informada
     * @param codMarca
     * @return String vazia como resposta
     * @throws Exception
     **/
     @RequestMapping(value="deletar/{codMarca}", method=RequestMethod.DELETE)
     public  @ResponseBody String deletar (@PathVariable("codMarca") String codMarca) throws Exception {
        super.deletar(loadObjeto(Long.parseLong(codMarca)));
        return "";
     }


     /**
     * Consulta e retorna a Marca com o codigo informado
     * @param codMarca
     * @return JSON Marca pesquisada
     * @throws Exception
     **/
     @RequestMapping(value="buscarmarca/{codMarca}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarMarca (@PathVariable("codMarca") String codMarca) throws Exception {
        Marca objeto = super.loadObjeto(Long.parseLong(codMarca));
        if (objeto == null) {
           return "{}".getBytes("UTF-8");
        }
           return new Gson().toJson(objeto).getBytes("UTF-8");
     }


     /**
     * Consulta e retorna a Marca com o nome  informado
     * @param nomeMarca
     * @return JSON Marca pesquisado
     * @throws Exception
     **/
     @RequestMapping(value="buscarnome/{nomeMarca}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarNome (@PathVariable("nomeMarca") String nomeMarca) throws Exception {
        List<Marca> marcas = new ArrayList<Marca>();
        marcas = super.listaLikeExpression("nome", nomeMarca);

        if (marcas == null || marcas.isEmpty() ) {
           return "{}".getBytes("UTF-8");
        }

           return new Gson().toJson(marcas).getBytes("UTF-8");
     }

}
