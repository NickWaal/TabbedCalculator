import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class OverzichtsTabel extends JPanel {
	private JFormattedTextField filterDisplay;
	private JLabel filterLabel;
	public static JTable table;
	private JButton save, filter;
	Color background = new Color(235, 235, 224);
	Color soldRow = new Color(0, 153, 0);

	public OverzichtsTabel() {
		setLayout(new BorderLayout(3, 3));
		table = new JTable(new MyModel());
		table.setPreferredScrollableViewportSize(new Dimension(700, 200));
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Object.class, new YourTableCellRenderer());
//		table.setAutoCreateRowSorter(true);
		JPanel readPanel = new JPanel();
		add(readPanel);
		// scrollPane maken en table er aan toevoegen
		JScrollPane scrollPane = new JScrollPane(table);
		// scrollPane aan readPanel toevoegen
		add(scrollPane, BorderLayout.CENTER);
		// border instellen
		setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 1, 2, 2));
		add(buttons, BorderLayout.SOUTH);

		save = new JButton("Opslaan");
		buttons.add(save);

		filter = new JButton("Zoek");

		JPanel zoeken = new JPanel();
		zoeken.setLayout(new GridLayout(1, 2, 2, 2));
		add(zoeken, BorderLayout.NORTH);

		filterLabel = new JLabel("Zoek:");
		filterLabel.setHorizontalAlignment(JLabel.CENTER);

		filterDisplay = new JFormattedTextField("");
		filterDisplay.setHorizontalAlignment(JFormattedTextField.LEFT);

//		zoeken.add(filterLabel);
		zoeken.add(filterDisplay);
		zoeken.add(filter);

		CSVFile Rd = new CSVFile();
		MyModel NewModel = new MyModel();
		OverzichtsTabel.table.setModel(NewModel);
		table.setBackground(background);
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		File DataFile = new File(desktop, "Ebay Tool.csv");

		ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
		NewModel.AddCSVData(Rs2);
		System.out.println("Rows: " + NewModel.getRowCount());
		System.out.println("Cols: " + NewModel.getColumnCount());

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(400);
		table.getColumnModel().getColumn(3).setPreferredWidth(400);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter(OverzichtsTabel.table.getModel());
		trs.setComparator(0, new IntComparator());
		trs.setComparator(2, new DoubleComparator());
		trs.setComparator(3, new DoubleComparator());

		table.setRowSorter(trs);
		table.setAutoCreateRowSorter(false);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = "";
				System.out.println("Knop werkt");

				if (MyModel.NewModel == null) {
					try {
						File desktop2 = new File(System.getProperty("user.home"), "Desktop");
						File textFile = new File(desktop2, "Ebay Tool.csv");

						FileWriter fw = new FileWriter(textFile);
						BufferedWriter bw = new BufferedWriter(fw);

						for (int i = 0; i < NewModel.getRowCount(); i++) {
							for (int j = 0; j < NewModel.getColumnCount(); j++) {
								if (j < (NewModel.getColumnCount()) - 1) {
									s += NewModel.getValueAt(i, j) + ";";
								} else {
									s += NewModel.getValueAt(i, j);
									System.out.println("row:" + i);
								}
							}
							bw.write(s);
							s = "";
							bw.newLine();
						}
						bw.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				} else {
					OverzichtsTabel.table.setModel(MyModel.NewModel);
					try {
						File desktop2 = new File(System.getProperty("user.home"), "Desktop");
						File textFile = new File(desktop2, "Ebay Tool.csv");

						FileWriter fw = new FileWriter(textFile);
						BufferedWriter bw = new BufferedWriter(fw);

						for (int i = 0; i < MyModel.NewModel.getRowCount(); i++) {
							for (int j = 0; j < MyModel.NewModel.getColumnCount(); j++) {
								if (j < (MyModel.NewModel.getColumnCount()) - 1) {
									s += MyModel.NewModel.getValueAt(i, j) + ";";
								} else {
									s += MyModel.NewModel.getValueAt(i, j);
									System.out.println("row:" + i);
								}
							}
							bw.write(s);
							s = "";
							bw.newLine();
						}
						bw.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}
}

class IntComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		Integer int1 = Integer.parseInt((String) o1);
		Integer int2 = Integer.parseInt((String) o2);
		return int1.compareTo(int2);
	}

	public boolean equals(Object o2) {
		return this.equals(o2);
	}
}

class DoubleComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		Double int1 = Double.parseDouble((String) o1);
		Double int2 = Double.parseDouble((String) o2);
		return int1.compareTo(int2);
	}

	public boolean equals(Object o2) {
		return this.equals(o2);
	}
}

// CSV file lezen
class CSVFile {
	private final ArrayList<String[]> Rs = new ArrayList<String[]>();
	private String[] OneRow;

	public ArrayList<String[]> ReadCSVfile(File DataFile) {
		try {
			BufferedReader brd = new BufferedReader(new FileReader(DataFile));
			while (brd.ready()) {
				String st = brd.readLine();
				OneRow = st.split(",\\s+|;");
				Rs.add(OneRow);
				System.out.println(Arrays.toString(OneRow));
			}
		} catch (Exception e) {
			String errmsg = e.getMessage();
			System.out.println("File not found:" + errmsg);
		}
		return Rs;
	}
}

class MyModel extends AbstractTableModel {
	private final String[] columnNames = { "#", "Titel", "Verwachtte Prijs", "Verkoop Prijs" };
	private ArrayList<String[]> Data = new ArrayList<String[]>();

	static MyModel NewModel;

	public void AddCSVData(ArrayList<String[]> DataIn) {
		this.Data = DataIn;
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;// length;
	}

	@Override
	public int getRowCount() {
		return Data.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return Data.get(row)[col];
	}

	@Override
	public void setValueAt(Object aValue, int row, int col) {
		Data.get(row)[col] = (String) aValue;
		fireTableCellUpdated(row, col);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return column == 1 || column == 2 || column == 3 ? true : false;
	}

	public Class getColumnClass(int column) {
		Class returnValue;
		if (column == 0) {
			returnValue = Integer.class;
		} else {
			returnValue = Object.class;
		}
		return returnValue;
	}

	public void refresh() {
		CSVFile Rd = new CSVFile();
		NewModel = new MyModel();
		OverzichtsTabel.table.setModel(NewModel);
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		File DataFile = new File(desktop, "Ebay Tool.csv");

		ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
		NewModel.AddCSVData(Rs2);
		fireTableDataChanged();
		fireTableStructureChanged();
		System.out.println("Rows: " + NewModel.getRowCount());
	}

}

class YourTableCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color soldRow = new Color(0, 153, 0);
//		String status = (String) table.getModel().getValueAt(2, 2);
//		double values = Double.parseDouble(status);

		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (j == (table.getColumnCount()) - 1) { // nog kijken of dit beter kan
					if (column == 0 || column == 1 || column == 2) {
//									c.setForeground(soldRow);
						c.setForeground(Color.BLACK);
//									c.setBackground(/* achtergrond kleur */);
					} else if (column == 3) {
						String status = (String) table.getModel().getValueAt(i, 3);
						String status2 = status.replace(",", ".");
						double values = Double.parseDouble(status2);
						if (values > 0 && row == i) {
							c.setForeground(soldRow);
							System.out.println(table.getModel().getValueAt(i, 3));
						} else if (values <= 0 && row == i) {
							c.setForeground(Color.RED);
							System.out.println(table.getModel().getValueAt(i, 3));
							System.out.println("rood");
						}
					}
				}
			}
		}
		return c;
	}
}
