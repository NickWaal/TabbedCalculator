import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JLabel;

public class ReadPanel extends JPanel {
	private JFormattedTextField display1, display2, display3, display4;
	private JLabel label1, label2, label3, label4;
	private JButton calculateButton;
	private NumberFormat soldPriceFormat;
	private NumberFormat shippingChargeFormat;
	private NumberFormat shippingCostFormat;
	private double soldPrice;
	private double shippingCharge;
	private double shippingCost;
//	private double profit = 0;

	public ReadPanel() {

		setLayout(new BorderLayout());

		label1 = new JLabel("Verwachtte totale opbrengst:");
		label1.setHorizontalAlignment(JLabel.LEFT);

		label2 = new JLabel("Opbrengst tot nu:");
		label2.setHorizontalAlignment(JLabel.LEFT);

		label3 = new JLabel("Verwachtte winst:");
		label3.setHorizontalAlignment(JLabel.LEFT);

		label4 = new JLabel("Winst tot nu:");
		label4.setHorizontalAlignment(JLabel.LEFT);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 2));
		add(labelPanel, BorderLayout.NORTH);

		display1 = new JFormattedTextField("0");
		display1.setEditable(false);
		display1.setHorizontalAlignment(JTextField.CENTER);

		display2 = new JFormattedTextField("0");
		display2.setEditable(false);
		display2.setHorizontalAlignment(JTextField.CENTER);

		display3 = new JFormattedTextField("0");
		display3.setEditable(false);
		display3.setHorizontalAlignment(JTextField.CENTER);

		display4 = new JFormattedTextField("0");
		display4.setEditable(false);
		display4.setHorizontalAlignment(JTextField.CENTER);

		calculateButton = new JButton("Bereken");
		calculateButton.setPreferredSize(new Dimension(50, 50));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 1));
		add(buttonPanel, BorderLayout.SOUTH);

		JPanel profitPanel = new JPanel();
		profitPanel.setLayout(new GridLayout(0, 2, 0, 5));
		add(profitPanel, BorderLayout.NORTH);

		buttonPanel.add(calculateButton);
		profitPanel.add(label1);
		profitPanel.add(display1);
		profitPanel.add(label2);
		profitPanel.add(display2);
		profitPanel.add(label3);
		profitPanel.add(display3);
		profitPanel.add(label4);
		profitPanel.add(display4);

		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				File desktop = new File(System.getProperty("user.home"), "Desktop");
				File DataFile = new File(desktop, "Ebay Tool.csv");

				String line = null;
				double totaal = 0;

				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.HALF_UP);

				// here we create a container for read and splitted lines
				ArrayList<String[]> lines = new ArrayList<String[]>();
				try {
					BufferedReader br = new BufferedReader(new FileReader(DataFile));

					while ((line = br.readLine()) != null) {

						String[] distance = line.split(",");

						// Hier alle lines toevoegen die gelezen worden
						lines.add(distance);
//						        int columnToSum = 1;
//						        int sum = sumRows(lines, columnToSum );
//			                    System.out.println(line);

//			                    String sumString = String.valueOf(sum);
//			                    display4.setText(line);

						line = line.substring(line.indexOf(';', 1));
						line = line.substring(line.indexOf(';', 1));
						line = line.substring(line.indexOf(';', 1));
//			                    line = line.substring(line.indexOf(';', 1));
						line = line.substring(1);

						System.out.println(line);

						String test = line.replace(",", ".");

						double sum = Double.parseDouble(test);

						totaal += sum;
						System.out.println(totaal);
						String winst = String.valueOf(totaal).replace(".", ",");
						display2.setValue("€ " + df.format(new Double(totaal)));
//						display2.setText("€ " + winst);
						display4.setValue("€ " + df.format(new Double((totaal - CostPanel.totaal))));

					}
					br.close();
					line = null;
					totaal = 0;
					lines.clear();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					BufferedReader br = new BufferedReader(new FileReader(DataFile));

					while ((line = br.readLine()) != null) {

						String[] distance = line.split(",");

						// Hier alle lines toevoegen die gelezen worden
						lines.add(distance);
//						        int columnToSum = 1;
//						        int sum = sumRows(lines, columnToSum );
//			                    System.out.println(line);

//			                    String sumString = String.valueOf(sum);
//			                    display4.setText(line);

						line = line.substring(line.indexOf(';', 1));
						line = line.substring(line.indexOf(';', 1));
//						line = line.substring(line.indexOf(';', 1));
//			                    line = line.substring(line.indexOf(';', 1));
//						line = line.substring(line.indexOf(';', 0));

						line = line.substring(line.indexOf(";") + 1);

						line = line.substring(0, line.indexOf(';'));

						System.out.println(line);

						String test = line.replace(",", ".");

						double sum = Double.parseDouble(test);

						totaal += sum;
						System.out.println(totaal);
						String winst = String.valueOf(totaal).replace(".", ",");
						display1.setValue("€ " + df.format(new Double(totaal)));
						display3.setValue("€ " + df.format(new Double((totaal - CostPanel.totaal))));
//						display1.setText("€ " + winst);

					}
					br.close();
					line = null;
					totaal = 0;
					lines = null;

				} catch (IOException e1) {
					e1.printStackTrace();
				}

//			        // column sum
//			        int columnToSum = 1;
//			        int sum = sumRows(lines, columnToSum );
//			        System.out.println("The sum of column "+ columnToSum +" is: " + sum); 
			}

		});
	}
}
