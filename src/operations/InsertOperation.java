package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsertOperation {
	static Scanner scan = new Scanner(System.in);
		public void insert(Connection con, String table) {
			try {
				switch(table) {
					case "address" -> insertAddress(con);
					
					case "language" -> insertLanguage(con);
					
					case "category" -> insertCategory(con);
					
					case "inventory" -> insertInventory(con);
					
					default -> System.out.println("Neatbalstita tabula: "+table);
				}
			}catch(SQLException e) {
				System.out.println("Insert Kluda: "+e.getMessage());
			}
	}

		private void insertAddress(Connection con) throws SQLException{
			String address_id;
			String address;
			String address2;
			String district;
			String city_id;
			String postal_code;
			String phone;
			String location;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			do {
			System.out.println("Ievadi adresses ID");
			address_id = scan.nextLine();
			}while(!address_id.matches("^\\d{0,}$"));
			
			System.out.println("Ievadi adresi");
			 address = scan.nextLine();
			
			System.out.println("Ievadi 2. adresi (var but tukss)");
			address2 = scan.nextLine();
			
			System.out.println("Ievadi rajonu");
			district = scan.nextLine();
			do {
			System.out.println("Ievadi pilsetas ID");
			city_id = scan.nextLine();
			}while(!city_id.matches("^\\d{0,}$"));
			do {
			System.out.println("Ievadi pasta kodu (tikai cipari)");
			postal_code = scan.nextLine();
			}while(!postal_code.matches("^\\d{3,18}$"));
			do {
			System.out.println("Ievadi telefona numuru");
			phone = scan.nextLine();
			}while(!phone.matches("^\\d{4,15}$"));
			
			System.out.println("Ievadi lokaciju (Koordinatas)");
			location = scan.nextLine();
			if (location.trim().isEmpty()) {
			    location = "POINT(0 0)";
			}
			
			last_update = now.format(formatter);
			
			String sql = "INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, location, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ST_GeomFromText(?), ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, address_id);
				ps.setString(2, address);
				ps.setString(3, address2);
				ps.setString(4, district);
				ps.setString(5, city_id);
				ps.setString(6, postal_code);
				ps.setString(7, phone);
				ps.setString(8, location);
				ps.setString(9, last_update);
				int rows = ps.executeUpdate();
				System.out.println("address tabula ir ievietotas: "+rows+" rindas");
		}
	}
		
		private void insertCategory(Connection con) throws SQLException{
			String category_id;
			String name;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			do {
				System.out.println("Ievadi kategorijas ID");
				category_id = scan.nextLine();
				}while(!category_id.matches("^\\d{1,}$"));
			
			System.out.println("Ievadi nosaukumu kategorijai");
			name = scan.nextLine();
			 
			last_update = now.format(formatter);
			 
			String sql = "INSERT INTO category (category_id, name, last_update) VALUES (?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, category_id);
				ps.setString(2, name);
				ps.setString(3, last_update);
				int rows = ps.executeUpdate();
				System.out.println("category tabula ir ievietotas: "+rows+" rindas");
		}
	}
		private void insertLanguage(Connection con) throws SQLException{
			String language_id;
			String name;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			do {
				System.out.println("Ievadi valodas ID");
				language_id = scan.nextLine();
				}while(!language_id.matches("^\\d{1,}$"));
			System.out.println("Ievadi valodas nosaukumu"); 
			name = scan.nextLine();
			
			last_update = now.format(formatter);
			 
			String sql = "INSERT INTO language (language_id, name, last_update) VALUES (?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, language_id);
				ps.setString(2, name);
				ps.setString(3, last_update);
				int rows = ps.executeUpdate();
				System.out.println("language tabula ir ievietotas: "+rows+" rindas");
		}
		}
		private void insertInventory(Connection con) throws SQLException{
			String inventory_id;
			String film_id;
			String store_id;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			do {
				System.out.println("Ievadi inventara ID");
				inventory_id = scan.nextLine();
				}while(!inventory_id.matches("^\\d{1,}$"));
			do {
				System.out.println("Ievadi filmas ID");
				film_id = scan.nextLine();
				}while(!film_id.matches("^\\d{1,}$"));
			do {
				System.out.println("Ievadi veikala ID (pagaidam 1 vai 2)");
				store_id = scan.nextLine();
				}while(!store_id.matches("^\\d{1,}$"));
			
			last_update = now.format(formatter);
			
			String sql = "INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES (?, ?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, inventory_id);
				ps.setString(2, film_id);
				ps.setString(3, store_id);
				ps.setString(4, last_update);
				int rows = ps.executeUpdate();
				System.out.println("inventory tabula ir ievietotas: "+rows+" rindas");
		}
		}
}

