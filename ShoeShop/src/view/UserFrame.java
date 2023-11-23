package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.UserDB;
import model.UserManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class UserFrame extends JFrame
{

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_id;
	private JTextField textField_username;
	private JTextField textField_password;
	ArrayList<UserDB> list;
	private JComboBox comboBox;

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
					UserFrame frame = new UserFrame();
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
	public UserFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 453, 511);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1)
					return;
				String id = table.getValueAt(selectedRow, 0).toString();
				String username = table.getValueAt(selectedRow, 1).toString();
				String password = table.getValueAt(selectedRow, 2).toString();
				String usertype = list.get(selectedRow).usertype;

				textField_id.setText(id);
				textField_username.setText(username);
				textField_password.setText(password);
				if (usertype.equals("admin"))
				{
					comboBox.setSelectedIndex(0);
				}
				if (usertype.equals("user"))
				{
					comboBox.setSelectedIndex(1);
				}
			}
		});
		scrollPane.setViewportView(table);

		textField_id = new JTextField();
		textField_id.setBackground(Color.GRAY);
		textField_id.setEditable(false);
		textField_id.setBounds(575, 66, 96, 19);
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		textField_username = new JTextField();
		textField_username.setColumns(10);
		textField_username.setBounds(575, 107, 96, 19);
		contentPane.add(textField_username);

		textField_password = new JTextField();
		textField_password.setColumns(10);
		textField_password.setBounds(575, 153, 96, 19);
		contentPane.add(textField_password);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(494, 69, 45, 13);
		contentPane.add(lblNewLabel);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(494, 110, 56, 13);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(494, 156, 56, 13);
		contentPane.add(lblPassword);

		JLabel lblUsertype = new JLabel("User-type");
		lblUsertype.setBounds(494, 197, 56, 13);
		contentPane.add(lblUsertype);

		JButton btn_save = new JButton("Save New");
		btn_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UserDB data = new UserDB();
				data.id = 0;
				data.username = textField_username.getText().trim();
				data.password = textField_password.getText().trim();
				int index = comboBox.getSelectedIndex();
				String usertype = comboBox.getItemAt(index).toString();
				data.usertype = usertype;
				UserManager.saveNewUser(data);

				load();
				JOptionPane.showMessageDialog(UserFrame.this, "Done");

				textField_id.setText("");
				textField_username.setText("");
				textField_password.setText("");
				comboBox.setSelectedIndex(-1);
			}
		});
		btn_save.setBounds(535, 247, 85, 21);
		contentPane.add(btn_save);

		JButton btn_edit = new JButton("Edit");
		btn_edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UserDB data = new UserDB();
				data.id = Integer.parseInt(textField_id.getText());
				data.username = textField_username.getText().trim();
				data.password = textField_password.getText().trim();
				int index = comboBox.getSelectedIndex();
				String usertype = comboBox.getItemAt(index).toString();
				data.usertype = usertype;
				UserManager.editUser(data);

				load();
				JOptionPane.showMessageDialog(UserFrame.this, "Done");

				textField_id.setText("");
				textField_username.setText("");
				textField_password.setText("");
				comboBox.setSelectedIndex(-1);
			}
		});
		btn_edit.setBounds(535, 301, 85, 21);
		contentPane.add(btn_edit);

		JButton btn_delete = new JButton("Delete");
		btn_delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(UserFrame.this, "Confirm delete?", "Delete", JOptionPane.OK_CANCEL_OPTION))
				{
					UserDB data = new UserDB();
					data.id = Integer.parseInt(textField_id.getText());
					UserManager.deleteUser(data);

					load();
					JOptionPane.showMessageDialog(UserFrame.this, "Done");

					textField_id.setText("");
					textField_username.setText("");
					textField_password.setText("");
					comboBox.setSelectedIndex(-1);
				}
			}
		});
		btn_delete.setBounds(535, 355, 85, 21);
		contentPane.add(btn_delete);

		JButton btn_clear = new JButton("Clear");
		btn_clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textField_id.setText("");
				textField_username.setText("");
				textField_password.setText("");
				comboBox.setSelectedIndex(-1);
			}
		});
		btn_clear.setBounds(649, 301, 85, 21);
		contentPane.add(btn_clear);

		String[] usertype =
		{ "admin", "user" };
		comboBox = new JComboBox(usertype); // add choice by insert String[] usertype as agrument
		comboBox.setBounds(575, 193, 115, 21);
		contentPane.add(comboBox);

		load(); // Display SQL data into JFrame table
	}

	public void load()
	{
		list = UserManager.getAllUsers();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Username");
		model.addColumn("Password");
		model.addColumn("User-Type");
		for (UserDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.username, c.password, c.usertype });
		}
		table.setModel(model);
	}
}
