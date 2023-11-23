package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.GlobalData;

public class UserManager
{
	public static ArrayList<UserDB> getAllUsers()
	{
		ArrayList<UserDB> list = new ArrayList<UserDB>();
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM users";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String usertype = rs.getString("usertype");
				UserDB data = new UserDB(id, username, password, usertype);
				list.add(data);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return list;
	}

	public static boolean checkLogin(String username, String password)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM users WHERE username = ? AND password = ? ";

			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, username);
			st.setString(2, password);

			ResultSet rs = st.executeQuery(); // when use PreparedStatement no need to put agrument query in ()

			while (rs.next())
			{
				GlobalData.CurrentUser_userID = rs.getInt(1); // column ที่ 1
				GlobalData.CurrentUser_username = rs.getString(2); // column ที่ 2
				GlobalData.CurrentUser_usertype = rs.getString(4); // column ที่ 4
				return true; // Correct Password
			}

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return false; // Wrong Password
	}

	public static void saveNewUser(UserDB data)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "INSERT INTO users VALUES ('" + data.id + "','" + data.username + "','" + data.password + "','" + data.usertype + "' )";

			Statement st = conn.createStatement();

			st.executeUpdate(query);

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void editUser(UserDB data)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "UPDATE users SET username = '" + data.username + "', password = '" + data.password + "', usertype = '" + data.usertype + "' WHERE id = '" + data.id + "' ";

			Statement st = conn.createStatement();

			st.executeUpdate(query);

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void deleteUser(UserDB data)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "DELETE FROM users WHERE id = '" + data.id + "' ";

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
