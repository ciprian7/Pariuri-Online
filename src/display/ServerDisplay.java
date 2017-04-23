package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.sun.xml.internal.ws.api.Component;

import logic.Match;
import logic.Ticket;
import networking.Server;

public class ServerDisplay extends Display {
	JPanel ticketContainer;
	private Server server;

	public ServerDisplay(Server server) {
		this.server = server;
		title = "Pariuri Online - Server";
		initialize();
	}
	
	public ServerDisplay(){
		title = "Pariuri Online - Server";
		initialize();	
	}

	public void addMatch(Match match){
		JPanel matchPanel = new JPanel();
		matchPanel.setPreferredSize(new Dimension(width - 50,height/6));
		matchPanel.setMaximumSize(new Dimension(width - 50,height/6));
		matchPanel.setLayout(new BorderLayout());
		
		if(match.isOver())
			matchPanel.setBackground(Color.gray);
		else matchPanel.setBackground(Color.green);
		
		
		matchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel teamAPanel = new JPanel();
		teamAPanel.setLayout(new BorderLayout());
		teamAPanel.setBounds(0,0,width/2 - 50,height/6);
		teamAPanel.setPreferredSize(new Dimension(width/2 - 50 , height/6));
		teamAPanel.setBackground(matchPanel.getBackground());
		
		JLabel teamA = new JLabel(match.getTeamA());
		teamA.setFont(mediumFont);
		teamA.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel scoreA = new JLabel(""+match.getScoreA());
		scoreA.setFont(largeFont);
		
		JLabel stakeA = new JLabel(""+match.getStakeA());
		stakeA.setFont(smallFont);
		
		
		teamAPanel.add(stakeA,BorderLayout.LINE_START);
		teamAPanel.add(teamA, BorderLayout.CENTER);
		teamAPanel.add(scoreA,BorderLayout.LINE_END);
		

		
		JPanel teamBPanel = new JPanel();
		teamBPanel.setLayout(new BorderLayout());
		teamBPanel.setBounds(0,0,width/2 - 50,height/6);
		teamBPanel.setPreferredSize(new Dimension(width/2 - 50 , height/6));
		teamBPanel.setBackground(matchPanel.getBackground());
		
		JLabel teamB = new JLabel(match.getTeamB());
		teamB.setFont(mediumFont);
		teamB.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel scoreB = new JLabel(""+match.getScoreB());
		scoreB.setFont(largeFont);
		
		JLabel stakeB = new JLabel(""+match.getStakeB());
		stakeB.setFont(smallFont);
		
		teamBPanel.add(scoreB,BorderLayout.LINE_START);
		teamBPanel.add(teamB,BorderLayout.CENTER);
		teamBPanel.add(stakeB,BorderLayout.LINE_END);
		
		JLabel middle = new JLabel("-");
		middle.setFont(largeFont);
		middle.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel date = new JLabel(match.getHour()+":"+match.getMinute());
		date.setFont(mediumFont);
		date.setHorizontalAlignment(JLabel.CENTER);
		
		matchPanel.add(date, BorderLayout.PAGE_START);
		matchPanel.add(teamAPanel, BorderLayout.LINE_START);
		matchPanel.add(middle, BorderLayout.CENTER);
		matchPanel.add(teamBPanel, BorderLayout.LINE_END);
		matchesPanel.add(matchPanel);
		
	}
	
