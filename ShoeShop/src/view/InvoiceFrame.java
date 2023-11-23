package view;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import model.CompanyInfoDB;
import model.CompanyInfoManager;
import model.CustomerDB;
import model.ProductDB;
import model.InvoiceDetail;
import model.InvoiceManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InvoiceFrame extends JFrame
{

	private JPanel contentPane;
	public JPanel panel;
	public JTable table;
	CustomerDB xCustomer;
	ProductDB xProduct;
	ArrayList<InvoiceDetail> detailList;
	public JScrollPane scrollPane;

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
					InvoiceFrame frame = new InvoiceFrame();
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
	public InvoiceFrame()
	{
		setResizable(false);
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e) // When JFrame resize , JPanel also resize
			{
				panel.setBounds(0, 60, getWidth() - 20, getHeight() - 100);
			}
		});
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get screen resolution
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		setBounds(100, 100, 1251, 1152);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 53, getWidth() - 20, getHeight() - 100);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ใบเสร็จรับเงิน");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(508, 10, 196, 62);
		panel.add(lblNewLabel);

		JLabel label_companyInfo = new JLabel("New label");
		label_companyInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_companyInfo.setBounds(21, 84, 1208, 34);
		panel.add(label_companyInfo);
		CompanyInfoDB data = CompanyInfoManager.getCompanyInfo();
		String CompanyInfo = "บริษัท " + data.company_name + " ที่อยู่ " + data.address + " โทร." + data.phone + " Email: " + data.email;
		label_companyInfo.setText(CompanyInfo);

		JLabel label_customerInfo = new JLabel("New label");
		label_customerInfo.setForeground(new Color(30, 144, 255));
		label_customerInfo.setBackground(Color.WHITE);
		label_customerInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_customerInfo.setBounds(120, 128, 350, 34);
		panel.add(label_customerInfo);

		JLabel lblNewLabel_1 = new JLabel("รายละเอียด");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(21, 151, 100, 62);
		panel.add(lblNewLabel_1);

		JLabel label_date = new JLabel("New label");
		label_date.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_date.setBounds(21, 26, 127, 43);
		panel.add(label_date);
		label_date.setText(new SimpleDateFormat().format(Calendar.getInstance().getTime())); // display current time and date

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 223, 1200, 409);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("ได้รับเงินจาก :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(21, 128, 108, 34);
		panel.add(lblNewLabel_2);

		JButton btn_selectCustomer = new JButton("Select Customer");
		btn_selectCustomer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomerSearch menu = new CustomerSearch();
				menu.setSelectCustomer(new SelectCustomerI()
				{

					@Override
					public void select(CustomerDB data)
					{
						xCustomer = data;
						String s = data.name + " " + data.surname + "(" + data.phone + ") (id " + data.id + ")";
						label_customerInfo.setText(s);
					}
				});
				menu.setVisible(true);
			}
		});
		btn_selectCustomer.setBounds(10, 10, 107, 21);
		contentPane.add(btn_selectCustomer);

		JButton btn_selectProduct = new JButton("Select Product");
		btn_selectProduct.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ProductSearch menu = new ProductSearch();
				menu.setVisible(true);
				menu.setSelectProduct(new SelectProductI()
				{

					@Override
					public void select(ProductDB data)
					{
						setDetail(data);
					}
				});

			}
		});
		btn_selectProduct.setBounds(139, 10, 107, 21);
		contentPane.add(btn_selectProduct);

		JButton btn_save = new JButton("Save");
		btn_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (xCustomer != null)
				{
					InvoiceManager.saveInvoice(xCustomer, detailList);
					JOptionPane.showMessageDialog(InvoiceFrame.this, "Done");
				}
			}
		});
		btn_save.setBounds(266, 10, 107, 21);
		contentPane.add(btn_save);

		JButton btnNewButton = new JButton("Print");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(new InvoicePrint(InvoiceFrame.this));
				boolean doPrint = job.printDialog();
				if (doPrint)
				{
					try
					{
						job.print();
					} catch (Exception e2)
					{
						e2.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(393, 10, 107, 21);
		contentPane.add(btnNewButton);

		detailList = new ArrayList<InvoiceDetail>();
	}

	public void setDetail(ProductDB xProduct)
	{
		InvoiceDetail x = new InvoiceDetail();
		x.no = detailList.size() + 1; // at first size is zero
		x.price_per_unit = xProduct.price_per_unit;
		x.product_name = xProduct.product_name;
		x.quantity = 1;
		x.totalPrice = x.price_per_unit * x.quantity;
		x.product = xProduct;

		detailList.add(x);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("no");
		model.addColumn("image");
		model.addColumn("product_name");
		model.addColumn("quantity");
		model.addColumn("price_per_unit");
		model.addColumn("totalPrice");
		for (InvoiceDetail c : detailList)
		{
			model.addRow(new Object[]
			{ c.no, c.product.product_image, c.product_name, c.quantity, c.price_per_unit, c.totalPrice });
		}
		table.setModel(model);

		table.getColumn("image").setCellRenderer(new InvoiceDetailTableRenderer());
		// "setCellRenderer" method is used to set a custom cell renderer for a specific column in a JTable.

		for (int i = 0; i < table.getRowCount(); i++)
		{
			table.setRowHeight(i, 120); // set each height of each row to 120 , so it can fit the image size
		}
	}
}

