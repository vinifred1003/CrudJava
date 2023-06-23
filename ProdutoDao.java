package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProdutoDao implements Dao {

   private Connection con;
   PreparedStatement ps;
   ResultSet rs;

   public ProdutoDao() {
      con = new FabricaConexao().getConnection();
   }

   // ****************************
   // ********** inserir produto ***
   // ****************************
   @Override
   public void inserir(Produto produto) {
      String insert = "Insert into Produtos "
            + "(descricao,estoque,preco,imagem) values (?,?,?,?)";
      try {
         ps = con.prepareStatement(insert);
         ps.setString(1, produto.getDescricao());
         ps.setInt(2, produto.getEstoque());
         ps.setDouble(3, produto.getPreco());
         ps.setBytes(4, produto.getImagem());
         ps.execute();
         ps.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // ****************************
   // ********** atualizar produto *
   // ****************************
   @Override
   public void atualizar(Produto produto) {
      String atualiza = "Update Produtos "
            + "set descricao = ?, estoque = ?, preco = ?, imagem = ? where id = ?";
      try {
         ps = con.prepareStatement(atualiza);         
         ps.setString(1, produto.getDescricao());
         ps.setInt(2, produto.getEstoque());
         ps.setDouble(3, produto.getPreco());
         ps.setBytes(4, produto.getImagem());
         ps.setInt(5, produto.getId());
         ps.executeUpdate();
         ps.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   // ****************************
   // ********** remover produto ***
   // ****************************
   @Override
   public void remover(Produto produto) {
      String remove = "delete from Produtos Where id = ?";
      try {
         ps = con.prepareStatement(remove);
         ps.setInt(1, produto.getId());
         ps.executeUpdate();
         ps.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   // ****************************
   // ********** pesquisar produto *
   // ****************************
   @Override
   public Produto pesquisarId(int Id) {
      // TODO Auto-generated method stub
      Produto produto = new Produto();
      String pesquisa = "Select * from Produtos where id = ?";
      try {
         ps = con.prepareStatement(pesquisa);
         ps.setInt(1, Id);
         rs = ps.executeQuery();
         while (rs.next()) {
            produto.setId(rs.getInt("id"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setEstoque(rs.getInt("estoque"));
            produto.setPreco(rs.getDouble("preco"));
         }
         ps.close();
         rs.close();
         return produto;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   // ****************************
   // ********** buscar todos os produtos
   // ****************************
   @Override
   public List<Produto> getTodos() {
      List<Produto> lista = new ArrayList<>();
      String pesqLista = ("Select * from Produtos");
      try {
         ps = con.prepareStatement(pesqLista);
         rs = ps.executeQuery();
         while (rs.next()) {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setEstoque(rs.getInt("estoque"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setImagem(rs.getBytes("imagem"));
            lista.add(produto);
         }
         ps.close();
         rs.close();
         return lista;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
   public Produto buscarNoBanco(String termoBusca) {
	   Produto produto = new Produto();
	   String query = "SELECT * FROM produtos WHERE descricao LIKE '%" + termoBusca + "%'";
	   try {
	         ps = con.prepareStatement(query);
	         rs = ps.executeQuery();
	         while (rs.next()) {
	            produto.setId(rs.getInt("id"));
	            produto.setDescricao(rs.getString("descricao"));
	            produto.setEstoque(rs.getInt("estoque"));
	            produto.setPreco(rs.getDouble("preco"));
	         }
	         ps.close();
	         rs.close();
	         return produto;
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return null;
	   }
   }