	protected void initialize() {
		super.initialize();
		
		frame.setLocation(100, 200);
		
		JFrame ticketFrame = new JFrame("Ticket Database");
		ticketFrame.setAlwaysOnTop(true);
		ticketFrame.setBounds(0,0,width/3,height);
		ticketFrame.setVisible(true);
		ticketFrame.setLocation(frame.getX()+width,frame.getY());
		ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ticketFrame.setResizable(false);
		
		ticketContainer = new JPanel();
		ticketContainer.setBackground(Color.lightGray);
		ticketContainer.setLayout(new BoxLayout(ticketContainer,BoxLayout.Y_AXIS));
		
		ticketFrame.add(ticketContainer);
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		
		JButton removeGame = new JButton("Remove game");
		removeGame.setBackground(Color.red);
		removeGame.setFont(largeFont);
		removeGame.setPreferredSize(new Dimension(width,height));
		
		removeGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();
				window.setAlwaysOnTop(true);
				window.setLocationRelativeTo(frame);
				window.setSize(240, 120);
				window.setVisible(true);
				window.setLayout(new FlowLayout());
					
				JTextField text = new JTextField();
				text.setMaximumSize(new Dimension(width/4,20));
				text.setColumns(5);
				
				JButton button = new JButton("Remove!");
				button.setPreferredSize(new Dimension(100,20));
				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						server.getMatches().remove(server.getMatches().get(Integer.parseInt(text.getText())-1));
						refresh();

						window.dispose();
					}
					
				});
				window.add(text);
				window.add(button);
					
				
			}
		});
		

		
		JButton setScore = new JButton("Set Score");
		setScore.setBackground(Color.yellow);
		setScore.setFont(largeFont);
		
		setScore.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();
				window.setVisible(true);
				window.setAlwaysOnTop(true);
				window.setLocationRelativeTo(frame);
				window.setSize(240, 120);
				window.setLayout(new FlowLayout());
				JTextField matchNumber = new JTextField();
				matchNumber.setColumns(5);
				JTextField scoreA = new JTextField();
				scoreA.setColumns(5);
				JTextField scoreB = new JTextField();
				scoreB.setColumns(5);
				JButton button = new JButton("Set score!");
				
				JRadioButton radio = new JRadioButton("Match ended");
				
				button.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e){
						server.getMatches().get(Integer.parseInt(matchNumber.getText())- 1).setScore(Integer.parseInt(scoreA.getText()), Integer.parseInt(scoreB.getText()));
						if(radio.isSelected())
							server.getMatches().get(Integer.parseInt(matchNumber.getText()) - 1).setOver();
						window.dispose();
						window.setVisible(false);
						refresh();
					}
				});
				
				window.add(matchNumber);
				window.add(scoreA);
				window.add(scoreB);
				window.add(radio);
				window.add(button);
				
			}
			
		});
		setScore.setPreferredSize(new Dimension(width,height));
		
		JButton addGame = new JButton("Add new game");
		addGame.setPreferredSize(new Dimension(width,height));
		addGame.setBackground(Color.green);
		addGame.setFont(largeFont);
		
		addGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();
				window.setAlwaysOnTop(true);
				window.setBounds(0,0,320,240);
				window.setLocationRelativeTo(frame);			
				window.setVisible(true);
				window.setLayout(new FlowLayout());
				
				JTextField teamA = new JTextField("teamA");
				teamA.setFont(mediumFont);
				JTextField teamB = new JTextField("teamB");
				teamB.setFont(mediumFont);
				JTextField stakeA = new JTextField("0.0");
				stakeA.setFont(mediumFont);
				JTextField stakeB = new JTextField("0.0");
				stakeB.setFont(mediumFont);
				JTextField hour = new JTextField("21");
				hour.setFont(mediumFont);
				JTextField minute = new JTextField("45");
				minute.setFont(mediumFont);
				JButton button = new JButton("Add!");
				button.setFont(mediumFont);
				
				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						server.getMatches().add(new Match(	teamA.getText(),
															Float.parseFloat(stakeA.getText()),
															teamB.getText(),
															Float.parseFloat(stakeB.getText()),
															Integer.parseInt(hour.getText()),
															Integer.parseInt(minute.getText())));
						refresh();
						window.dispose();
						window.setVisible(false);
						
					}
					
				});
				
				window.add(teamA);
				window.add(stakeA);
				window.add(teamB);
				window.add(stakeB);
				window.add(hour);
				window.add(minute);
				window.add(button);
				
			}
			
		});
		
		panel.add(removeGame);
		panel.add(setScore);
		panel.add(addGame);
	}
	public void refresh(){
		matchesPanel.removeAll();
		for(Match match : server.getMatches())
			addMatch(match);
		matchesPanel.repaint();
		matchesPanel.invalidate();
		matchesPanel.validate();
		ticketContainer.removeAll();
		
		for(Ticket ticket : server.getTickets()){
			JPanel p = new JPanel();
			p.setMaximumSize(new Dimension(width/3,20));
			p.setBorder(new MatteBorder(0,0,2,0,Color.black));
			JPanel pn = new JPanel();
			pn.setMaximumSize(new Dimension(width/3,20));
			JTextArea label = new JTextArea(ticket.toString());
			label.setEditable(false);
			label.setMaximumSize(new Dimension(width/3,70));
			JScrollPane scroll = new JScrollPane(pn);
			scroll.setMinimumSize(new Dimension(30,20));
			p.setFont(smallFont);
			pn.add(label);
			p.add(scroll);
			ticketContainer.add(scroll);
			
		}
		ticketContainer.repaint();
		ticketContainer.invalidate();
		ticketContainer.validate();
	}
	
}
