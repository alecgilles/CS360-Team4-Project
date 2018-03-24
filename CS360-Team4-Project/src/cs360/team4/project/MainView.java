package cs360.team4.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainView implements Observer {
	private final String[] EVENT_LEVELS = {"Semi-State Events", "Regional Events", "Sectional Events"};
	private JFrame window;
	//private Tournament tournament;
	private ControlPanel controlPanel;
	private TierViewPanel tierViewPanel;
	
	public MainView(Object tournament) {
		//this.tournament = tournament;
		window = new JFrame();
		
		controlPanel = new ControlPanel();
		tierViewPanel = new TierViewPanel(controlPanel.tierSelect.getSelectedIndex());
		
		JScrollPane tierViewPanelScroller = new JScrollPane(tierViewPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		window.add(controlPanel, BorderLayout.WEST);
		window.add(tierViewPanelScroller, BorderLayout.CENTER);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		tierViewPanel.update(controlPanel.tierSelect.getSelectedIndex());
	}
	
	private class ControlPanel extends JPanel {
		JComboBox<String> tierSelect;
		
		public ControlPanel() {
			this.setLayout(new GridBagLayout());
			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.ipadx = 10;
			constraints.ipady = 5;
			constraints.insets = new Insets(10, 5, 5, 5);
			constraints.weightx = 1.0;
			constraints.weighty = 1.0;
			constraints.anchor = GridBagConstraints.PAGE_START;
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			add(new JLabel("Current Level Viewed:"), constraints);
			
			tierSelect = new JComboBox<>(EVENT_LEVELS);
			tierSelect.addActionListener(new TierSelectController());
			constraints.gridx = 1;
			this.add(tierSelect, constraints);
			
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}
	
	private class TierViewPanel extends JPanel {
		JPanel gridBagPanel;
		GridBagConstraints constraints;
		
		public TierViewPanel(int currentTier) {
			this.setLayout(new BorderLayout());
			gridBagPanel = new JPanel(new GridBagLayout());
			
			constraints = new GridBagConstraints();
			constraints.ipadx = 10;
			constraints.ipady = 5;
			constraints.insets = new Insets(0, 0, 0, 0);
			constraints.weightx = 1.0;
			constraints.weighty = 0;
			constraints.gridx = 0;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			
			this.add(gridBagPanel, BorderLayout.NORTH);
			update(currentTier);
		}
		
		public void update(int currentTier) {
			String[] hostSchools = {""};//tournament.getHostSchools(currentTier);
			
			for(int i = 0; i < hostSchools.length; i++) {
				JPanel tierEvent = new JPanel(new BorderLayout());
				tierEvent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				JButton tierHeader = new JButton(hostSchools[i]);
				tierEvent.add(tierHeader, BorderLayout.NORTH);
				
				tierEvent.add(new JLabel("test"), BorderLayout.CENTER);
				
				constraints.gridy = i;
				gridBagPanel.add(tierEvent, constraints);
			}
		}
	}
	
	private class TierSelectController implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			tierViewPanel.update(controlPanel.tierSelect.getSelectedIndex());
		}
	}
}
