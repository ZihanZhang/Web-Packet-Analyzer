package edu.neu.csye6200.na;

import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

/**
 * packet holder class
 * used to hold the packet
 * @author Zihan Zhang
 * ID: 001280965
 */

public class apacket {
	int pktype;
	Packet pk;
	int headerlength;
	int datalength;
	String header;
	String data;
}
