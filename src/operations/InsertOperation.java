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
					
					/*case "film" -> insertFilm(con);
					
					case "category" -> insertCategory(con);
					
					case "staff" -> insertStaff(con);*/
					
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
}

