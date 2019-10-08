package rslojavirtual.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rslojavirtual.dao.DaoImplementacao;
import rslojavirtual.dao.DaoInterface;
import rslojavirtual.model.ItemPedido;
import rslojavirtual.model.Produto;
import rslojavirtual.model.Pedido;
import rslojavirtual.model.EstoqueProduto;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/itempedido")
public class ItemPedidoController extends DaoImplementacao<ItemPedido> implements
		DaoInterface<ItemPedido> {
	
	//private static final EstoqueProduto Long = null;

	@Autowired
	private ProdutoController produtoController;
	
	@Autowired
    private EstoqueProdutoController estoqueProdutoController;

	public ItemPedidoController(Class<ItemPedido> persistenceClass) {
		super(persistenceClass);
	}
	
	@RequestMapping(value="processar/{itens}")
	public @ResponseBody String processar(@PathVariable("itens") String itens) throws Exception{
		List<Produto> produtos = produtoController.lista(itens);
		//List<EstoqueProduto> estoquep = estoqueProdutoController.lista(itens);
		List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();	    
		
		Pedido pedido = new Pedido();
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (Produto produto: produtos) {
			String valor = produto.getValor().replace("R", "").replace("$", "").replaceAll("\\.", "").replaceAll("\\,", ".");
			valorTotal = valorTotal.add(new BigDecimal(valor.trim()));
		}
		
		/*** Tela ItensLoja - Apresenta a pagina do carrinho com os produtos selecionados ***/
		pedido.setValorTotal("R$" + valorTotal.setScale(2, RoundingMode.HALF_DOWN).toString());
		for (Produto produto: produtos) {			
			ItemPedido itemPedido  = new ItemPedido();
			itemPedido.setProduto(produto);
			itemPedido.setPedido(pedido);
			itemPedido.setQuantidade(1L);
			itemPedido.setEstoqueprodutoid(produto.getId());
			itemPedidos.add(itemPedido);
			
			// for (EstoqueProduto estoqueproduto : produtos) {
			
			//	itemPedido.setEstoqueProduto(estoqueproduto);
		//	}
			
		//	Produto estoquepp = itemPedido.getProduto();
		//	estoquepp.setQuantidade(estoquepp.getQuantidade() - itemPedido.getQuantidade());
			
			/*
		    String estoquepp = itemPedido.getEstoqueprodutoid().toString();
		    
		    EstoqueProduto itemEstoque = (EstoqueProduto) itemPedidos;
		    
		    estoquepp = itemEstoque.getProduto().toString();
		    
			itemEstoque.setQuantidade(itemEstoque.getQuantidade() - itemPedido.getQuantidade());
			*/
					
			//itemPedido.getEstoqueprodutoid();
			
			
			//estoquepp.setQuantidade(estoquepp.getQuantidade() - itemPedido.getQuantidade());
		  
		  }
		
		return new Gson().toJson(itemPedidos);
	}

	//private EstoqueProduto Long(java.lang.Long estoqueprodutoid) {
		// TODO Auto-generated method stub
	//	return null;
	//}
	

}
