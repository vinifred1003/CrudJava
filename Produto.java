package br.edu.ifpr.dao;

public class Produto {
   private int id;
   private String descricao;
   private int estoque;
   private double preco;
   private byte[] imagem;

   public byte[] getImagem() {
	return imagem;
}

public void setImagem(byte[] imagem) {
	this.imagem = imagem;
}

public Produto() {
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public int getEstoque() {
      return estoque;
   }

   public void setEstoque(int estoque) {
      this.estoque = estoque;
   }

   public double getPreco() {
      return preco;
   }

   public void setPreco(double preco) {
      this.preco = preco;
   }

   @Override
   public String toString() {
      return "Produto [id=" + id + ", descricao=" + descricao + ", estoque=" + estoque + ", preco=" + preco + "]";
   }
}
