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

		private void insertAddress(Connection con) throws SQLException {
		    String address;
		    String address2;
		    String district;
		    int cityId;
		    String postalCode;
		    String phone;
		    String location;
		    LocalDateTime now = LocalDateTime.now();
		    String lastUpdate;
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    
		    System.out.println("Ievadi adresi:");
		    address = scan.nextLine();
		    
		    System.out.println("Ievadi 2. adresi (var būt tukšs):");
		    address2 = scan.nextLine();
		    if (address2.trim().isEmpty()) {
		        address2 = null;
		    }
		    
		    System.out.println("Ievadi rajonu:");
		    district = scan.nextLine();
		    
		    String cityIdInput;
		    do {
		        System.out.println("Ievadi pilsetas ID (tikai cipari):");
		        cityIdInput = scan.nextLine();
		    } while (!cityIdInput.matches("^\\d+$"));
		    cityId = Integer.parseInt(cityIdInput);
		    
		    do {
		        System.out.println("Ievadi pasta kodu (tikai cipari, 3-18):");
		        postalCode = scan.nextLine();
		    } while (!postalCode.matches("^\\d{3,18}$"));
		    
		    do {
		        System.out.println("Ievadi telefona numuru (4-15 cipari):");
		        phone = scan.nextLine();
		    } while (!phone.matches("^\\d{4,15}$"));
		    
		    System.out.println("Ievadi lokaciju formātā 'POINT(X Y)' vai atstāj tukšu:");
		    location = scan.nextLine();
		    if (location.trim().isEmpty()) {
		        location = "POINT(0 0)";
		    }
		    
		    lastUpdate = now.format(formatter);
		    
		    String sql = "INSERT INTO address (address, address2, district, city_id, postal_code, phone, location, last_update) VALUES (?, ?, ?, ?, ?, ?, ST_GeomFromText(?), ?)";
		    try (PreparedStatement ps = con.prepareStatement(sql)) {
		        ps.setString(1, address);
		        ps.setString(2, address2);
		        ps.setString(3, district);
		        ps.setInt(4, cityId);
		        ps.setString(5, postalCode);
		        ps.setString(6, phone);
		        ps.setString(7, location);
		        ps.setString(8, lastUpdate);
		        
		        int rows = ps.executeUpdate();
		        System.out.println("address tabula ir ievietotas: " + rows + " rindas");
		    }
		}

		
		private void insertCategory(Connection con) throws SQLException{
			String name;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			
			System.out.println("Ievadi nosaukumu kategorijai");
			name = scan.nextLine();
			 
			last_update = now.format(formatter);
			 
			String sql = "INSERT INTO category ( name, last_update) VALUES (?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, last_update);
				int rows = ps.executeUpdate();
				System.out.println("category tabula ir ievietotas: "+rows+" rindas");
		}
	}
		private void insertLanguage(Connection con) throws SQLException{
			String name;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			System.out.println("Ievadi valodas nosaukumu"); 
			name = scan.nextLine();
			
			last_update = now.format(formatter);
			 
			String sql = "INSERT INTO language ( name, last_update) VALUES (?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, last_update);
				int rows = ps.executeUpdate();
				System.out.println("language tabula ir ievietotas: "+rows+" rindas");
		}
		}
		private void insertInventory(Connection con) throws SQLException{
			String film_id;
			String store_id;
			LocalDateTime now = LocalDateTime.now();
			String last_update;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			do {
				System.out.println("Ievadi filmas ID");
				film_id = scan.nextLine();
				}while(!film_id.matches("^\\d{1,}$"));
			do {
				System.out.println("Ievadi veikala ID (pagaidam 1 vai 2)");
				store_id = scan.nextLine();
				}while(!store_id.matches("^\\d{1,}$"));
			
			last_update = now.format(formatter);
			
			String sql = "INSERT INTO inventory (film_id, store_id, last_update) VALUES (?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, film_id);
				ps.setString(2, store_id);
				ps.setString(3, last_update);
				int rows = ps.executeUpdate();
				System.out.println("inventory tabula ir ievietotas: "+rows+" rindas");
		}
		}
}

