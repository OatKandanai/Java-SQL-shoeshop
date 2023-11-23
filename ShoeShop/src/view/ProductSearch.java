package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.ProductDB;
import model.ProductManager;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductSearch extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table;
	ArrayList<ProductDB> list;
	SelectProductI xSelectProductI;
	private ImagePanel imagePanel;

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
					ProductSearch frame = new ProductSearch();
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
	public ProductSearch()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 187, 766, 415);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		textField_search = new JTextField();
		textField_search.setColumns(10);
		textField_search.setBounds(10, 144, 96, 19);
		contentPane.add(textField_search);

		JButton button_search = new JButton("Search");
		button_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				search();
			}
		});
		button_search.setBounds(142, 143, 85, 21);
		contentPane.add(button_search);

		JButton button_select = new JButton("Select");
		button_select.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (table.getSelectedRow() == -1)
				{
					JOptionPane.showMessageDialog(ProductSearch.this, "No row selected");
					return;
				}
				if (xSelectProductI != null)
				{
					if (list != null)
					{
						xSelectProductI.select(list.get(table.getSelectedRow()));
						setVisible(false);
					}
				}
			}
		});
		button_select.setBounds(258, 143, 85, 21);
		contentPane.add(button_select);

		imagePanel = new ImagePanel();
		imagePanel.setBounds(559, 10, 217, 167);
		contentPane.add(imagePanel);

		load(); // display data from SQL table into JFrame
	}

	public void load()
	{
		list = ProductManager.getAllProduct();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product_id");
		model.addColumn("product_name");
		model.addColumn("price_per_unit");
		model.addColumn("product_description");

		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description });
		}
		table.setModel(model);
	}

	public void search()
	{
		list = ProductManager.searchProduct(textField_search.getText().trim());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product_id");
		model.addColumn("product_name");
		model.addColumn("price_per_unit");
		model.addColumn("product_description");

		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description });
		}
		table.setModel(model);
	}

	public void setSelectProduct(SelectProductI x)
	{
		xSelectProductI = x;
	}
}

interface SelectProductI
{
	public void select(ProductDB data);
}