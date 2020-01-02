package rslojavirtual.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import rslojavirtual.model.EstoqueProduto;

import org.hibernate.annotations.ForeignKey;

/**
 * Modelo que representa a tabela de Devolução de produtos(Pedido) no estoque do banco
 * @author renatosalinas
 *
 */
@Entity
public class Devolucao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name = "pedido_fk")
	private Pedido pedido;
	
	@ManyToOne
	@ForeignKey(name = "itempedido_fk")
	private ItemPedido itempedido;

	@ManyToOne
	@ForeignKey(name = "estoqueproduto_fk")
	private EstoqueProduto estoqueproduto;
	
	private Long quantidade;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public ItemPedido getItemPedido() {
		return itempedido;
	}

	public void setItemPedido(ItemPedido itempedido) {
		this.itempedido = itempedido;
	}
	
	public EstoqueProduto getEstoqueProduto() {
		return estoqueproduto;
	}

	public void setEstoqueProduto(EstoqueProduto estoqueproduto) {
		this.estoqueproduto = estoqueproduto;
	}
	
	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
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
		Devolucao other = (Devolucao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

