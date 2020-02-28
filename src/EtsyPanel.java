import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class EtsyPanel extends JPanel {
	public JFormattedTextField display1, display2, display3, display4, display5;
	private JLabel label1, label2, label3, label4, label5;
	private JButton calculateButton;
	private NumberFormat soldPriceFormat;
	private NumberFormat shippingChargeFormat;
	private NumberFormat shippingCostFormat;
	private NumberFormat itemCostFormat;
	private double soldPrice;
	private double shippingCharge;
	private double shippingCost;
	private double itemCost;
//	private double profit = 0;

	ArrayList<JFormattedTextField> textFields = new ArrayList<>();

	public EtsyPanel() {

		setUpFormats();
		setLayout(new BorderLayout());

		textFields.add(display1);
		textFields.add(display2);
		textFields.add(display3);
		textFields.add(display4);
		textFields.add(display5);

		label1 = new JLabel("Verkoopprijs");
		label2 = new JLabel("Verzendvergoeding");
		label3 = new JLabel("Verzendkosten");
		label4 = new JLabel("Artikelkosten");
		label5 = new JLabel("Opbrengst:");
		label5.setHorizontalAlignment(JLabel.CENTER);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 2));
		add(labelPanel, BorderLayout.NORTH);

		display1 = new JFormattedTextField(soldPriceFormat);
//      display1.setValue(new Double(soldPrice));
//		display1.addPropertyChangeListener("value", this);
		display1.setEditable(true);
		display1.setHorizontalAlignment(JTextField.LEFT);

		display2 = new JFormattedTextField(shippingChargeFormat);
//      display2.setValue(new Double(shippingCharge));
//		display2.addPropertyChangeListener("value", this);
		display2.setEditable(true);
		display2.setHorizontalAlignment(JTextField.LEFT);

		display3 = new JFormattedTextField(shippingCostFormat);
//      display3.setValue(new Double(shippingCost));
//		display3.addPropertyChangeListener("value", this);
		display3.setEditable(true);
		display3.setHorizontalAlignment(JTextField.LEFT);

		display4 = new JFormattedTextField(itemCostFormat);
		display4.setValue(new Double(itemCost));
//		display4.addPropertyChangeListener("value", this);
		display4.setEditable(true);
		display4.setHorizontalAlignment(JTextField.LEFT);

		display5 = new JFormattedTextField("0");
		display5.setEditable(false);
		display5.setHorizontalAlignment(JTextField.CENTER);

		labelPanel.add(label1);
		labelPanel.add(display1);
		labelPanel.add(label2);
		labelPanel.add(display2);
		labelPanel.add(label3);
		labelPanel.add(display3);
		labelPanel.add(label4);
		labelPanel.add(display4);

		calculateButton = new JButton("Bereken");
		calculateButton.setPreferredSize(new Dimension(50, 50));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 5));
		add(buttonPanel, BorderLayout.CENTER);

		JPanel profitPanel = new JPanel();
		profitPanel.setLayout(new GridLayout(0, 1));
		add(profitPanel, BorderLayout.SOUTH);

		profitPanel.add(calculateButton);
		profitPanel.add(label5);
		profitPanel.add(display5);

		display1.addMouseListener(ml);
		display2.addMouseListener(ml);
		display3.addMouseListener(ml);
		display4.addMouseListener(ml);

		// Caret rechts van de invoer zetten
		display1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display1.setCaretPosition(display1.getText().length());
					}
				});
			}
		});

		// Caret rechts van de invoer zetten
		display2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display2.setCaretPosition(display2.getText().length());
					}
				});
			}
		});

		// Caret rechts van de invoer zetten
		display3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display3.setCaretPosition(display3.getText().length());
					}
				});
			}
		});

		// Caret rechts van de invoer zetten
		display4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						display4.setCaretPosition(display4.getText().length());
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
				itemCost = ((Number) display4.getValue()).doubleValue();

				double profit = ((((soldPrice) + (shippingCharge)) * 0.921) - (shippingCost) - (itemCost)) - 0.50;

				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.HALF_UP);

				display5.setValue(df.format(new Double(profit)));

				if (profit > 0) {
					display5.setForeground(new Color(0, 153, 51));
				} else if (profit == 0) {
					display5.setForeground(Color.GRAY);
				} else {
					display5.setForeground(Color.RED);
				}
			}
		});

	}

	MouseListener ml = new MouseAdapter() {
		public void mousePressed(final MouseEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JTextField tf = (JTextField) e.getSource();
					int offset = tf.viewToModel(e.getPoint());
					tf.setCaretPosition(offset);
				}
			});
		}
	};

	// Zorgen voor alleen cijfer invoer
	private void setUpFormats() {
		soldPriceFormat = NumberFormat.getNumberInstance();
		shippingChargeFormat = NumberFormat.getNumberInstance();
		shippingCostFormat = NumberFormat.getNumberInstance();
		itemCostFormat = NumberFormat.getNumberInstance();
	}
}