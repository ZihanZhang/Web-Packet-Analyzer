package edu.neu.csye6200.na;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * UI class
 * @author Zihan Zhang
 * ID: 001280965
 */

public class NAUI extends CAApp{
	protected JButton startBtn = null;
	protected JButton stopBtn = null;
	protected JButton pauseBtn = null;
	protected JButton quitBtn = null;
	protected JButton continueBtn=null;
	protected JButton loadfileBtn=null;
	protected JButton quitdelBtn=null;
//    private CACanvas caPanel = null;
	private JPanel northPanel = null;
    private JPanel westPanel=null;
    private JPanel southPanel=null;
    private JPanel eastPanel=null;
    private JScrollPane centerPanel=null;
    private Thread startrdthread;
    private Thread startuithread;
    private Thread loadfilethread;
    
    
    JCheckBox jcb4 = new JCheckBox("Save to File");
    JCheckBox jcb1 = new JCheckBox("TCP/IP Protocol");
    JCheckBox jcb2 = new JCheckBox("ARP Protocol");
    JCheckBox jcb3 = new JCheckBox("UDP Protocol");
    
	JTextField jinterval = new JTextField();
	
	JTextField showtcpnum = new JTextField();
	
	JTextField showudpnum = new JTextField();
	
	JTextField showarpnum = new JTextField();
	
	JTextField grabnum = new JTextField();
    
    int tcpnum=0;
    
    int udpnum=0;
    
    int arpnum=0;
    
    apacket apkt = new apacket();
    
    DataReader DR = new DataReader(jcb1,jcb2,jcb3,jinterval,jcb4,grabnum);
    
    JTextPane textArea = new JTextPane();
    
    CACanvas ca = new CACanvas(DR,textArea,tcpnum,udpnum,arpnum,showtcpnum,showudpnum,showarpnum,jinterval,grabnum);
    
    LoadFile lf = new LoadFile(jcb1,jcb2,jcb3,jinterval,textArea);
   
    
    public NAUI(){
    	frame.setSize(1000, 700);
		frame.setTitle("Network Analytics");
		
    //	caPanel = new CACanvas();
		frame.getContentPane().add(getNorthPanel(),BorderLayout.NORTH);
	//	frame.add(caPanel, BorderLayout.CENTER);
		frame.getContentPane().add(getWestPanel(),BorderLayout.WEST);
		frame.getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		frame.getContentPane().add(getEastPanel(), BorderLayout.EAST);
		frame.getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
		frame.setVisible(true);
    }
    
    public JPanel getEastPanel(){
    	eastPanel = new JPanel();
    	eastPanel.setLayout(new GridLayout(20,1));
    	JLabel jl1 = new JLabel("Current TCP/IP packet:");
    	JLabel jl2 = new JLabel("Current UDP packet:");
    	JLabel jl3 = new JLabel("Current ARP packet:");
    	eastPanel.add(jl1);
    	eastPanel.add(showtcpnum);
    	showtcpnum.setText(Integer.toString(tcpnum));
    	eastPanel.add(jl2);
    	eastPanel.add(showudpnum);
    	showudpnum.setText(Integer.toString(udpnum));
    	eastPanel.add(jl3);
    	eastPanel.add(showarpnum);
    	showarpnum.setText(Integer.toString(arpnum));
    	return eastPanel;
    }
    
    public JPanel getSouthPanel(){
    	southPanel = new JPanel();
    	JLabel jl1 = new JLabel("Author: Zihan Zhang");  
    	southPanel.add(jl1);
    	return southPanel;
    }
    
    public JScrollPane getCenterPanel(){
    	centerPanel = new JScrollPane(textArea);
    	centerPanel.setBackground(Color.black);    	
    	return centerPanel;
    }
    
    public JPanel getWestPanel(){
    	westPanel = new JPanel();
    	westPanel.setLayout(new GridLayout(20,1));
    	westPanel.setBackground(Color.gray);
    	JLabel jl1 = new JLabel("Please Select Protocol");  	
    	JLabel jl3 = new JLabel("(In Seconds)");  	
    	jcb1.setForeground(Color.red);
    	jcb2.setForeground(Color.blue);
    	jcb3.setForeground(Color.green);
    	JLabel jl2 = new JLabel("Please input the interval:");
    	JLabel jl4 = new JLabel("Please input the number of packets");
    	jinterval.setText("1");
    	westPanel.add(jcb4);
    	westPanel.add(jl1);
    	westPanel.add(jcb1);
    	westPanel.add(jcb2);
    	westPanel.add(jcb3);
    	westPanel.add(jl4);
    	westPanel.add(grabnum);
    	westPanel.add(jl2);
    	westPanel.add(jl3);
    	westPanel.add(jinterval);
    	return westPanel;
    }
    
    public JPanel getNorthPanel(){
    	northPanel = new JPanel();
    	northPanel.setLayout(new FlowLayout());
    	
    	startBtn = new JButton("Start Capture");
    	startBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				startrdthread = new Thread(DR);
				startrdthread.start();
				startuithread = new Thread(ca);
				startuithread.start();
			}
    	});
    	northPanel.add(startBtn);
    	
    	loadfileBtn = new JButton("Load file");
    	loadfileBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				loadfilethread = new Thread(lf);
				loadfilethread.start();
			}
    		
    	});
    	northPanel.add(loadfileBtn);
    	
    	pauseBtn = new JButton("Pause");
    	pauseBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startrdthread.suspend();
				startuithread.suspend();	
			}  		
    	});
    	northPanel.add(pauseBtn);
    	
    	
    	continueBtn = new JButton("Continue"); 
    	continueBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startrdthread.resume();
				startuithread.resume();
			}
    		
    	});
    	northPanel.add(continueBtn);
    	
    	stopBtn = new JButton("Clear"); 
    	stopBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
    		
    	});
    	northPanel.add(stopBtn);
    	
    	quitBtn = new JButton("Quit");
    	quitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
    		
    	});
    	northPanel.add(quitBtn);
    	
    	quitBtn = new JButton("Delete File");
    	quitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File("PacketRecords");
				file.delete();
			}
    		
    	});
    	northPanel.add(quitBtn);

    	return northPanel;
    }
    @Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == startBtn)
			System.out.println("Start pressed");
		else if (ae.getSource() == stopBtn)
			System.out.println("Stop pressed");
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {	
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
