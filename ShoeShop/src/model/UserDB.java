package model;

public class UserDB
{
	public int id;
	public String username, password, usertype;

	public UserDB()
	{
		// TODO Auto-generated constructor stub
	}

	public UserDB(int id, String username, String password, String usertype)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
}
