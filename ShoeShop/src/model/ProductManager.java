package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.mysql.cj.protocol.a.ByteArrayValueEncoder;

import common.GlobalData;

public class ProductManager
{

	public static ArrayList<ProductDB> getAllProduct()
	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM products";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("product_id");
				String productName = rs.getString("product_name");
				double price = rs.getDouble("price_per_unit");
				String description = rs.getString("product_description");
				byte[] img_byte = rs.getBytes("product_image"); // store image as byte array
				BufferedImage buff_img = null;
				if (img_byte == null || img_byte.length == 0) // ถ้าไม่มีรูปใส่เข้ามา หรือ file ที่ดึงมาไม่ใช่ไฟล์รูปภาพ
				{

				} else
				{
					ByteArrayInputStream in = new ByteArrayInputStream(img_byte);
					// convert byte array into input stream
					buff_img = ImageIO.read(in);
					// read then return as BufferedImage
					in.close();
				}
				ProductDB pp = new ProductDB(id, productName, price, description, buff_img);
				list.add(pp);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return list;
	}

	public static void saveNewProduct(ProductDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "INSERT INTO products VALUES (?,?,?,?,?)";
			// USE INSERT COMMAND TO CREATE NEW id AND INFO IN SQL TABLE
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, 0);
			st.setString(2, x.product_name);
			st.setDouble(3, x.price_per_unit);
			st.setString(4, x.product_description);

			if (x.product_image != null)
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ImageIO.write(x.product_image, "png", out);
				byte[] buffer = out.toByteArray();
				st.setBytes(5, buffer);
				out.close();
			} else
			{
				byte[] buffer = new byte[0]; // ถ้าไม่มีรูปจะ new byte = 0 แล้วเอาไปใส่แทน
				st.setBytes(5, buffer);
			}

			st.executeUpdate(); // UPDATE TABLE

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void editProduct(ProductDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "UPDATE products SET product_name = ? , price_per_unit = ? , product_description = ?, product_image = ? WHERE product_id = ?";
			// UPDATE DATA INTO TABLE
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(5, x.product_id);
			st.setString(1, x.product_name);
			st.setDouble(2, x.price_per_unit);
			st.setString(3, x.product_description);
			if (x.product_image != null)
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ImageIO.write(x.product_image, "png", out);
				byte[] buffer = out.toByteArray();
				st.setBytes(4, buffer);
				out.close();
			} else
			{
				byte[] buffer = new byte[0]; // ถ้าไม่มีรูปจะ new byte = 0 แล้วเอาไปใส่แทน
				st.setBytes(4, buffer);
			}

			st.executeUpdate(); // UPDATE TABLE

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static void deleteProduct(ProductDB x)
	{
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "DELETE FROM products WHERE product_id = ?";
			// DELETE SELECTED TABLE
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, x.product_id);

			st.executeUpdate(); // UPDATE TABLE

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public static ArrayList<ProductDB> searchProduct(String text)
	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();
		try
		{
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM products WHERE product_name LIKE '" + text + "' OR product_description = '" + text + "' ";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				int id = rs.getInt("product_id");
				String productName = rs.getString("product_name");
				double price = rs.getDouble("price_per_unit");
				String description = rs.getString("product_description");
				byte[] img_byte = rs.getBytes("product_image"); // store image as byte array
				BufferedImage buff_img = null;
				if (img_byte == null || img_byte.length == 0) // ถ้าไม่มีรูปใส่เข้ามา หรือ file ที่ดึงมาไม่ใช่ไฟล์รูปภาพ
				{

				} else
				{
					ByteArrayInputStream in = new ByteArrayInputStream(img_byte);
					// convert byte array into input stream
					buff_img = ImageIO.read(in);
					// read then return as BufferedImage
					in.close();
				}
				ProductDB pp = new ProductDB(id, productName, price, description, buff_img);
				list.add(pp);
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
	}

}
