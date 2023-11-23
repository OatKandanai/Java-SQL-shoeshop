package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.CustomerDB;
import model.CustomerManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerSearch extends JFrame
{

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_search;
	private JButton button_search;
	ArrayList<CustomerDB> list;
	SelectCustomerI xSelectCustomerI;

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
					CustomerSearch frame = new CustomerSearch();
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
	public CustomerSearch()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 218, 766, 415);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		textField_search = new JTextField();
		textField_search.setBounds(10, 175, 96, 19);
		contentPane.add(textField_search);
		textField_search.setColumns(10);

		button_search = new JButton("Search");
		button_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				search();
			}
		});
		button_search.setBounds(142, 174, 85, 21);
		contentPane.add(button_search);

		JButton button_select = new JButton("Select");
		button_select.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (table.getSelectedRow() == -1) // If no customer is selected
				{
					JOptionPane.showMessageDialog(CustomerSearch.this, "No row selected");
					return;
				}
				if (xSelectCustomerI != null)
				{
					if (list != null)
					{
						xSelectCustomerI.select(list.get(table.getSelectedRow()));
						setVisible(false);
					}
				}
			}
		});
		button_select.setBounds(258, 174, 85, 21);
		contentPane.add(button_select);

		load(); // display data from SQL table into JFrame
	}

	public void load()
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

	public void search()
	{
		list = CustomerManager.searchCustomer(textField_search.getText().trim());
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

	public void setSelectCustomer(SelectCustomerI x)
	{
		xSelectCustomerI = x;
	}
}

interface SelectCustomerI
{
	public void select(CustomerDB data);
}