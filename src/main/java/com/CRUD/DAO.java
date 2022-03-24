package com.CRUD;

import java.sql.*;

public class DAO {
	private Connection conexao; 
	
	public DAO() {
		conexao = null; 
	}
	
	public boolean conectar() {
		String driver = "org.postgresql.Driver"; 
		String server = "localhost";
		String db = "teste"; 
		int porta = 5432; 
		String url = "jdbc:postgresql://" + server + ":" + porta +"/" + db;

		String user = "usuario";
		String pass = "senha"; 
		
		boolean status = false; 
		
		try {
			Class.forName(driver); 
			conexao = DriverManager.getConnection(url, user, pass); 
			status = (conexao == null); 
			System.out.println("Aplicação está conectada ao banco!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Erro ao conectar com o banco (Driver não foi encontrado)" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Erro ao conectar com o banco" + e.getMessage());
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false; 
		
		try {
			conexao.close();
			status = true; 
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return status;
	}
	

	public boolean inserir(Fazenda fazenda) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO fazenda (codigo, fazendeiro, vacas, galinhas, porcos) "
					       + "VALUES ("+fazenda.getCodigo()+ ", '" + fazenda.getFazendeiro() + "', "  
					       + fazenda.getVacas() + ", " + fazenda.getGalinhas() + " , " + fazenda.getPorcos + ");");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean atualizar(Fazenda fazenda) {
		boolean status = false;
		
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE fazenda SET fazendeiro = '" + fazenda.getFazendeiro() + "', vacas = "  
				       + fazenda.getVacas() + ", galinhas = " + fazenda.getGalinhas() + " WHERE codigo = " + fazenda.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean excluir(int codigo) {
		boolean status = false;
		
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM fazenda WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public Fazenda[] getFazendas() {
		Fazenda[] fazendas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM fazenda");		
	         if(rs.next()){
	             rs.last();
	             fazendas = new Fazenda[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                fazendas[i] = new Fazenda(rs.getInt("codigo"), rs.getString("fazendeiro"), rs.getInt("vacas"),  rs.getInt("porcos"),  rs.getInt("galinhas"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return fazendas;
	}

}
