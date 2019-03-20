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
import rslojavirtual.model.Cor;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/cor")
public class CorController  extends DaoImplementacao<Cor> implements 
       DaoInterface<Cor> {

    public CorController(Class<Cor> persistenceClass) { 
        super(persistenceClass); 
    }
    

    /**
    * Salva ou atualiza a cor
    * @param jsonCliente
    * @return ResponseEntity
    * @throws Exception
    **/
    @RequestMapping(value="salvar", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity salvar (@RequestBody String jsonCor) throws Exception {
       Cor cor = new Gson().fromJson(jsonCor, Cor.class);
 
       if (cor != null && cor.getAtivo() == null){
	   cor.setAtivo(false);
       }
 
       super.salvarOuAtualizar(cor);
       return new ResponseEntity(HttpStatus.CREATED);
 
    }
    

    /**
    * Retorna a lista de cores cadastradas
    * @return JSON String de Cores
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
    * Delete a cor informada
    * @param codCor
    * @return String vazia como resposta
    * @throws Exception
    **/
    @RequestMapping(value="deletar/{codCor}", method=RequestMethod.DELETE)
    public  @ResponseBody String deletar (@PathVariable("codCor") String codCor) throws Exception {
       super.deletar(loadObjeto(Long.parseLong(codCor)));
       return "";
    }


    /**
    * Consulta e retorna a cor com o codigo informado
    * @param codCor
    * @return JSON cor pesquisada
    * @throws Exception
    **/
    @RequestMapping(value="buscarcor/{codCor}", method=RequestMethod.GET)
    public  @ResponseBody byte[] buscarCor (@PathVariable("codCor") String codCor) throws Exception {
    Cor objeto = super.loadObjeto(Long.parseLong(codCor));
    if (objeto == null) {
     	return "{}".getBytes("UTF-8");
    }
        return new Gson().toJson(objeto).getBytes("UTF-8");
    }


    /**
    * Consulta e retorna a cor com o nome  informado
    * @param nomeCor
    * @return JSON cor pesquisada
    * @throws Exception
    **/
    @RequestMapping(value="buscarnome/{nomeCor}", method=RequestMethod.GET)
    public  @ResponseBody byte[] buscarNome (@PathVariable("nomeCor") String nomeCor) throws Exception {
       List<Cor> cores = new ArrayList<Cor>();
       cores = super.listaLikeExpression("nome", nomeCor);

       if (cores == null || cores.isEmpty() ) {
	      return "{}".getBytes("UTF-8");
       }

       return new Gson().toJson(cores).getBytes("UTF-8");
    }

    
}
