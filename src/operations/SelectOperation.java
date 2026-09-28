package operations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class SelectOperation {
	
	static final String GREEN = "\u001B[32m";
	static final String RED = "\u001B[31m";
	static final String RESET = "\u001B[0m";
	
	public static Scanner scan = new Scanner(System.in);
	
	public void select(Connection con, String tableOrView) {
		
		System.out.println("Ievadi rindu limitu (0 ja negribat): ");
		int limit = scan.nextInt();
		
		
		String sql = "SELECT * FROM " + tableOrView + (limit > 0 ? (" LIMIT " + limit) : "");
		try(Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)){
				
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			int colWidth = 30;
			
			for(int i = 1; i <= colCount; i++) {
				
				
				
				System.out.printf("%-" + colWidth + "s", meta.getColumnName(i));
			}
			
			System.out.println();
			
			System.out.println("_".repeat(colCount * colWidth));
			
			int rowIndex = 1;
			
			while(rs.next()) {
				
				String rowColor = (rowIndex % 2 != 0) ? GREEN : RED; 
				System.out.print(rowColor);
				
				for(int i = 1; i <= colCount; i++) {
					String value = rs.getString(i);
					
					if(value == null)
						value = "NULL";
					
					else if(value.length() > colWidth - 5) {
						value = value.substring(0, colWidth - 5) + "...";
					}
					String formattedValue = String.format("%-" + colWidth + "s", value);
					System.out.print(formattedValue);
					}
				
				System.out.print(RESET);
				System.out.println();
				
				rowIndex++;
			
			}
			
		}catch(SQLException e) {
			System.out.println("SELECT Kluda: "+e.getMessage());
		}
	}
}
