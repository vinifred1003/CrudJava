package br.edu.ifpr.dao;

import java.util.List;

public interface Dao {

   void inserir(Produto produto);

   void atualizar(Produto produto);

   void remover(Produto produto);

   Produto buscarNoBanco(String produto );
   
   Produto pesquisarId(int id);
 
   
   List<Produto> getTodos();

}