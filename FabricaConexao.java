package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

public class FabricaConexao {
   private static final String timezone = "?useTimezone=true&serverTimezone=UTC";
   private static final String URL = "jdbc:mysql://localhost/sisifpr" + timezone;
   private static final String USUARIO = "root";
   private static final String SENHA = "123456";

   public Connection getConnection() {
      try {
         //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
         return DriverManager.getConnection(URL + timezone, USUARIO, SENHA);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         System.out.println(e);
         throw new RuntimeException(e);
      }
   }
}
