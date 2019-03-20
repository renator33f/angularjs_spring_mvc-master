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
import rslojavirtual.model.Tamanho;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/tamanho")
public class TamanhoController extends DaoImplementacao<Tamanho> implements 
       DaoInterface<Tamanho> {

     public TamanhoController(Class<Tamanho> persistenceClass) { 
         super(persistenceClass); 
     }

     
     /**
     * Salva ou atualiza o Tamanho
     * @param jsonTamanho
     * @return ResponseEntity
     * @throws Exception
     **/
     @RequestMapping(value="salvar", method= RequestMethod.POST)
     @ResponseBody
     public ResponseEntity salvar (@RequestBody String jsonTamanho) throws Exception {
    	 Tamanho tamanho = new Gson().fromJson(jsonTamanho, Tamanho.class);

         if (tamanho != null && tamanho.getTamanhope() == null){
         tamanho.setTamanhope("");
         }

        super.salvarOuAtualizar(tamanho);
        return new ResponseEntity(HttpStatus.CREATED);
     }


     /**
     * Retorna a lista de Tamanhos cadastrados
     * @return JSON String de Tamanhos
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
     * Delete o Tamanho informado
     * @param codTamanho
     * @return String vazia como resposta
     * @throws Exception
     **/
     @RequestMapping(value="deletar/{codTamanho}", method=RequestMethod.DELETE)
     public  @ResponseBody String deletar (@PathVariable("codTamanho") String codTamanho) throws Exception {
        super.deletar(loadObjeto(Long.parseLong(codTamanho)));
        return "";
     }


     /**
     * Consulta e retorna o Tamanho com o codigo informado
     * @param codTamanho
     * @return JSON Tamanho pesquisado
     * @throws Exception
     **/
     @RequestMapping(value="buscartamanho/{codTamanho}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarTamanho (@PathVariable("codTamanho") String codTamanho) throws Exception {
        Tamanho objeto = super.loadObjeto(Long.parseLong(codTamanho));
     if (objeto == null) {
	    return "{}".getBytes("UTF-8");
     }
        return new Gson().toJson(objeto).getBytes("UTF-8");
     }


     /**
     * Consulta e retorna o Tamanho com o nome  informado
     * @param nomeTamanho
     * @return JSON Tamanho pesquisado
     * @throws Exception
     **/
     @RequestMapping(value="buscarnome/{nomeTamanho}", method=RequestMethod.GET)
     public  @ResponseBody byte[] buscarNome (@PathVariable("nomeTamanho") String nomeTamanho) throws Exception {
       List<Tamanho> tamanhos = new ArrayList<Tamanho>();
       tamanhos = super.listaLikeExpression("nome", nomeTamanho);

       if (tamanhos == null || tamanhos.isEmpty() ) {
          return "{}".getBytes("UTF-8");
       }

       return new Gson().toJson(tamanhos).getBytes("UTF-8");
     }

}
