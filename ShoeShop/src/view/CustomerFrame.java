package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.CustomerDB;
import model.CustomerManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerFrame extends JFrame
{

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_surname;
	private JTextField textField_phone;
	ArrayList<CustomerDB> list;

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
					CustomerFrame frame = new CustomerFrame();
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
	public CustomerFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 536, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 326, 239);
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

				int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
				String name = table.getValueAt(selectedRow, 1).toString();
				String surname = table.getValueAt(selectedRow, 2).toString();
				String phone = table.getValueAt(selectedRow, 3).toString();

				textField_id.setText("" + id);
				textField_name.setText(name);
				textField_surname.setText(surname);
				textField_phone.setText(phone);
			}
		});

		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(346, 12, 46, 14);
		contentPane.add(lblNewLabel);

		textField_id = new JTextField();
		textField_id.setBackground(Color.LIGHT_GRAY);
		textField_id.setEditable(false);
		textField_id.setBounds(412, 11, 86, 20);
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(412, 42, 86, 20);
		contentPane.add(textField_name);

		JLabel lblName = new JLabel("name");
		lblName.setBounds(346, 43, 46, 14);
		contentPane.add(lblName);

		textField_surname = new JTextField();
		textField_surname.setColumns(10);
		textField_surname.setBounds(412, 71, 86, 20);
		contentPane.add(textField_surname);

		JLabel lblSurname = new JLabel("surname");
		lblSurname.setBounds(346, 74, 56, 14);
		contentPane.add(lblSurname);

		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(412, 102, 86, 20);
		contentPane.add(textField_phone);

		JLabel lblPhone = new JLabel("phone");
		lblPhone.setBounds(346, 105, 46, 14);
		contentPane.add(lblPhone);

		JButton btnNewButton = new JButton("SAVE NEW");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomerDB x = new CustomerDB(0, textField_name.getText().trim(), textField_surname.getText().trim(), textField_phone.getText().trim());
				CustomerManager.saveNewCustomer(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				JOptionPane.showMessageDialog(CustomerFrame.this, "DONE");
			}
		});
		btnNewButton.setBounds(375, 133, 86, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("EDIT");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomerDB x = new CustomerDB(Integer.parseInt(textField_id.getText().trim()), textField_name.getText().trim(), textField_surname.getText().trim(), textField_phone.getText().trim());
				CustomerManager.editCustomer(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				JOptionPane.showMessageDialog(CustomerFrame.this, "DONE");
			}
		});
		btnNewButton_1.setBounds(375, 167, 86, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("DELETE");
		btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(CustomerFrame.this, "Do you want to delete?", "DELETE?", JOptionPane.OK_CANCEL_OPTION)) // Confirm ว่าต้องการ delete หรือไม่
				{
					CustomerDB x = new CustomerDB(Integer.parseInt(textField_id.getText().trim()), textField_name.getText().trim(), textField_surname.getText().trim(), textField_phone.getText().trim());
					CustomerManager.deleteCustomer(x);
					load();
					textField_id.setText("");
					textField_name.setText("");
					textField_surname.setText("");
					textField_phone.setText("");
					JOptionPane.showMessageDialog(CustomerFrame.this, "DONE");
				}
			}
		});
		btnNewButton_2.setBounds(375, 201, 86, 23);
		contentPane.add(btnNewButton_2);

		load();
	}

	public void load() // ทำให้ข้อมูลจากตาราง customers2 ขึ้นใน UI
	{
		list = CustomerManager.getAllCustomer();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("name");
		model.addColumn("surname");
		model.addColumn("phone");
		for (CustomerDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.name, c.surname, c.phone });
		}
		table.setModel(model);
	}
}
