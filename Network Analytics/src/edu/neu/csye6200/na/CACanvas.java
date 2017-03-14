package edu.neu.csye6200.na;

import java.util.Timer;
import java.util.logging.Logger;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Output Results
 * @author Zihan Zhang
 * ID: 001280965
 */

public class CACanvas implements Runnable{
	
	DataReader t;
	JTextPane a;
	apacket apkt;
	int tcpnum;
	int udpnum;
	int arpnum;
	JTextField jft1;
	JTextField jft2;
	JTextField jft3;
	String pkg;
	Document doc;
	JTextField jinterval;
	int interval;
	int num=0;
	JTextField grabnum;
	int tnum;
	
	public CACanvas(DataReader t,JTextPane a,int tcpnum,int udpnum,int arpnum,JTextField jft1,JTextField jft2,JTextField jft3,JTextField jinterval,JTextField grabnum){
		this.t=t;
		this.a=a;
		this.tcpnum=tcpnum;
		this.udpnum=udpnum;
		this.arpnum=arpnum;
		this.jft1=jft1;
		this.jft2=jft2;
		this.jft3=jft3;
		this.jinterval=jinterval;
		this.grabnum=grabnum;
	}

	@Override
	public void run() {
		for(int i=0;i<10000;i++){
			tnum=Integer.parseInt(grabnum.getText());
			if(num==tnum)break;
			try {
				interval=Integer.parseInt(jinterval.getText());
				Robot r   =  new Robot();
				r.delay(interval*1000);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

			apkt = t.apkt;
			pkg="Header :"+ apkt.header+ "\n" + "Data :" + apkt.data + "\n"+"\n";
	//		a.append(apkt.header+"\n");
			doc=a.getDocument();
			SimpleAttributeSet attrset=new SimpleAttributeSet();
			if(t.apkt.pktype==0){
				StyleConstants.setForeground(attrset, Color.RED);
				tcpnum++;
				jft1.setText(Integer.toString(tcpnum));
				num++;
			}
			else if(t.apkt.pktype==1){
				StyleConstants.setForeground(attrset, Color.GREEN);
				arpnum++;
				jft2.setText(Integer.toString(arpnum));
				num++;
			}
			else if(t.apkt.pktype==2){
				StyleConstants.setForeground(attrset, Color.BLUE);
				udpnum++;
				jft3.setText(Integer.toString(udpnum));
				num++;
			}
			try {
				doc.insertString(doc.getLength(), pkg, attrset);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
/*	apacket apkt;
	JTextArea textArea;
	public CACanvas(apacket apkt, JTextArea textArea){
		this.apkt=apkt;
		this.textArea=textArea;
	}
	@Override
	public void run() {
		textArea.append(apkt.header);
		textArea.paintImmediately(textArea.getBounds());	
	}
*/
	
}