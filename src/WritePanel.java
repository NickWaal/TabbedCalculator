import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class WritePanel extends JPanel {

	private JFormattedTextField title, expectedPrice, actualPrice, total;
	private JButton save, calculate, clear, exit;
	OverzichtsTabel table2 = new OverzichtsTabel();
//	private DecimalFormatSymbols dfs;
//	private DecimalFormat dFormat;

	public WritePanel() {

//		dfs = new DecimalFormatSymbols();
//		dfs.setDecimalSeparator(','); 
//		dfs.setGroupingSeparator('.'); 
//		dFormat = new DecimalFormat ("#0.##", dfs);NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

		setLayout(new BorderLayout());

		JPanel writePanel = new JPanel();
		writePanel.setLayout(new GridLayout(5, 5, 2, 2));
		add(writePanel, BorderLayout.NORTH);

		writePanel.add(new JLabel("Titel:"));
		writePanel.add(title = new JFormattedTextField());

		writePanel.add(new JLabel("Verwachtte Prijs:"));
		writePanel.add(expectedPrice = new JFormattedTextField());

		writePanel.add(new JLabel("Verkoop Prijs:"));
		writePanel.add(actualPrice = new JFormattedTextField());

//		writePanel.add(new JLabel("Totaal prijs:"));
//		writePanel.add(total = new JFormattedTextField());
//		total.setEditable(false);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4, 1, 2, 2));
		add(buttons, BorderLayout.SOUTH);

//		calculate = new JButton("Bereken");
//		buttons.add(calculate);
		save = new JButton("Opslaan");
		buttons.add(save);
		clear = new JButton("Leegmaken");
		buttons.add(clear);
		exit = new JButton("Sluiten");
		buttons.add(exit);

//		calculate.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				
//				double cost1 = new Double(actualPrice.getText().trim()).doubleValue();
//				double number1 = new Double(expectedPrice.getText().trim()).doubleValue();
//
//				double result = cost1 * number1;
//				total.setText(String.valueOf(result).replace(".", ","));
//				actualPrice.setText(String.valueOf(cost1).replace(".", ","));
//
//
//
//
//			}
//		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Bestandsnaam opgeven waar naar geschreven moet worden
				File desktop = new File(System.getProperty("user.home"), "Desktop");
				File DataFile = new File(desktop, "Ebay Tool.csv");
				int rows = OverzichtsTabel.table.getRowCount();
				try {

					FileWriter fileWriter = new FileWriter(DataFile, true);

					// Wrap FileWriter altijd in BufferedWriter.
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					// write() voegt niet automatisch een newline character toe.
					rows++;
					String newRow = String.valueOf(rows);
					bufferedWriter.write(newRow);
					bufferedWriter.write(';');
					bufferedWriter.write(title.getText());
					bufferedWriter.write(';');
					bufferedWriter.write(expectedPrice.getText());
					bufferedWriter.write(';');
					if (actualPrice.getText().equals(""))
						bufferedWriter.write("0");
					else
						bufferedWriter.write(actualPrice.getText());
//					bufferedWriter.write(';');
//					bufferedWriter.write(total.getText());
					bufferedWriter.newLine();
					// Altijd sluiten
					bufferedWriter.close();
				} catch (IOException ex) {
					System.out.println("Error writing to file '" + DataFile + "'");
				}
				// Tabel refreshen na het toevoegen van new line.
				MyModel model = new MyModel();
				model.refresh();

				TableRowSorter<DefaultTableModel> trs = new TableRowSorter(OverzichtsTabel.table.getModel());
				trs.setComparator(0, new IntComparator());
				trs.setComparator(2, new DoubleComparator());
				trs.setComparator(3, new DoubleComparator());

				OverzichtsTabel.table.setRowSorter(trs);
				OverzichtsTabel.table.setAutoCreateRowSorter(false);
			}
		});

		// Velden leegmaken
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title.setText(null);
				expectedPrice.setText(null);
				actualPrice.setText(null);
//				total.setText(null);
			}
		});

		// Afsluiten van applicatie
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		actualPrice.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '.')
					e.setKeyChar(',');
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	// JFrame aanmaken
	private static void createAndShowUI() {
		JFrame frame = new JFrame("CSV Writer");
		frame.setPreferredSize(new Dimension(400, 260));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new WritePanel());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}