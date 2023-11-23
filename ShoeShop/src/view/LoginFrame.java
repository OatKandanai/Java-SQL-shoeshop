package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.UserManager;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_username;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame()
	{
		setTitle("Shoe Shop Login Page");
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null); // SET JFrame to always appear at the center of screen

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_username = new JTextField();
		textField_username.setBounds(187, 53, 96, 19);
		contentPane.add(textField_username);
		textField_username.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) // if "ENTER KEY" is pressed and released
 				{
					check();
				}
			}
		});
		passwordField.setBounds(187, 101, 96, 19);
		contentPane.add(passwordField);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(115, 56, 62, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(115, 104, 62, 13);
		contentPane.add(lblNewLabel_1);

		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				check();
			}
		});
		btn_login.setBounds(187, 138, 85, 21);
		contentPane.add(btn_login);

		JButton btn_exit = new JButton("Exit");
		btn_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0); // exit the program
			}
		});
		btn_exit.setBounds(187, 184, 85, 21);
		contentPane.add(btn_exit);
	}
	
	public void check()
	{
		boolean login = UserManager.checkLogin(textField_username.getText(), new String(passwordField.getPassword())); // getPassword return type is char Array
		if (login == true)
		{
			MainFrame menu = new MainFrame();
			menu.setVisible(true);
			LoginFrame.this.setVisible(false); // hide this LoginFrame
		} else
		{
			JOptionPane.showMessageDialog(LoginFrame.this, "Incorrect Username or Password");
		}
	}
}
