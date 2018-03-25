package cs360.team4.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import events.Event;
import events.Regional;
import events.Sectional;
import events.SemiState;
import tables.EventTable;
import tables.SchoolTable;

public class MainView implements Observer {
	private final String[] EVENT_LEVELS = {"Semi-State Events", "Regional Events", "Sectional Events"};
	private JFrame window;
	private EventTable allEvents;
	private ControlPanel controlPanel;
	private TierViewPanel tierViewPanel;
	
	public MainView(EventTable allEvents) {
		this.allEvents = allEvents;
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
		JPanel gridPanel;
		
		public TierViewPanel(int currentTier) {
			this.setLayout(new BorderLayout());
			update(currentTier);
		}
		
		public void update(int currentTier) {
			this.removeAll();
			EventTable hostSchools = null;
			
			switch(currentTier) {
				case 0:
					hostSchools = allEvents.getSemiStates();
					break;
				case 1:
					hostSchools = allEvents.getRegionals();
					break;
				case 2:
					hostSchools = allEvents.getSectionals();
			}
			
			gridPanel = new JPanel(new GridLayout(hostSchools.size(), 1));
			
			for(int i = 0; i < hostSchools.size(); i++) {
				Event event = hostSchools.getByIndex(i);
				
				JPanel tierEvent = new JPanel(new BorderLayout());
				tierEvent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				JButton tierHeader = new JButton(event.getHost().getName());
				tierEvent.add(tierHeader, BorderLayout.NORTH);
				
				tierEvent.add(getSchoolListPane(event, currentTier), BorderLayout.CENTER);
				
				gridPanel.add(tierEvent);
			}
			
			this.add(gridPanel, BorderLayout.NORTH);
			this.repaint();
			this.revalidate();
		}
		
		private JTextArea getSchoolListPane(Event event, int currentTier) {
			JTextArea textArea;
			String schoolList = "";

			if(currentTier == 0) {
				EventTable regionals = ((SemiState) event).getRegionals();
				for(int i = 0; i < regionals.size(); i++) {
					EventTable sectionals = ((Regional) regionals.getByIndex(i)).getSectionals();
					for(int j = 0; j < sectionals.size(); j++) {
						SchoolTable schools = ((Sectional)sectionals.getByIndex(j)).getSchools();
						for(int k = 0; k < schools.size(); k++) {
							schoolList += schools.getByIndex(k).getName()+", ";
						}
					}
				}
			} else if(currentTier == 1) {
				EventTable sectionals = ((Regional) event).getSectionals();
				for(int i = 0; i < sectionals.size(); i++) {
					SchoolTable schools = ((Sectional)sectionals.getByIndex(i)).getSchools();
					for(int j = 0; j < schools.size(); j++) {
						schoolList += schools.getByIndex(j).getName()+", ";
					}
				}
			} else {
				SchoolTable schools = ((Sectional) event).getSchools();
				for(int j = 0; j < schools.size(); j++) {
					schoolList += schools.getByIndex(j).getName()+", ";
				}
			}
			
			if(schoolList.length() > 0) {
				schoolList = schoolList.substring(0, schoolList.length() - 2);
			}
			
			textArea = new JTextArea(schoolList);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			
			return textArea;
		}
	}
	
	private class TierSelectController implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			tierViewPanel.update(controlPanel.tierSelect.getSelectedIndex());
		}
	}
}
