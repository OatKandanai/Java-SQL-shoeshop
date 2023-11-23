package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import common.GlobalData;

public class CompanyInfoManager
{
	public static CompanyInfoDB getCompanyInfo() // ไม่ต้อง return เป็น list เพราะมีบริษัทเดียว
	{
		CompanyInfoDB data = new CompanyInfoDB();
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM company_info";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				int id = rs.getInt("id");
				String company_name = rs.getString("company_name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				data = new CompanyInfoDB(id, company_name, address, phone, email);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return data;
	}

	public static void editCompanyInfo(CompanyInfoDB data)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "UPDATE company_info set company_name = '" + data.company_name + "', address = '" + data.address + "', phone = '" + data.phone + "' , email = '" + data.email + "' WHERE id = '" + data.id + "'  ";

			Statement st = conn.createStatement();

			st.executeUpdate(query);

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}
