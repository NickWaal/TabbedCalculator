import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CostPanel extends JPanel {

	private NumberFormat costFormat;
	private JFormattedTextField cost, expectedPrice, actualPrice, total, display4, display5;
	private JButton save, calculateButton;
	private JLabel label4, label5;
	static double totaal;

	public CostPanel() {

//		setUpFormats();
		setLayout(new BorderLayout());

		JPanel costPanel = new JPanel();
		costPanel.setLayout(new GridLayout(1, 2, 2, 2));
		add(costPanel, BorderLayout.NORTH);

		costPanel.add(new JLabel("Kosten:"));
		costPanel.add(cost = new JFormattedTextField(costFormat));

		label4 = new JLabel("Totaal kosten:");
		label4.setHorizontalAlignment(JLabel.CENTER);
		label5 = new JLabel("Laatst toegevoegd:");
		label5.setHorizontalAlignment(JLabel.CENTER);
		display5 = new JFormattedTextField("0");
		display5.setEditable(false);
		display5.setHorizontalAlignment(JTextField.CENTER);
		display4 = new JFormattedTextField("0");
		display4.setEditable(false);
		display4.setHorizontalAlignment(JTextField.CENTER);

		calculateButton = new JButton("Bereken");
		save = new JButton("Opslaan");

		JPanel profitPanel = new JPanel();
		profitPanel.setLayout(new GridLayout(0, 1, 2, 2));
		add(profitPanel, BorderLayout.SOUTH);

		profitPanel.add(save);
		profitPanel.add(label5);
		profitPanel.add(display5);
		profitPanel.add(label4);
		profitPanel.add(display4);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Bestandsnaam opgeven waar naar geschreven moet worden
				File desktop = new File(System.getProperty("user.home"), "Desktop");
				File csvFile = new File(desktop, "Uitgaven.csv");
				try {
					FileWriter fileWriter = new FileWriter(csvFile, true);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

					double cost1 = new Double(cost.getText().trim()).doubleValue();

					// . vervangen door , voor in de csv file.
					cost.setText(String.valueOf(cost1).replace(".", ","));

					// Write tekst
					bufferedWriter.write(cost.getText());

					// , daarna weer vervangen door .
					cost.setText(String.valueOf(cost1).replace(",", "."));

					// Want gaat niet automatisch naar nieuwe line.
					bufferedWriter.newLine();

					bufferedWriter.close(); /* Altijd files sluiten */
				} catch (IOException ex) {
					System.out.println("Error writing to file '" + csvFile + "'");
				}
				calculateButton.doClick(); /* Om na save automatisch opnieuw de sum te berekenen. */
				display5.setText("€ " + cost.getText().trim());
				display5.setForeground(Color.RED);
				cost.setText(null);
			}
		});

		// wordt automatisch aangeroepen na het saven. Knop is niet aanwezig op het
		// panel.
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				File desktop = new File(System.getProperty("user.home"), "Desktop");
				File csvFile = new File(desktop, "Uitgaven.csv");
				try {
					csvFile.createNewFile();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				String line = null;
				totaal = 0;

				try {
					BufferedReader br = new BufferedReader(new FileReader(csvFile));

					while ((line = br.readLine()) != null) {
						double tijdelijkTotaal = 0;

						String csvRegel = line.replace(",", ".");
						System.out.println(csvRegel);

						if (csvRegel.isEmpty()) {
							tijdelijkTotaal = 0;
						} else
							tijdelijkTotaal = Double.parseDouble(csvRegel);

						totaal += tijdelijkTotaal;
						System.out.println(totaal);
						String uitgaven = String.valueOf(totaal).replace(".", ",");
						display4.setText("€ " + uitgaven);
						if (totaal > 0)
							display4.setForeground(Color.RED);
						else
							display4.setForeground(Color.GREEN);
					}
					System.out.println("Totaal Kosten: €" + totaal);
					br.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		cost.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == ',')
					e.setKeyChar('.');
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

		// Om vanaf het begin al 1 keer de sum te berekenen.
		calculateButton.doClick();

	}

	// Zorgen voor alleen cijfer invoer
	private void setUpFormats() {
		costFormat = NumberFormat.getNumberInstance();
	}
}