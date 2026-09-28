package operations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewManager {
	private final Connection con;
	private final SelectOperation selectOp;
	private final Scanner scan;
	
	public ViewManager(Connection con, SelectOperation selectOp, Scanner scan) {
		this.con = con;
		this.selectOp = selectOp;
		this.scan = scan;
	}
	public void showViewsMenu() {
		boolean back = false;
		while(!back) {
			List<String> views = getDatabaseViews();
			
			if(views.isEmpty()) {
				System.out.println("Datu baze nav atrasts neviens skats!");
				return;
			}
			System.out.println("\n---SKATI---");
			for(int i = 0; i<views.size(); i++) {
				System.out.println((i+1) + ". "+views.get(i));
			}
			System.out.println("0. Atpakal\n"+"Izvele: ");
			String choice = scan.nextLine().trim();
			
			if(choice.equals("0")) {
				back = true;
			} else {
				try {
					int index = Integer.parseInt(choice) - 1;
					if(index >= 0 && index < views.size()) {
						selectOp.select(con,  views.get(index));
						
					}else {
						System.out.println("Nepareizi izvelets skats!");
					}
				} catch(NumberFormatException e) {
					System.out.println("Nepareiza izvele!");
				}
			}
			
		}
	}
		private List<String> getDatabaseViews(){
			List<String> views = new ArrayList<>();
		try {
			DatabaseMetaData metaData = con.getMetaData();
			try(ResultSet rs = metaData.getTables(con.getCatalog(), null, "%", new String[] {"VIEW"})){
				while(rs.next()) {
					views.add(rs.getString("TABLE_NAME"));
				}
			}
			
		}catch(SQLException e) {
			System.out.println("Kluda iegustot skatu sarakstu: "+e.getMessage());
		}
		return views;
	}
	
}
