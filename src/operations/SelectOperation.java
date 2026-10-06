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
		String limit;
		do {
			System.out.println("Ievadi rindu limitu (0 ja negribat): ");
			limit = scan.nextLine();
		} while(!limit.matches("^\\d{1,}$"));
		
		String sql = "SELECT * FROM " + tableOrView + (Integer.parseInt(limit) > 0 ? (" LIMIT " + limit) : "");
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
			
			while(rs.next()) {
				for(int i = 1; i <= colCount; i++) {
					String colColor = (i % 2 != 0) ? GREEN : RED; 
					System.out.print(colColor);
					
					String value;
					String columnTypeName = meta.getColumnTypeName(i);
					
					if ("GEOMETRY".equalsIgnoreCase(columnTypeName) || "POINT".equalsIgnoreCase(columnTypeName)) {
						byte[] geomBytes = rs.getBytes(i);
						value = (geomBytes == null) ? "NULL" : parseSpatialBytes(geomBytes);
					} else {
						value = rs.getString(i);
					}
					
					if(value == null) {
						value = "NULL";
					} else if(value.length() > colWidth - 5) {
						value = value.substring(0, colWidth - 5) + "...";
					}
					
					String formattedValue = String.format("%-" + colWidth + "s", value);
					System.out.print(formattedValue);
					
					System.out.print(RESET);
				}
				System.out.println();
			}
			
		} catch(SQLException e) {
			System.out.println("SELECT Kluda: " + e.getMessage());
		}
	}

	private String parseSpatialBytes(byte[] bytes) {
		if (bytes.length < 25) return "[GEOMETRY]";
		try {
			boolean isLittleEndian = (bytes[4] == 1);
			long xBits = 0, yBits = 0;
			
			if (isLittleEndian) {
				for (int i = 0; i < 8; i++) {
					xBits |= ((long) (bytes[9 + i] & 0xFF)) << (8 * i);
					yBits |= ((long) (bytes[17 + i] & 0xFF)) << (8 * i);
				}
			} else {
				for (int i = 0; i < 8; i++) {
					xBits = (xBits << 8) | (bytes[9 + i] & 0xFF);
					yBits = (yBits << 8) | (bytes[17 + i] & 0xFF);
				}
			}
			double x = Double.longBitsToDouble(xBits);
			double y = Double.longBitsToDouble(yBits);
			return String.format("POINT(%.2f %.2f)", x, y);
		} catch (Exception e) {
			return "[GEOMETRY]";
		}
	}
}
