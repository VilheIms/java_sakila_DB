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
			case "city" -> deleteCity(con);
			
			case "country" -> deleteCountry(con);
			
			case "countrylanguage" -> deleteCountryLanguage(con);
			
			default -> System.out.println("Neatbalstita tabula: "+table);
			}
		}catch(SQLException e) {
			System.out.println("DELETE Kluda: "+e.getMessage());
		}
	}


	private void deleteCity(Connection con) throws SQLException{
		System.out.println("Noradi pilsetas ID, kuru velies dzest: ");
		int id = scan.nextInt();
		scan.nextLine();
		
		String sql = "DELETE FROM city WHERE ID = ? ";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			System.out.println("CITY tabula dzesti "+rows+ " ieraksti");
		}
	}
	
	private void deleteCountry(Connection con) throws SQLException{
		System.out.println("Noradi valsts kodu: ");
		String code = scan.nextLine();
		
		String deleteCitiesSql = "DELETE FROM city WHERE CountryCode = ?";
	    try (PreparedStatement ps = con.prepareStatement(deleteCitiesSql)) {
	        ps.setString(1, code);
	        int cityRows = ps.executeUpdate();
	        System.out.println("Dzesti " + cityRows + " saistitie ieraksti no CITY tabulas.");
	    }
	    
	    String deleteCountryLanguagesql = "DELETE FROM countrylanguage WHERE CountryCode = ?";
		    try (PreparedStatement ps = con.prepareStatement(deleteCountryLanguagesql)) {
		        ps.setString(1, code);
		        int countryLanguage = ps.executeUpdate();
		        System.out.println("COUNTRY LANGUAGE tabula dzesti " + countryLanguage + " ieraksti.");
		}
		
	    String deleteCountrySql = "DELETE FROM country WHERE Code = ?";
	    try (PreparedStatement ps = con.prepareStatement(deleteCountrySql)) {
	        ps.setString(1, code);
	        int countryRows = ps.executeUpdate();
	        System.out.println("COUNTRY tabula dzesti " + countryRows + " ieraksti.");
	    }
	        
	}
	
	private void deleteCountryLanguage(Connection con) throws SQLException {
		System.out.println("Noradi valsts kodu valodai, kuru dzesisi: ");
		String code = scan.nextLine();
		
		String sql = "DELETE FROM countrylanguage WHERE CountryCode = ? ";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, code);
			int rows = ps.executeUpdate();
			System.out.println("COUNTRY LANGUAGE tabula dzesti "+rows+ " ieraksti");
		}
	}
}
