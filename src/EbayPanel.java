import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.*;

public class EbayPanel extends JPanel {
	private JFormattedTextField display1, display2, display3, display4;
	private JComboBox<String> display5;
	private JLabel label1, label2, label3, label4, label5;
	private JButton calculateButton;
	private NumberFormat soldPriceFormat;
	private NumberFormat shippingChargeFormat;
	private NumberFormat shippingCostFormat;
	private double soldPrice;
	private double shippingCharge;
	private double shippingCost;
	String[] FeePercentage = { "Europa", "Buiten Europa" };

	public EbayPanel() {

		setUpFormats();
		setLayout(new BorderLayout());

		label1 = new JLabel("Verkoopprijs");
		label2 = new JLabel("Verzendvergoeding");
		label3 = new JLabel("Verzendkosten");
		label5 = new JLabel("Heffing");
		label4 = new JLabel("Opbrengst:");
		label4.setHorizontalAlignment(JLabel.CENTER);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 2));
		add(labelPanel, BorderLayout.NORTH);

		display1 = new JFormattedTextField(soldPriceFormat);
		display1.setEditable(true);
		display1.setHorizontalAlignment(JTextField.LEFT);
//		display1.addFocusListener(offSet); /* eventueel andere focus listener. */

		display2 = new JFormattedTextField(shippingChargeFormat);
		display2.setEditable(true);
		display2.setHorizontalAlignment(JTextField.LEFT);

		display3 = new JFormattedTextField(shippingCostFormat);
		display3.setEditable(true);
		display3.setHorizontalAlignment(JTextField.LEFT);

//		JComboBox<String> display5 = new JComboBox<>(FeePercentage);
		display5 = new JComboBox<>(FeePercentage);
		display5.setEditable(false);
		display5.setSelectedIndex(0);

		display4 = new JFormattedTextField("0");
		display4.setEditable(false);
		display4.setHorizontalAlignment(JTextField.CENTER);

		labelPanel.add(label1);
		labelPanel.add(display1);
		labelPanel.add(label2);
		labelPanel.add(display2);
		labelPanel.add(label3);
		labelPanel.add(display3);
		labelPanel.add(label5);
		labelPanel.add(display5);

		calculateButton = new JButton("Bereken");
		calculateButton.setPreferredSize(new Dimension(50, 50));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 5));
		add(buttonPanel, BorderLayout.CENTER);

		JPanel profitPanel = new JPanel();
		profitPanel.setLayout(new GridLayout(0, 1));
		add(profitPanel, BorderLayout.SOUTH);

		profitPanel.add(calculateButton);
		profitPanel.add(label4);
		profitPanel.add(display4);

		// Caret rechts van de invoer zetten van display1
		display1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display1.setCaretPosition(display1.getText().length());
//						display1.selectAll(); /* om alle invoer meteen te selecteren in plaats van de caret rechts te zetten. */
					}
				});
			}
		});

		// Caret rechts van de invoer zetten van display2
		display2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display2.setCaretPosition(display2.getText().length());
//						display2.selectAll(); /* om alle invoer meteen te selecteren in plaats van de caret rechts te zetten. */
					}
				});
			}
		});

		// Caret rechts van de invoer zetten van display3
		display3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display3.setCaretPosition(display3.getText().length());
//						display3.selectAll(); /* om alle invoer meteen te selecteren in plaats van de caret rechts te zetten. */
					}
				});
			}
		});

		// Berekening uitvoeren na druk op de Calculate knop.
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soldPrice = ((Number) display1.getValue()).doubleValue();
				shippingCharge = ((Number) display2.getValue()).doubleValue();
				shippingCost = ((Number) display3.getValue()).doubleValue();

				// 10% Ebay fee | 3,4% Paypal fee | €0,35 Paypal fee
				if (display5.getSelectedItem().toString().equals("Europa")) {
					double payment = ((((soldPrice) + (shippingCharge)) * 0.866) - (shippingCost)) - 0.35;

					DecimalFormat df = new DecimalFormat("#.##");
					df.setRoundingMode(RoundingMode.HALF_UP);

					display4.setValue(df.format(new Double(payment)));
				}
				// 10% Ebay fee | 5,4% Paypal fee | €0,35 Paypal fee
				else if (display5.getSelectedItem().toString().equals("Buiten Europa")) {
					double payment = ((((soldPrice) + (shippingCharge)) * 0.846) - (shippingCost)) - 0.35;

					DecimalFormat df = new DecimalFormat("#.##");
					df.setRoundingMode(RoundingMode.HALF_UP);

					display4.setValue(df.format(new Double(payment)));
				}
			}
		});
	}

	// Zorgen voor alleen cijfer invoer
	private void setUpFormats() {
		soldPriceFormat = NumberFormat.getNumberInstance();
		shippingChargeFormat = NumberFormat.getNumberInstance();
		shippingCostFormat = NumberFormat.getNumberInstance();
	}
}