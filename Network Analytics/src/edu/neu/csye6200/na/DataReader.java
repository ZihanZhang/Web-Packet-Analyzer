package edu.neu.csye6200.na;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * Data Reader class
 * used to read raw data and create observation instances
 * @author Zihan Zhang
 * ID: 001280965
 */

public class DataReader implements Runnable{
	apacket apkt = new apacket();
	JCheckBox tcpcheckbox;
	JCheckBox udpcheckbox;
	JCheckBox arpcheckbox;
	JTextField jinterval;
	JCheckBox savecheckbox;
	JTextField grabnum;
	int interval;
	int isrun;
	int num=0;
	int tnum;
//	int count=0;
	
	public DataReader(JCheckBox tcpcheckbox,JCheckBox udpcheckbox, JCheckBox arpcheckbox, JTextField jinterval,JCheckBox savecheckbox,JTextField grabnum){
		this.tcpcheckbox=tcpcheckbox;
		this.udpcheckbox=udpcheckbox;
		this.arpcheckbox=arpcheckbox;
		this.jinterval=jinterval;
		this.savecheckbox=savecheckbox;
		this.grabnum=grabnum;
	}
	
	public void run(){                  //return shouldn't be put in try/catch block if used
		try{
			NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			int index = 0;
			JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
			JpcapWriter writer = JpcapWriter.openDumpFile(captor, "PacketRecords");
	//		captor.setFilter("ip and tcp", true);
			for(;;){
				tnum=Integer.parseInt(grabnum.getText());
				if(num==tnum){
					writer.close();
		//			captor.close();
					break;
				}
				interval=Integer.parseInt(jinterval.getText());
				Packet pk = captor.getPacket();
				if(pk == null)continue;
				else if(pk instanceof TCPPacket && tcpcheckbox.isSelected()){ 
					tcprun(pk,writer);
				}
				else if(pk instanceof ARPPacket && arpcheckbox.isSelected()){
					arprun(pk,writer);
				}
				else if(pk instanceof UDPPacket && udpcheckbox.isSelected()){
					udprun(pk,writer);
				}
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void tcprun(Packet pk,JpcapWriter writer) throws AWTException{
		//				apkt=new apacket();					
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
		if(savecheckbox.isSelected()){
			writer.writePacket(pk);
		}
		num++;
	}
	private void arprun(Packet pk,JpcapWriter writer) throws AWTException{
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
		if(savecheckbox.isSelected()){
			writer.writePacket(pk);
		}
		num++;
	}
	private void udprun(Packet pk,JpcapWriter writer) throws AWTException{
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
		if(savecheckbox.isSelected()){
			writer.writePacket(pk);
		}
		num++;
	}
}
