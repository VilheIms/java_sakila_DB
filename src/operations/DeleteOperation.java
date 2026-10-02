package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteOperation {
	static Scanner scan = new Scanner(System.in);
	
	public void delete(Connection con, String table) {
		try {
			switch(table) {
			case "address" -> deleteAddress(con);
			
			case "language" -> deleteLanguage(con);
			
			case "category" -> deleteCategory(con);
					
			//case "inventory" -> deleteInventory(con);
			
			default -> System.out.println("Neatbalstita tabula: "+table);
			}
		}catch(SQLException e) {
			System.out.println("DELETE Kluda: "+e.getMessage());
		}
	}
		
		private void deleteAddress(Connection con) throws SQLException {
		    String address_id;
		    do {
		        System.out.println("Ievadi adresses ID");
		        address_id = scan.nextLine();
		    } while(!address_id.matches("^\\d{1,}$"));

		    String deletePaymentSql = 
		        "DELETE p FROM payment p " +
		        "INNER JOIN customer c ON p.customer_id = c.customer_id " +
		        "WHERE c.address_id = ?";
		    try (PreparedStatement ps = con.prepareStatement(deletePaymentSql)) {
		        ps.setString(1, address_id);
		        int paymentRows = ps.executeUpdate();
		        System.out.println("Dzesti " + paymentRows + " saistitie maksajumi no payment tabulas.");
		    }

		    String deleteRentalSql = 
		        "DELETE r FROM rental r " +
		        "INNER JOIN customer c ON r.customer_id = c.customer_id " +
		        "WHERE c.address_id = ?";
		    try (PreparedStatement ps = con.prepareStatement(deleteRentalSql)) {
		        ps.setString(1, address_id);
		        int rentalRows = ps.executeUpdate();
		        System.out.println("Dzesti " + rentalRows + " saistitie nomas ieraksti no rental tabulas.");
		    }

		    String deleteCustomerSql = "DELETE FROM customer WHERE address_id = ?";
		    try (PreparedStatement ps = con.prepareStatement(deleteCustomerSql)) {
		        ps.setString(1, address_id);
		        int customerRows = ps.executeUpdate();
		        System.out.println("CUSTOMER tabula dzesti " + customerRows + " ieraksti.");
		    }
		    String deleteAddressSql = "DELETE FROM address WHERE address_id = ?";
		    try (PreparedStatement ps = con.prepareStatement(deleteAddressSql)) {
		        ps.setString(1, address_id);
		        int addressRows = ps.executeUpdate();
		        System.out.println("Dzesti " + addressRows + " saistitie ieraksti no address tabulas.");
		   }
		}
		   private void deleteCategory(Connection con) throws SQLException  {
			   String category_id;
			   
			   do {
					System.out.println("Ievadi kategorijas ID");
					category_id = scan.nextLine();
					}while(!category_id.matches("^\\d{1,}$"));
			   
			   String deleteFilmCategorySql = "DELETE FROM film_category WHERE category_id = ?";
			    try (PreparedStatement ps = con.prepareStatement(deleteFilmCategorySql)) {
			        ps.setString(1, category_id);
			        int addressRows = ps.executeUpdate();
			        System.out.println("Dzesti " + addressRows + " saistitie ieraksti no film category tabulas.");
			    }
			   
			   String deleteCategorySql = "DELETE FROM category WHERE category_id = ?";
			    try (PreparedStatement ps = con.prepareStatement(deleteCategorySql)) {
			        ps.setString(1, category_id);
			        int addressRows = ps.executeUpdate();
			        System.out.println("Dzesti " + addressRows + " saistitie ieraksti no category tabulas.");
			    }
	}
		   private void deleteLanguage(Connection con) throws SQLException{
			   String language_id;
			   do {
				   System.out.println("Ievadi valodas ID");
				   language_id = scan.nextLine();
				}while(!language_id.matches("^\\d{1,}$"));
			   
			   String deleteLanguageSql = "DELETE FROM language WHERE language_id = ?";
			    try (PreparedStatement ps = con.prepareStatement(deleteLanguageSql)) {
			        ps.setString(1, language_id);
			        int addressRows = ps.executeUpdate();
			        System.out.println("Dzesti " + addressRows + " saistitie ieraksti no language tabulas.");
			    }
		   }
}
