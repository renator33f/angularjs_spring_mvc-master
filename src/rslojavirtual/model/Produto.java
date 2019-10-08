package rslojavirtual.model;

// import java.util.ArrayList;
// import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
// import javax.persistence.ManyToMany;
// import javax.persistence.JoinColumn;
// import javax.persistence.JoinTable;
// import javax.persistence.CascadeType;


import org.hibernate.annotations.ForeignKey;


@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(columnDefinition = "text")
	private String foto;
	
	private String modelo;
	
	private String descricao;
	
	private String cores;
	
	private String valor = "";
	
	private String genero;
	
	private Long quantidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "fornecedor_fk")
	private Fornecedor fornecedor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "marca_fk")
	private Marca marca;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "categoria_fk")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "tamanho_fk")
	private Tamanho tamanho;
	
	/*
	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "tamanho_fk")
	private Tamanho tamanho2;
	*/
	
	/*
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="sapato_cor", joinColumns=
    {@JoinColumn(name="sapato_id")}, inverseJoinColumns=
      {@JoinColumn(name="cor_id")})
    private List<Cor> cores;
    */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getCores() {
		return cores;
	}

	public void setCores(String cores) {
		this.cores = cores;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}
	/*
	public Tamanho getTamanho2() {
		return tamanho2;
	}

	public void setTamanho2(Tamanho tamanho2) {
		this.tamanho2 = tamanho2;
	}
	*/
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	
		
	/*
	public List<Cor> getCores() {
        return cores;
    }

    public void setCores(List<Cor> cores) {
        this.cores = cores;
    }
	*/
	
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
