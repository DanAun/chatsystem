/*
 * The ContactList class handles the phase of making a contactList of all online users using the chatsystem on the local network.
 * The ContactList is stored as a dictionary in contactDict that can be accessed from the outside using getContactList.
 * The contactList can also be partially accessed using getName(), getAllNames(), getIp(), getAllIps().
 * */

package cs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class ContactList {
	public static final int destPort = 24071; // The port on which all javaChatProgram instances must listen for Broadcast.
	public static final int srcPort = 24072;
    private Dictionary<String, InetAddress> contactDict;
    private String username;

    public ContactList(String username) {
    	this.username = username;
        contactDict = new Hashtable<>();
    }

    /* Creates a contactDict by sending an UDP broadcast to destPort and listening to the responses on the srcPort.
     * 
     */
    public void makeContactDict() {
    	
    	contactDict.put(username, InetAddress.getLoopbackAddress()); // Adds itself to contactDict first
        //Step 1: Send UDP broadcast to network
    		// All Connected users should reply with their username and ip
    	UdpSender sender = new UdpSender(destPort, srcPort);
    	try {
			sender.sendBroadcast(username.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Step 2: Listen to response and add replies to contactDict
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			UdpListener listener = new UdpListener(srcPort, 100);
			listener.start();
			
			// While there are packets in the stack pops them and adds them to contactList.
			while(!listener.isPacketStackEmpty()) {
				DatagramPacket packet = listener.popPacketStack();
				String username = new String(packet.getData(), 0, packet.getLength());
				InetAddress ip = packet.getAddress();
				contactDict.put(username, ip);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public void addContact(String name, InetAddress ip) {
        contactDict.put(name, ip);
    }

    public void removeContact(String name) {
        contactDict.remove(name);
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> names = new ArrayList<>();
        Enumeration<String> k = contactDict.keys();
        while (k.hasMoreElements()) {
            names.add(k.nextElement());
        }
        return names;
    }

    public ArrayList<InetAddress> getAllIps() {
        ArrayList<InetAddress> ips = new ArrayList<>();
        Enumeration<InetAddress> k = contactDict.elements();
        while (k.hasMoreElements()) {
            ips.add(k.nextElement());
        }
        return ips;
    }
    
    public Dictionary<String, InetAddress> getContactDict(){
    	return this.contactDict;
    }

    public InetAddress getIp(String name) {
        return contactDict.get(name);
    }

    public String getName(InetAddress ip) {
        Enumeration<String> k = contactDict.keys();
        while (k.hasMoreElements()) {
            String key = k.nextElement();
            if (contactDict.get(key).equals(ip)) {
                return key;
            }
        }
        return null;
    }

}
