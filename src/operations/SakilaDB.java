package operations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import sakila_java_db.DatabaseConnection;
import operations.DeleteOperation;
import operations.InsertOperation;
import operations.SelectOperation;
import operations.UpdateOperation;
import operations.ViewManager;

public class SakilaDB {
	private static Connection con;
	private static final Scanner scan = new Scanner(System.in);
	static final String RESET = "\u001B[0m";
	static final String CYAN = "\u001B[36m";
	static final String GREEN = "\u001B[32m";
	static final String RED = "\u001B[31m";
	
	
	private static String chooseTable() {
		while(true) {
			System.out.println(CYAN + "\n--- TABULAS ---\n" + RESET
					+ "1. Address\n"
					+ "2. Film\n"
					+ "3. Category\n"
					+ "4. Staff\n"
					+ "0. Atpakal\n"
					+ "Izvelies tabulu: ");
			String c = scan.nextLine().trim();
			
			return switch(c) {
			case "1" -> "address";
			case "2" -> "film";
			case "3" -> "category";
			case "4" -> "staff";
			case "0" -> "exit";
			default -> {
				System.out.println("Nepareiza izvele.");
				yield "exit";
				}
			};
		}
	}
	
	private static void tableMenu(String table, SelectOperation selectOp, InsertOperation insertOp, UpdateOperation updateOp, DeleteOperation deleteOp) {
		boolean back = false;
		while(!back) {
			System.out.println("\n---" + table.toUpperCase() + "---\n"
					+ "1. Atlasit (SELECT)\n"
					+ "2. Pievienot (INSERT)\n"
					+ "3. Atjauninat (UPDATE)\n"
					+ "4. Dzest (DELETE)\n"
					+ "0. Atpakal\n"
					+ "Izvele: ");
			String c = scan.nextLine().trim();
			
			switch(c) {
			case "1": {
				selectOp.select(con, table);
			}
			
			case "2": insertOp.insert(con, table);
			
			case "3":  updateOp.update(con, table);
			
			case "4":  deleteOp.delete(con, table);
			
			case "0":  back = true;
			default: System.out.println();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			con = DatabaseConnection.getConnection();
			System.out.println("Izveidots savienojums ar datu bazi Sakila!");
			
			SelectOperation selectOp = new SelectOperation();
			ViewManager viewManager = new ViewManager(con, selectOp, scan);
			InsertOperation insertOp = new InsertOperation();
			UpdateOperation updateOp = new UpdateOperation();
			DeleteOperation deleteOp = new DeleteOperation();
			
			boolean running = true;
			
			while(running) {
				System.out.println(CYAN + "\n----- SAKILA DB -----\n" + RESET
						+ GREEN +  "1. Tabulas \n" 
						+ "2. Skati\n" + RESET
						+ RED + "0. Apturet\n" + RESET
						+ CYAN + "Izvele:" + RESET);
				
				String mainChoice = scan.nextLine().trim();
				
				switch(mainChoice) {
				case "1" ->{
					String table = chooseTable();
					if(!table.equals("exit")) {
						tableMenu(table, selectOp, insertOp, updateOp, deleteOp);
					}
				}
				
				case "2" -> viewManager.showViewsMenu();
				
				
				case "0" -> running = false;
				
				default -> System.out.println(RED + "Nepareiza izvele!" + RESET);
				
				}
			}
				con.close();
				System.out.println("Savienojums ar DB slegts.");
			
		}catch(SQLException e) {
			System.out.println("DB kluda: "+e.getMessage());
		}
}
}
