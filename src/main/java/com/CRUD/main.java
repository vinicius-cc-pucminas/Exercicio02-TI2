package com.CRUD;

import java.util.*;

public class main {

	private static Scanner sc = new Scanner(System.in);
	
	public static void listar(DAO dao) {
		Fazenda[] fazendas = dao.getFazendas();
		
		System.out.println("==== Listar fazendas === ");		
		for(int i = 0; i < fazendas.length; i++) {
			System.out.println(fazendas[i].listar());
		}
		
		System.out.println("---");		
	}
	
	public static void inserir(DAO dao) {
		int codigo, vacas, galinhas, porcos;
		String fazendeiro;
		Fazenda fazenda = null; 
		
		System.out.print("==== Inserir fazenda ===\n > código: ");
		codigo = sc.nextInt(); 
		
		System.out.print(" > fazendeiro: ");
		fazendeiro = sc.next(); 
		
		System.out.print(" > vacas: ");
		vacas = sc.nextInt(); 
		
		System.out.print(" > galinhas: ");
		galinhas = sc.nextInt(); 
		
		System.out.print(" > porcos: ");
		porcos = sc.nextInt(); 
		
		fazenda = new Fazenda(codigo, fazendeiro, vacas, porcos, galinhas);
		
		if (dao.inserir(fazenda)) {
			System.out.println("Fazenda inserida com sucesso -> " + fazenda.listar());
		} else {
			System.out.print("Não foi possível inserir esta fazenda\n---\n");
		}
		
	}
	
	public static void excluir(DAO dao) {
		int codigo;
		System.out.print("==== Deletando fazenda ===\n > código: ");
		codigo = sc.nextInt(); 

		if (dao.getFazenda(codigo)) {
			if (dao.excluir(codigo)) {
				System.out.print("Fazenda excluída com sucesso\n---\n");
			} else {
				System.out.print("Não foi possível excluir esta fazenda\n---\n");
			}
		} else {
			System.out.print("---\nNão existe fazenda com este código\n---\n");
		}
	}
	
	public static void alterar(DAO dao) {
		int codigo, vacas, galinhas, porcos;
		String fazendeiro;
		Fazenda fazenda = null; 
		
		System.out.print("==== Alterar fazenda ===\n > código: ");
		codigo = sc.nextInt(); 
		
		if (dao.getFazenda(codigo)) {
			System.out.print(" > fazendeiro: ");
			fazendeiro = sc.next(); 
			
			System.out.print(" > vacas: ");
			vacas = sc.nextInt(); 
			
			System.out.print(" > galinhas: ");
			galinhas = sc.nextInt(); 
			
			System.out.print(" > porcos: ");
			porcos = sc.nextInt(); 
			
			fazenda = new Fazenda(codigo, fazendeiro, vacas, porcos, galinhas);
			
			if (dao.atualizar(fazenda)) {
				System.out.println("Fazenda alterarada com sucesso -> " + fazenda.listar());
			} else {
				System.out.print("Não foi possível alterar esta fazenda\n---\n");
			}
		} else {
			System.out.print("---\nNão existe fazenda com este código\n---\n");
		}
	}
	
	public static void main(String[] args) {
		int option = 0;
		
		DAO dao = new DAO();
		dao.conectar();
		
		while (option != 5) {
			System.out.println("==== Menu =====");
			System.out.print(" > Listar (1)\n > Inserir (2)\n > Excluir(3)\n > Alterar(4)\n > Sair(5)\n");
			option = sc.nextInt();

			if (option > 5) {
				System.out.print("---\nInforme um número de 1 à 5\n---\n");
			} else {
				switch(option) {
					case 1: listar(dao); break;
					case 2: inserir(dao); break;
					case 3: excluir(dao); break;
					case 4: alterar(dao); break;
					case 5:
						System.out.println("Saindo...");
						dao.close(); 
						System.exit(0);
						break;
				}
			}
		}
	}

}
