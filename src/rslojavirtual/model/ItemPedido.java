package rslojavirtual.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@ForeignKey(name = "produto_fk")
	private Produto produto;

	@ManyToOne
	@ForeignKey(name = "pedido_fk")
	private Pedido pedido;
	
	private Long estoqueprodutoid;
	
	/*
	@ManyToOne
	@ForeignKey(name = "estoqueproduto_fk")
	private EstoqueProduto estoqueproduto;
    */
	
	private Long quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	/*
	public EstoqueProduto getEstoqueProduto() {
		return estoqueproduto;
	}

	public void setEstoqueProduto(Produto produto) {
		this.estoqueproduto = estoqueproduto;
	}
    */
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	public Long getEstoqueprodutoid() {
		return estoqueprodutoid;
	}

	public void setEstoqueprodutoid(Long estoqueprodutoid) {
		this.estoqueprodutoid = estoqueprodutoid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
