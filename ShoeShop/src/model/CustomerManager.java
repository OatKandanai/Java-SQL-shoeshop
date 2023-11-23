package model;

import java.sql.*;
import java.util.ArrayList;

import common.GlobalData;

public class CustomerManager
{
	public static ArrayList<CustomerDB> getAllCustomer() // get customers info from SQL table then return list ของ
															// customerDB
	{
		ArrayList<CustomerDB> list = new ArrayList<CustomerDB>();
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM customers";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			// executeQuery(query) used when you want to retrieve data from the database
			// executeQuery returns a ResultSet object, which contains the results of the query.
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("id");
				String firstName = rs.getString("name");
				String lastName = rs.getString("surname");
				String phone = rs.getString("phone");
				CustomerDB cc = new CustomerDB(id, firstName, lastName, phone);
				list.add(cc);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return list;
	}

	public static void saveNewCustomer(CustomerDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "INSERT INTO customers VALUES (0,'" + x.name + "','" + x.surname + "','" + x.phone + "' )";
			// USE INSERT COMMAND TO CREATE NEW id AND INFO IN SQL TABLE
			Statement st = conn.createStatement();

			// executeUpdate(query) is used to execute SQL statements that modify the database, such as INSERT, UPDATE, or DELETE statements.
			// It is not used for retrieving data.
			st.executeUpdate(query);

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void editCustomer(CustomerDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "UPDATE customers SET name = '" + x.name + "', surname = '" + x.surname + "', phone = '" + x.phone + "' WHERE id = '" + x.id + "'";

			Statement st = conn.createStatement();

			st.executeUpdate(query); // UPDATE TABLE

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void deleteCustomer(CustomerDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "DELETE FROM customers WHERE id = '" + x.id + "'";

			Statement st = conn.createStatement();

			st.executeUpdate(query); // UPDATE TABLE

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static ArrayList<CustomerDB> searchCustomer(String text)
	{
		ArrayList<CustomerDB> list = new ArrayList<CustomerDB>();
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM customers WHERE name LIKE '" + text + "' OR surname LIKE '" + text + "' OR phone LIKE '" + text + "' ";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				int id = rs.getInt("id");
				String firstName = rs.getString("name");
				String lastName = rs.getString("surname");
				String phone = rs.getString("phone");
				CustomerDB cc = new CustomerDB(id, firstName, lastName, phone);
				list.add(cc);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return list;
	}

	public static void main(String[] args)
	{
		ArrayList<CustomerDB> ll = getAllCustomer();
		System.out.println(ll.size());
	}
}
