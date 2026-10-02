package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UpdateOperation {
	static Scanner scan = new Scanner(System.in);
	
	public void update(Connection con, String table) {
		try {
			switch(table) {
			case "address" -> updateAddress(con);

			case "language" -> updateLanguage(con);
			
			case "category" -> updateCategory(con);
			
			case "inventory" -> updateInventory(con);
			
			default -> System.out.println("Neatbalstita tabula: "+table);
			}
		}catch(SQLException e) {
			System.out.println("update kluda: "+ e.getMessage());
		}
	}

	private void updateAddress(Connection con) throws SQLException{
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
		
		String sql = "UPDATE address SET address = ?, address2 = ?, district = ?, city_id = ?, postal_code = ?, phone = ?, location = ST_GeomFromText(?), last_update = ? WHERE address_id = ?";
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, address);
			ps.setString(2, address2);
			ps.setString(3, district);
			ps.setString(4, city_id);
			ps.setString(5, postal_code);
			ps.setString(6, phone);
			ps.setString(7, location);
			ps.setString(8, last_update);
			ps.setString(9, address_id);
			int rows = ps.executeUpdate();
			System.out.println("ADDRESS tabula ir atjaunotas: "+rows+" rindas");
		}
	}

		private void updateCategory(Connection con) throws SQLException{
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
			 
			String sql = "UPDATE category SET name = ?, last_update = ? WHERE category_id = ?";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, last_update);
				ps.setString(3, category_id);
				int rows = ps.executeUpdate();
				System.out.println("category tabula ir atjaunotas: "+rows+" rindas");
	
		}
		}
		private void updateLanguage(Connection con) throws SQLException{
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
			
			String sql = "UPDATE language SET name = ?, last_update = ? WHERE language_id = ?";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, last_update);
				ps.setString(3, language_id);
				int rows = ps.executeUpdate();
				System.out.println("language tabula ir atjaunotas: "+rows+" rindas");
	
		}
		}
		private void updateInventory(Connection con) throws SQLException{
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
			
			String sql = "UPDATE inventory SET film_id = ?, store_id = ?, last_update = ? WHERE inventory_id = ?";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, film_id);
				ps.setString(2, store_id);
				ps.setString(3, last_update);
				ps.setString(4, inventory_id);
				int rows = ps.executeUpdate();
				System.out.println("inventory tabula ir atjaunotas: "+rows+" rindas");
		}
		}
}
