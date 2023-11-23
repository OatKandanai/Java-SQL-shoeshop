package model;

import java.awt.image.BufferedImage;

public class ProductDB
{
	public int product_id;
	public String product_name, product_description;
	public double price_per_unit;
	public BufferedImage product_image;

	public ProductDB()
	{

	}

	public ProductDB(int product_id, String product_name, double price_per_unit, String product_description, BufferedImage product_image)
	{
		this.product_id = product_id;
		this.product_name = product_name;
		this.price_per_unit = price_per_unit;
		this.product_description = product_description;
		this.product_image = product_image;
	}

}