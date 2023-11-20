package cs;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
	private static ContactList contactList;
	public static String username;
	public static void main (String[] args) throws SocketException, InterruptedException {
	    Scanner input = new Scanner(System.in);  // Create a Scanner object
	    System.out.print("Enter username: ");
	    username = input.nextLine();  // Read user input
	    input.close();
	
		System.out.println("Creating contactList object from username: " + username + "...");
		contactList = new ContactList(username);
		System.out.println("Created contactList object susccesfully");
		System.out.println("Running makeContactDict...");
		contactList.makeContactDict();
		System.out.println("Sleeping for 2 seconds...");
		Thread.sleep(2000);
		System.out.println("Accessing contactDict...");
		System.out.println(contactList.getContactDict().toString());
		
		System.out.println("Now online");
		System.out.println("Listening for other users...");
		OnJoinHandler onJoinHandlerThread = new OnJoinHandler(contactList);
		onJoinHandlerThread.setDaemon(false);
		onJoinHandlerThread.start();
	}
}
