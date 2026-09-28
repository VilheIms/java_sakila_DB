package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertOperation {
	static Scanner scan = new Scanner(System.in);
		public void insert(Connection con, String table) {
			try {
				switch(table) {
					case "city" -> insertCity(con);
					
					case "country" -> insertCountry(con);
					
					case "countrylanguage" -> insertCountryLanguage(con);
					
					default -> System.out.println("Neatbalstita tabula: "+table);
				}
			}catch(SQLException e) {
				System.out.println("Insert Kluda: "+e.getMessage());
			}
	}

		private void insertCountryLanguage(Connection con) throws SQLException{
			String IsOfficial;
			String CountryCode;
			do {
			System.out.println("Ievadi valsts kodu (3 simboli");
			CountryCode = scan.nextLine();
			}while(!CountryCode.matches("^\s*([A-Z]\s*){3}$"));
			
			System.out.println("Ievadi valsts valodu");
			String Language = scan.nextLine();
			do {
			System.out.println("Noradi, vai valoda ir oficiala (T/F)");
			IsOfficial = scan.nextLine();
			}while(!IsOfficial.equals("T") && !IsOfficial.equals("F"));
			System.out.println("Noradi runataju procentu (BEZ % ZIMES)");
			double percentage = scan.nextDouble();
			scan.nextLine();
			
			String sql = "INSERT INTO countrylanguage (CountryCode, Language, IsOfficial, percentage) VALUES (?, ?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, CountryCode);
				ps.setString(2, Language);
				ps.setString(3, IsOfficial);
				ps.setDouble(4, percentage);
				int rows = ps.executeUpdate();
				System.out.println("COUNTRY LANGUAGE tabula ir ievietotas: "+rows+" rindas");
		}
		}
		private void insertCountry(Connection con) throws SQLException{
			
			String Code;
			String Code2;
			String IndepYear;
			String SurfaceArea;
			String LifeExpectancy;
			String GNP;
			String GNPOld;
			String population;
			
			System.out.println("Ievadi pilsetas nosaukumu");
			String name = scan.nextLine();
			do {
				System.out.println("Noradi valsts kodu (3 simboli");
				Code = scan.nextLine();
				}while(!Code.matches("^\s*([A-Z]\s*){3}$"));
			System.out.println("Noradi kontinentu");
			String Continent = scan.nextLine();
			System.out.println("Noradi regionu");
			String Region = scan.nextLine();
			do {
			System.out.println("Noradi valsts izmeru");
			SurfaceArea = scan.nextLine();
			}while(!SurfaceArea.matches("^\\d{0,}.\\d{0,2}$"));
			do {
			System.out.println("Noradi neatkaribas datumu");
			IndepYear = scan.nextLine();
			}while(IndepYear.matches("^\\d{0,}$"));
			do {
			System.out.println("Noradi videju dzives gadu skaitu");
			LifeExpectancy = scan.nextLine();
			}while(!LifeExpectancy.matches("^\\d{0,2}.\\d{0,1}$"));
			do {
				System.out.println("Noradi iedzivotaju skaitu");
				population = scan.nextLine();
				}while(!population.matches("^\\d{0,}$"));
			do {
			System.out.println("Noradi GNP");
			 GNP = scan.nextLine();
			}while(!SurfaceArea.matches("^\\d{0,}.\\d{0,2}$"));
			do {
			System.out.println("Noradi veco GNP");
			 GNPOld = scan.nextLine();
			}while(!SurfaceArea.matches("^\\d{0,}.\\d{0,2}$"));
			scan.nextLine();
			System.out.println("Noradi vietejo vardu");
			String LocalName = scan.nextLine();
			System.out.println("Noradi valdibas formu");
			String GovernmentForm = scan.nextLine();
			System.out.println("Noradi valsts valdnieku");
			String HeadOfState = scan.nextLine();
			System.out.println("Noradi galvaspilsetas ID");
			String Capital = scan.nextLine();
			do {
			System.out.println("Noradi otro valsts kodu (2 burti)");
			Code2 = scan.nextLine();
		}while(!Code2.matches("^\s*([A-Z]\s*){2}$"));
			
			String sql = "INSERT INTO country (Name, Code, Continent, Region, SurfaceArea, IndepYear, LifeExpectancy, Population, GNP, GNPOld, LocalName, GovernmentForm, HeadOfState, Capital, Code2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, Code);
				ps.setString(3, Continent);
				ps.setString(4, Region);
				ps.setString(5, SurfaceArea);
				ps.setString(6, IndepYear);
				ps.setString(7, LifeExpectancy);
				ps.setString(8, population);
				ps.setString(9, GNP);
				ps.setString(10, GNPOld);
				ps.setString(11, LocalName);
				ps.setString(12, GovernmentForm);
				ps.setString(13, HeadOfState);
				ps.setString(14, Capital);
				ps.setString(15, Code2);
				int rows = ps.executeUpdate();
				System.out.println("COUNTRY tabula ir ievietotas: "+rows+" rindas");
		}
		}
		private void insertCity(Connection con) throws SQLException{
			String CountryCode;
			String population;
			System.out.println("Ievadi pilsetas nosaukumu");
			String name = scan.nextLine();
			do {
				System.out.println("Noradi valsts kodu (3 simboli");
				CountryCode = scan.nextLine();
				}while(!CountryCode.matches("^\s*([A-Z]\s*){3}$"));
			System.out.println("Noradi apgabalu");
			String district = scan.nextLine();
			do {
			System.out.println("Noradi iedzivotaju skaitu");
			population = scan.nextLine();
			}while(!population.matches("^\\d{0,}.\\d{0,2}$"));
			
			String sql = "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, name);
				ps.setString(2, CountryCode);
				ps.setString(3, district);
				ps.setString(4, population);
				int rows = ps.executeUpdate();
				System.out.println("CITY tabula ir ievietotas: "+rows+" rindas");
			}
		}
}
