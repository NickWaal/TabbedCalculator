import java.awt.Component;

import javax.swing.*;

public class TabbedCalculator extends JFrame {
	JTabbedPane tabs;
	EbayPanel ebay;
	EtsyPanel etsy;
	WritePanel write;
	ReadPanel read;
	CostPanel cost;
	OverzichtsTabel overzicht;

	TabbedCalculator() {
		super("Final Fees Calculator");

		// Terminate bij wegklikken
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Tab bovenaan zetten.
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

		// Nieuwe panels aanmaken
		ebay = new EbayPanel();
		etsy = new EtsyPanel();
		write = new WritePanel();
		read = new ReadPanel();
		cost = new CostPanel();
		overzicht = new OverzichtsTabel();

		// De nieuwe panels aan de tabs toevoegen.
		tabs.addTab("Ebay", ebay);
		tabs.addTab("Etsy", etsy);
		tabs.addTab("Toevoegen", write);
		tabs.addTab("Opbrengst", read);
		tabs.addTab("Uitgaven", cost);
		tabs.addTab("Overzicht", overzicht);

		getContentPane().add(tabs);
	}
}