class InvoiceDetailTableRenderer extends DefaultTableCellRenderer implements TableCellRenderer
// "DefaultTableCellRenderer" is use to provides a way to customize the rendering of data within each cell, allowing you to change the way the data is displayed.
// "TableCellRenderer" is for customize the visual representation of cell contents within a JTable.
{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
	{
		ImageComponent i = new ImageComponent();
		i.image = (BufferedImage) value;
		return i;
	}
}

class ImageComponent extends JComponent // JComponent is a fundamental class for creating graphical user interface (GUI) components.
{
	public BufferedImage image;

	public void paint(Graphics g)
	{
		if (image != null)
		{
			g.drawImage(image, 0, 0, 120, 120, 0, 0, image.getWidth(), image.getHeight(), this);
		}
	}
}

class InvoicePrint implements Printable
{
	InvoiceFrame xframe;

	public InvoicePrint(InvoiceFrame frame)
	{
		xframe = frame;
	}

	public int print(Graphics g, PageFormat pf, int page) throws PrinterException
	{
		if (page > 0)
		{
			return NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		for (int i = 0; i < xframe.panel.getComponentCount(); i++)
		{
			Component c = xframe.panel.getComponent(i);
			if (c instanceof JLabel)
			{
				JLabel l = (JLabel) c;
				g2d.setFont(l.getFont());
				g2d.drawString(l.getText(), (int) (l.getLocation().getX()), (int) (l.getLocation().getY() + l.getHeight()));
			}
		}

		int x = 10; // เว้นขอบบน
		int y = (int) xframe.scrollPane.getLocation().getY() + 40; // เว้นขอบซ้าย - ชวา
		for (int i = 0; i < xframe.table.getColumnCount(); i++)
		{
			g2d.setFont(xframe.table.getFont());
			g2d.drawString(xframe.table.getColumnName(i), x, y);
			x += xframe.table.getColumn(xframe.table.getColumnName(i)).getWidth();
		}

		y += 40;
		for (int j = 0; j < xframe.table.getRowCount(); j++)
		{
			x = 10;
			for (int i = 0; i < xframe.table.getColumnCount(); i++)
			{
				if (xframe.table.getColumnName(i).equals("image"))
				{
					BufferedImage image = (BufferedImage) xframe.table.getValueAt(j, i);
					g.drawImage(image, x, y, x + 120, y + 120, 0, 0, image.getWidth(), image.getHeight(), null);
				} else
				{
					g2d.setFont(xframe.table.getFont());
					g2d.drawString("" + xframe.table.getValueAt(j, i), x, y);
				}

				x += xframe.table.getColumn(xframe.table.getColumnName(i)).getWidth();
			}
			y += xframe.table.getRowHeight(j);
		}

		return PAGE_EXISTS;
	}
}