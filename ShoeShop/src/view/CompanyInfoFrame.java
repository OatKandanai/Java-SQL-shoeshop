package view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.CompanyInfoDB;
import model.CompanyInfoManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompanyInfoFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_company_name;
	private JTextField textField_address;
	private JTextField textField_phone;
	private JTextField textField_email;
	CompanyInfoDB data;
	Font thai = new Font("Tahoma", Font.PLAIN, 12);

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
					CompanyInfoFrame frame = new CompanyInfoFrame();
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
	public CompanyInfoFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Company Name");
		lblNewLabel.setBounds(26, 34, 106, 13);
		contentPane.add(lblNewLabel);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(26, 75, 86, 13);
		contentPane.add(lblAddress);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(26, 123, 86, 13);
		contentPane.add(lblPhone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(26, 170, 86, 13);
		contentPane.add(lblEmail);

		textField_company_name = new JTextField();
		textField_company_name.setBounds(154, 31, 150, 19);
		contentPane.add(textField_company_name);
		textField_company_name.setColumns(10);
		textField_company_name.setFont(thai);

		textField_address = new JTextField();
		textField_address.setColumns(10);
		textField_address.setBounds(154, 72, 599, 19);
		contentPane.add(textField_address);
		textField_address.setFont(thai);

		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(154, 120, 150, 19);
		contentPane.add(textField_phone);

		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(154, 167, 150, 19);
		contentPane.add(textField_email);

		JButton btn_save = new JButton("Save");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				data = new CompanyInfoDB();
				data.id = 1;
				data.company_name = textField_company_name.getText().trim();
				data.address = textField_address.getText().trim();
				data.phone = textField_phone.getText().trim();
				data.email = textField_email.getText().trim();
				CompanyInfoManager.editCompanyInfo(data);
				load();
				JOptionPane.showMessageDialog(CompanyInfoFrame.this, "Done");
			}
		});
		btn_save.setBounds(154, 226, 85, 21);
		contentPane.add(btn_save);

		load();
	}

	public void load()
	{
		data = CompanyInfoManager.getCompanyInfo();
		textField_company_name.setText(data.company_name);
		textField_address.setText(data.address);
		textField_email.setText(data.email);
		textField_phone.setText(data.phone);
	}
}
