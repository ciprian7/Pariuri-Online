package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.SwingPropertyChangeSupport;

import logic.Match;
import networking.Client;

public class ClientDisplay extends Display {

	private JFrame frame;
	private Client client;
	JPanel betMatches;
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public ClientDisplay() {
		title = "Pariuri Online - Client";
		client = new Client();
		initialize();
	}
	
	public ClientDisplay(Client client){
		this.client = client;
		title = "Pariuri Online - Client";
		initialize();
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	
	
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

		JFrame ticketFrame = new JFrame("My ticket");
		ticketFrame.setAlwaysOnTop(true);
		ticketFrame.setBounds(0,0,width/3,height);
		ticketFrame.setVisible(true);
		ticketFrame.setLocation(x+width,y);
		ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ticketFrame.setResizable(false);
		
		JPanel ticketContainer = new JPanel();
		ticketContainer.setBackground(Color.lightGray);
		ticketContainer.setLayout(new BorderLayout());
		
		JLabel myTicket = new JLabel("Your ticket: ");
		myTicket.setFont(mediumFont);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		bottomPanel.setBackground(Color.lightGray);
		JLabel label = new JLabel("Your sum: ");
		label.setFont(mediumFont);
		JTextField sum = new JTextField("");
		sum.setFont(mediumFont);
		sum.setColumns(10);
		JButton send = new JButton("Send");
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendRequest(2);
				client.setSum((Float.parseFloat(sum.getText())));
				client.getBetMatches().clear();
				refresh();
				
			}
		});
		
		send.setFont(mediumFont);
		bottomPanel.add(label);
		bottomPanel.add(sum);
		bottomPanel.add(send);
		ticketContainer.add(bottomPanel,BorderLayout.PAGE_END);
		
		
		
		
		betMatches = new JPanel();
		betMatches.setBounds(0, 50, width/3, height - 100);
		betMatches.setBackground(Color.white);
		betMatches.setLayout(new BoxLayout(betMatches,BoxLayout.Y_AXIS));
		
		ticketContainer.add(myTicket, BorderLayout.PAGE_START);
		ticketContainer.add(betMatches,BorderLayout.CENTER);
		ticketFrame.add(ticketContainer);
		
		
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		JButton refresh = new JButton("Refresh game list");
		refresh.setBackground(Color.green);
		refresh.setFont(smallFont);
		refresh.setMaximumSize(new Dimension(width/5,height/8));
		
		refresh.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendRequest(1);
				
			}
			
		});
		
		JButton addGame = new JButton("+Add game to ticket");
		addGame.setFont(smallFont);
		addGame.setBackground(Color.yellow);
		addGame.setMaximumSize(new Dimension(width/5,height/8));
		
		addGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				JFrame window = new JFrame ();
				window.setBounds(0,0,320,240);
				window.setAlwaysOnTop(true);
				window.setLocationRelativeTo(frame);
				window.setVisible(true);
				window.setResizable(false);
				window.setLayout(new FlowLayout());
				
				JTextField matchNubmer = new JTextField();
				matchNubmer.setColumns(10);
				matchNubmer.setBounds(width/2,10,50,50);
				
				JButton select = new JButton("Select game");
				select.setBounds(width/2-50,60,150,50);
				
	
				
				select.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Match match = client.getMatches().get(Integer.parseInt(matchNubmer.getText())-1);
						if(match.isOver()){
							window.dispose();
							window.setVisible(false);
							return;
						}
						else{
						//}
						JRadioButton teamA = new JRadioButton(match.getTeamA());
						JRadioButton teamB = new JRadioButton(match.getTeamB());
						JRadioButton x = new JRadioButton("X");

						teamA.setBounds(0,200,width/3,100);
						teamA.setFont(largeFont);
						
						x.setBounds(width/3,200,width/3,100);
						x.setFont(largeFont);
						
						teamB.setBounds(2*width/3,200,width/3,100);
						teamB.setFont(largeFont);
						
						JButton go = new JButton("Go");
						go.setBounds(width/2-50,300,50,50);
						
						teamA.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {

								teamB.setSelected(false);
								x.setSelected(false);
							}
							
						});
						
						
						teamB.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {

								teamA.setSelected(false);
								x.setSelected(false);
							}
							
						});
						
						
						x.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {

								teamA.setSelected(false);
								teamB.setSelected(false);
							}
							
						});
						
						go.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								window.dispose();
								window.setVisible(false);
								if(teamA.isSelected())
								match.setTeam(teamA.getText());
								else if (teamB.isSelected())
									match.setTeam(teamB.getText());
								else match.setTeam("draw");
								
								client.getBetMatches().add(match);
								match.setOver();
								refresh();
								
							}
						
						
						});
						
						window.add(teamA);
						window.add(x);
						window.add(teamB);
						window.add(go);
						window.repaint();
						window.pack();
						
						
			
						
					}
				}
				});
				

				
				window.add(matchNubmer);
				window.add(select);
				
			}
		});
		
		
		JButton check = new JButton("Check ticket");
		check.setFont(smallFont);
		check.setBackground(Color.cyan);
		check.setMaximumSize(new Dimension(width/5,height/8));
		
		
		JTextField ticketID = new JTextField();
		ticketID.setFont(largeFont);
		ticketID.setVisible(false);
		JButton go = new JButton("Go !");
		
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				go.setVisible(true);
				ticketID.setVisible(true);
				
				
			}
		});

		go.setFont(largeFont);
		go.setVisible(false);
		
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				go.setVisible(false);
				ticketID.setVisible(false);
				
				client.sendRequest(1);
				client.setRequestedID(Integer.parseInt(ticketID.getText()));
				client.sendRequest(3);
				
				
				JFrame window = new JFrame();
				window.setVisible(true);
				window.setAlwaysOnTop(true);
				window.setBounds(0,0,400,400);
				window.setLocationRelativeTo(frame);
				window.setLayout(new BorderLayout());
				
				JPanel cont = new JPanel();
				
				cont.setLayout(new BorderLayout());
				cont.setPreferredSize(new Dimension(400,400));
				
				JButton close = new JButton("Close");
				
				JLabel title = new JLabel("Ticket #"+ticketID.getText());
				title.setFont(largeFont);
				title.setBackground(Color.ORANGE);
				title.setHorizontalAlignment(JLabel.CENTER);
				
				close.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						window.dispose();
						window.setVisible(false);
						
					}
				});
				int state =  0; //0 - win 1-lose 2-game(s) not ready

				if(client.getRequestedTicket() == null){
					JLabel notFound = new JLabel("Sorry, this ticket does not exist!");
					notFound.setFont(mediumFont);
					notFound.setForeground(Color.red);
					notFound.setHorizontalAlignment(JLabel.CENTER);
					cont.add(notFound, BorderLayout.CENTER);
				}
				else{
					JPanel matches = new JPanel();
					matches.setLayout(new BoxLayout(matches,BoxLayout.Y_AXIS));
					matches.setBackground(Color.gray);
					for(Match match : client.getRequestedTicket().getMatches()){
						Match tmp = client.getEqMatch(match);
						JLabel l = new JLabel(match.toString()+" "+tmp.getWinningTeam()+" won Your pick: "+match.getUserTeam());
						if(!tmp.isOver()){
							JLabel lab = new JLabel(match.toString()+"This game is not over yet!");
							lab.setForeground(Color.orange);
							matches.add(lab, BorderLayout.CENTER);
							state = 2;
						}
						else{
							
						if(match.getUserTeam().equals(tmp.getWinningTeam()))
							l.setForeground(Color.green);
						else {l.setForeground(Color.red);
							state = 1;
	
						}
						matches.add(l,BorderLayout.CENTER);
						}
					}
					cont.add(matches, BorderLayout.CENTER);
				}
				JLabel bottom = new JLabel();
				if(state == 0)
					bottom.setText("Congratulaitons! You won: "+client.getRequestedTicket().getSum());
				else if(state == 1)
					bottom.setText("Sorry, you lost!");
				else bottom.setText("One ore more games aren't ready yet!");
				cont.add(bottom, BorderLayout.PAGE_END);
				window.add(cont,BorderLayout.CENTER);
				window.add(close,BorderLayout.PAGE_END);
				window.add(title, BorderLayout.PAGE_START);

				
			}
		});
		
		panel.add(refresh);
		panel.add(addGame);
		panel.add(check);
		panel.add(ticketID);
		panel.add(go);
		
		panel.invalidate();
		panel.validate();
		panel.repaint();

	}
	public void refresh(){
		
		matchesPanel.removeAll();
		for(Match match : client.getMatches())
			addMatch(match);
		matchesPanel.repaint();
		matchesPanel.invalidate();
		matchesPanel.validate();
		
		betMatches.removeAll();
		
		for(Match match : client.getBetMatches()){
			JPanel p = new JPanel();
			p.setMaximumSize(new Dimension(width/3,70));
			p.setBorder(new MatteBorder(0,0,2,0,Color.black));
			JLabel label = new JLabel(match.toString());
			label.setPreferredSize(new Dimension(width/3,70));
			label.setBackground(Color.red);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			p.setFont(smallFont);
			p.add(label);
			betMatches.add(p);
		}
		betMatches.repaint();
		betMatches.invalidate();
		betMatches.validate();
	}
}
