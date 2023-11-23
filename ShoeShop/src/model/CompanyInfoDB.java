package model;

public class CompanyInfoDB
{
	public int id;
	public String company_name;
	public String address;
	public String phone;
	public String email;

	public CompanyInfoDB()
	{
		// TODO Auto-generated constructor stub
	}

	public CompanyInfoDB(int id, String company_name, String address, String phone, String email)
	{
		this.id = id;
		this.company_name = company_name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
}
