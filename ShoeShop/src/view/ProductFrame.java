package view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import model.CustomerDB;
import model.CustomerManager;
import model.ProductDB;
import model.ProductManager;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_price;
	private JTextField textField_des;
	ArrayList<ProductDB> list;
	private JTable table;
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
					ProductFrame frame = new ProductFrame();
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
	public ProductFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 654, 444);
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
				double price = Double.parseDouble(table.getValueAt(selectedRow, 2).toString());
				String des = table.getValueAt(selectedRow, 3).toString();

				BufferedImage img = list.get(selectedRow).product_image; // get รูปจากตาราง
				if (img != null) // check ว่ามีรูปใส่เข้ามาไหม
				{
					ImagePanel.setImage(img);
					repaint();
				}

				textField_id.setText("" + id);
				textField_name.setText(name);
				textField_price.setText("" + price);
				textField_des.setText(des);
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblProductid = new JLabel("product_id");
		lblProductid.setBounds(346, 12, 95, 14);
		contentPane.add(lblProductid);

		JLabel lblProductname = new JLabel("product_name");
		lblProductname.setBounds(346, 43, 95, 14);
		contentPane.add(lblProductname);

		JLabel lblPriceperunit = new JLabel("price_per_unit");
		lblPriceperunit.setBounds(346, 74, 95, 14);
		contentPane.add(lblPriceperunit);

		JLabel lblProductdescription = new JLabel("product_description");
		lblProductdescription.setBounds(346, 105, 95, 14);
		contentPane.add(lblProductdescription);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setColumns(10);
		textField_id.setBackground(Color.LIGHT_GRAY);
		textField_id.setBounds(451, 11, 86, 20);
		contentPane.add(textField_id);

		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(451, 42, 86, 20);
		contentPane.add(textField_name);

		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(451, 71, 86, 20);
		contentPane.add(textField_price);

		textField_des = new JTextField();
		textField_des.setColumns(10);
		textField_des.setBounds(451, 102, 86, 20);
		contentPane.add(textField_des);

		JButton Button_save = new JButton("SAVE NEW");
		Button_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ProductDB x = new ProductDB();
				x.product_id = 0;
				x.product_name = textField_name.getText().trim();
				x.price_per_unit = Double.parseDouble(textField_price.getText().trim());
				x.product_description = textField_des.getText().trim();
				x.product_image = (BufferedImage) ImagePanel.getImage();

				ProductManager.saveNewProduct(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_price.setText("");
				textField_des.setText("");
				JOptionPane.showMessageDialog(ProductFrame.this, "DONE");
			}
		});
		Button_save.setBounds(346, 371, 86, 23);
		contentPane.add(Button_save);

		JButton Button_edit = new JButton("EDIT");
		Button_edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ProductDB x = new ProductDB();
				x.product_id = Integer.parseInt(textField_id.getText());
				x.product_name = textField_name.getText().trim();
				x.price_per_unit = Double.parseDouble(textField_price.getText().trim());
				x.product_description = textField_des.getText().trim();
				x.product_image = (BufferedImage) ImagePanel.getImage();

				ProductManager.editProduct(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_price.setText("");
				textField_des.setText("");
				JOptionPane.showMessageDialog(ProductFrame.this, "DONE");
			}
		});
		Button_edit.setBounds(442, 371, 86, 23);
		contentPane.add(Button_edit);

		JButton Button_delete = new JButton("DELETE");
		Button_delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(ProductFrame.this, "Confirm delete?", "DELETE?", JOptionPane.OK_CANCEL_OPTION))
					;
				{
					ProductDB x = new ProductDB();
					x.product_id = Integer.parseInt(textField_id.getText());
					x.product_name = textField_name.getText().trim();
					x.price_per_unit = Double.parseDouble(textField_price.getText().trim());
					x.product_description = textField_des.getText().trim();
					x.product_image = (BufferedImage) ImagePanel.getImage();

					ProductManager.deleteProduct(x);
					load();
					textField_id.setText("");
					textField_name.setText("");
					textField_price.setText("");
					textField_des.setText("");
					JOptionPane.showMessageDialog(ProductFrame.this, "DONE");
				}
			}
		});
		Button_delete.setBounds(538, 371, 86, 23);
		contentPane.add(Button_delete);

		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser img = new JFileChooser();
				img.addChoosableFileFilter(new OpenFileFilter("jpeg", "JPEG format"));
				img.addChoosableFileFilter(new OpenFileFilter("jpg", "JPG format"));
				img.addChoosableFileFilter(new OpenFileFilter("png", "PNG format"));
				img.addChoosableFileFilter(new OpenFileFilter("svg", "SVG format"));
				img.setAcceptAllFileFilterUsed(false);
				int returnVal = img.showOpenDialog(ProductFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) // ถ้า UPLOAD รูป = true
				{
					File f = img.getSelectedFile();
					try
					{
						BufferedImage buff_img = ImageIO.read(f);
						ImagePanel.setImage(buff_img); // send image to class ImagePanel to show Image
						repaint();
						
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				load();
			}
		});
		btnNewButton.setBounds(247, 290, 89, 23);
		contentPane.add(btnNewButton);

		imagePanel = new ImagePanel();
		imagePanel.setBounds(375, 186, 184, 142);
		contentPane.add(imagePanel);

		load();
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
}

class OpenFileFilter extends FileFilter // class ซ้อน class
{

	String description = "";
	String fileExt = "";

	public OpenFileFilter(String extension)
	{
		fileExt = extension;
	}

	public OpenFileFilter(String extension, String typeDescription)
	{
		fileExt = extension;
		this.description = typeDescription;
	}

	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		return (f.getName().toLowerCase().endsWith(fileExt));
	}

	@Override
	public String getDescription()
	{
		return description;
	}
}
