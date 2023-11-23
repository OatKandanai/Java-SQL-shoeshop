package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.GlobalData;

public class InvoiceManager
{
	public static void saveInvoice(CustomerDB data, ArrayList<InvoiceDetail> detail)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			// First SQL command
			// insert data into invoice table
			String query = "INSERT INTO invoice VALUES (0, NOW() ,'" + data.id + "', 'NORMAL' )";
			Statement st = conn.createStatement();
			st.executeUpdate(query);

			// Second SQL command
			// get max id
			query = "SELECT MAX(invoice_id) FROM invoice"; // select max id possible from "invoice" table
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int max_invoice_id = 0;
			while (rs.next())
			{
				max_invoice_id = rs.getInt(1);
			}

			// Third SQL command
			// insert data of products INTO invoice_detail table
			for (int i = 0; i < detail.size(); i++) // มีหลาย product
			{
				query = "INSERT INTO invoice_detail VALUES (0,'" + max_invoice_id + "', '" + detail.get(i).product.product_id + "', '" + detail.get(i).quantity + "' )"; 
				// select max id possible from "invoice" table
				st = conn.createStatement();
				st.executeUpdate(query);
			}

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}
