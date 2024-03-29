package rslojavirtual.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rslojavirtual.dao.DaoImplementacao;
import rslojavirtual.dao.DaoInterface;
import rslojavirtual.hibernate.HibernateUtil;
import rslojavirtual.model.ItemPedido;
import rslojavirtual.model.Pedido;
import rslojavirtual.model.PedidoBean;
import rslojavirtual.model.EstoqueProduto; 
import rslojavirtual.model.Produto;
import rslojavirtual.model.Vendedor;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/pedido")
public class PedidoController extends DaoImplementacao<Pedido> implements
		DaoInterface<Pedido> {

	@Autowired
	private ItemPedidoController itemPedidoController;

	@Autowired
	private ProdutoController produtoController;
	
	@Autowired
	private EstoqueProdutoController estoqueprodutoController;

	
	public PedidoController(Class<Pedido> persistenceClass) {
		super(persistenceClass);
	}

	@RequestMapping(value = "grafico", method = RequestMethod.GET)
	public @ResponseBody String grafico() {

		String sql = "select trunc(avg(ip.quantidade),2) as media, p.modelo"
				+ " from produto p "
				+ " inner join  itempedido ip on ip.produto_id = p.id"
				+ " group by p.id";
		
		List<Object[]> lista = getSessionFactory().getCurrentSession().createSQLQuery(sql).list();

		Object[] retorno = new Object[lista.size() + 1];
		int cont = 0;
		
		
		retorno[cont] = "[\"" + "Produto" +  "\"," + "\"" + "Quantidade " + "\"]";
		cont ++;
		
		for (Object[] object : lista) {
			retorno[cont] = "[\"" + object[1] +  "\"," + "\"" + object[0] + "\"]";
			cont ++;
		}
		
		
		return Arrays.toString(retorno); 

	}

	@RequestMapping(value = "finalizar", method = RequestMethod.POST)
	@ResponseBody
	public String finalizar(@RequestBody String jsonPedido) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		PedidoBean pedidoBean = new Gson().fromJson(jsonPedido,
				PedidoBean.class);

		Pedido pedido = pedidoBean.getPedido();
		
		pedido.setData(Calendar.getInstance().getTime());

		pedido = super.merge(pedido);
		
		List<ItemPedido> inItemPedidos = pedidoBean.getItens();
		
		List<EstoqueProduto> estoques = estoqueprodutoController.lista();
		
		List<Produto> produtos = produtoController.lista();

		for (ItemPedido itemPedido : inItemPedidos) {
			
		   itemPedido.setPedido(pedido);
		   JOptionPane.showMessageDialog(null, "mensagem aki");
		   		
			
			//itemPedido.setProduto(produto);
			//Produto estoqproduto = itemPedido.getProduto();
			//estoqproduto.setQuantidade(estoqproduto.getQuantidade() - itemPedido.getQuantidade());
		    //sessao.update(jsonPedido, estoqproduto);
			//System.out.println(itemPedido.getProduto());
		   
		  for (EstoqueProduto estoqueproduto: estoques){
			  
			  if (estoqueproduto.getProduto().getId() == itemPedido.getProduto().getId()) {
				  
				  itemPedido.setEstoqueProduto(estoqueproduto);
				  estoqueproduto.setQuantidade(estoqueproduto.getQuantidade() - itemPedido.getQuantidade());
				  
				  
					 JOptionPane.showMessageDialog(null, "mensagem aki 2");
					 JOptionPane.showMessageDialog(null, itemPedido.getProduto());
					 JOptionPane.showMessageDialog(null, estoqueproduto.getProduto());
					 JOptionPane.showMessageDialog(null, estoqueproduto.getQuantidade());
					 JOptionPane.showMessageDialog(null, itemPedido.getQuantidade());
					 JOptionPane.showMessageDialog(null, estoqueproduto.getQuantidade());
				  
			  }
			 
		  }  
			 
			  	 
		  
		   
		   for (Produto produto: produtos) {
			   
			   itemPedido.setProduto(produto);
			   
			 //  EstoqueProduto estoqproduto = itemPedido.getProduto();
			 //  estoqproduto.setQuantidade(estoqproduto.getQuantidade() - itemPedido.getQuantidade());
			 //  sessao.update(estoqproduto);	
			 
			 //  inItemPedidos.add(itemPedido);
			   itemPedidoController.salvar(itemPedido);
			    
			   // JOptionPane.showMessageDialog(null, "mensagem aki 2");
			   // JOptionPane.showMessageDialog(null, itemPedido.getProduto());
			   // JOptionPane.showMessageDialog(null, estoqproduto.getId());
			   // JOptionPane.showMessageDialog(null, estoqproduto.getQuantidade());
			   // JOptionPane.showMessageDialog(null, itemPedido.getQuantidade());
			  // JOptionPane.showMessageDialog(null, estoqproduto.getQuantidade());
		  
		   }
		   
		}

		return pedido.getId().toString();

	}

	@RequestMapping(value = "listar", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar() throws Exception {
		return new Gson().toJson(super.lista());
	}
	
	
	/**
	 * Consulta e retorna o Pedido com o codigo  informado
	 * @param codigoPedido
	 * @return JSON Pedido pesquisado
	 * @throws Exception
	**/
	
	@RequestMapping(value="buscarcodigo/{codPedido}", method=RequestMethod.GET)
	public  @ResponseBody String buscarPedido (@PathVariable("codPedido") String codPedido) throws Exception {
		Pedido objeto = super.loadObjeto(Long.parseLong(codPedido));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}
	
	/*
	@RequestMapping(value="buscarcodigo/{codPedido}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarCodigo (@PathVariable("codPedido") String codPedido) throws Exception {
		Pedido objeto = super.loadObjeto(Long.parseLong(codPedido));
		if (objeto == null) {
			return "{}".getBytes("UTF-8");
		}
		return new Gson().toJson(objeto).getBytes("UTF-8");
	} */
	
	
	@RequestMapping(value="buscarnomepedido/{nomePedido}", method=RequestMethod.GET)
	public  @ResponseBody byte[] buscarNome (@PathVariable("nomePedido") String nomePedido) throws Exception {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		// pedidos = super.listaLikeExpression("id", nomePedido);
		pedidos = (List<Pedido>) super.loadObjeto(Long.parseLong(nomePedido));

		if (pedidos == null || pedidos.isEmpty() ) {
			return "{}".getBytes("UTF-8");
		}
		
		return new Gson().toJson(pedidos).getBytes("UTF-8");
	}
	

	@RequestMapping(value = "deletar/{codPedido}", method = RequestMethod.DELETE)
	public @ResponseBody
	String deletar(@PathVariable("codPedido") String codPedido)
			throws Exception {

		List<ItemPedido> itemPedidos = itemPedidoController.lista("pedido.id",
				Long.parseLong(codPedido));

		for (ItemPedido itemPedido : itemPedidos) {
			itemPedidoController.deletar(itemPedido);
		}
		super.deletar(loadObjeto(Long.parseLong(codPedido)));
		return new Gson().toJson(super.lista());
	}

}
