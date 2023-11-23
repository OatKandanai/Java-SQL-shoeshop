package model;

public class CustomerDB
{
	public int id;
	public String name, surname, phone;

	public CustomerDB()
	{

	}

	public CustomerDB(int id, String name, String surname, String phone)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
	}
}
