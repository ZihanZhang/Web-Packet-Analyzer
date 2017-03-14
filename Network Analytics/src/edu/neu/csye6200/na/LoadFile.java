package edu.neu.csye6200.na;

import java.awt.AWTException;

import java.awt.Color;
import java.awt.Robot;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import jpcap.JpcapCaptor;
import jpcap.packet.ARPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * Load file class
 * @author Zihan Zhang
 * ID: 001280965
 */

public class LoadFile implements Runnable{
	
	apacket apkt = new apacket();
	JCheckBox tcpcheckbox;
	JCheckBox udpcheckbox;
	JCheckBox arpcheckbox;
	JTextField jinterval;
	JTextPane jtp;
	int interval;
	Document doc;
	String pkg;
	
	public LoadFile(JCheckBox tcpcheckbox,JCheckBox udpcheckbox, JCheckBox arpcheckbox, JTextField jinterval,JTextPane jtp){
		this.tcpcheckbox=tcpcheckbox;
		this.udpcheckbox=udpcheckbox;
		this.arpcheckbox=arpcheckbox;
		this.jinterval=jinterval;
		this.jtp=jtp;
	}

	@Override
	public void run() {
		try {
			JpcapCaptor captor = JpcapCaptor.openFile("PacketRecords");
			while(true){
				interval=Integer.parseInt(jinterval.getText());
				Packet pk = captor.getPacket();
				if(pk == null)continue;
				else if(pk instanceof TCPPacket && tcpcheckbox.isSelected()){ 
	//				apkt=new apacket();
					tcprun(pk);
				}
				else if(pk instanceof ARPPacket && arpcheckbox.isSelected()){
					arprun(pk);
				}
				else if(pk instanceof UDPPacket && udpcheckbox.isSelected()){
					udprun(pk);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void tcprun(Packet pk) throws AWTException, BadLocationException{
		pk=(TCPPacket)pk;
		Robot r = new Robot();
		r.delay(interval*1000);
		apkt.pk=pk;             
		apkt.pktype=0;         //list of object must new twice
		int headLength = pk.header.length;
		int dataLength = pk.data.length;
		String header = "";
		for (int i = 0; i < headLength; i++) {
			header += Byte.toString(pk.header[i]);
		}
		apkt.header=header;
		String data = "";
		for (int i = 0; i < dataLength; i++) {
			data += Byte.toString(pk.data[i]);
		}
		apkt.data=data;
//				count++;
//				if(count>19)break;
		pkg="Header :"+ apkt.header+ "\n" + "Data :" + apkt.data + "\n"+"\n";
		doc = jtp.getDocument();
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.RED);
		doc.insertString(doc.getLength(), pkg, attrset);
	}
	private void arprun(Packet pk) throws AWTException, BadLocationException{
		pk=(ARPPacket)pk;
		Robot r = new Robot();
		r.delay(interval*1000);
		apkt.pk=pk;             
		apkt.pktype=1;
		int headLength = pk.header.length;
		int dataLength = pk.data.length;
		String header = "";
		for (int i = 0; i < headLength; i++) {
			header += Byte.toString(pk.header[i]);
		}
		apkt.header=header;
		String data = "";
		for (int i = 0; i < dataLength; i++) {
			data += Byte.toString(pk.data[i]);
		}
		apkt.data=data;
		pkg="Header :"+ apkt.header+ "\n" + "Data :" + apkt.data + "\n"+"\n";
		doc = jtp.getDocument();
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.GREEN);
		doc.insertString(doc.getLength(), pkg, attrset);
	}
	private void udprun(Packet pk) throws AWTException, BadLocationException{
		pk=(UDPPacket)pk;
		Robot r = new Robot();
		r.delay(interval*1000);
		apkt.pk=pk;             
		apkt.pktype=2;
		int headLength = pk.header.length;
		int dataLength = pk.data.length;
		String header = "";
		for (int i = 0; i < headLength; i++) {
			header += Byte.toString(pk.header[i]);
		}
		apkt.header=header;
		String data = "";
		for (int i = 0; i < dataLength; i++) {
			data += Byte.toString(pk.data[i]);
		}
		apkt.data=data;
		pkg="Header :"+ apkt.header+ "\n" + "Data :" + apkt.data + "\n"+"\n";
		doc = jtp.getDocument();
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.BLUE);
		doc.insertString(doc.getLength(), pkg, attrset);
	}
	
}
