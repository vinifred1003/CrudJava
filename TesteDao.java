package br.edu.ifpr.dao;

import java.util.List;

public class TesteDao {

   public static void main(String[] args) {
      ProdutoDao estoque = new ProdutoDao();
      List<Produto> registros = estoque.getTodos();
      for(int i=0; i<registros.size(); i++)
         System.out.println(registros.get(i));
   }

}