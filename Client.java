package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/////////////////////////////////////////////
///Author: Yoseph Hasson; GitHub: MDbrosev///
/////////////////////////////////////////////

public class Client{
    private int myPort;
    private int recieveingPort;
    private String name;
    private String recieveingIP;
    private String message;
    private String[] nums;
    private String line;
    private String number;
    private String number2;
    private InetAddress IP;
    private Scanner scanner;
    private InetSocketAddress address;
    private Server server;
    
    void getHost() throws UnknownHostException{
        IP = InetAddress.getLocalHost();
        line = IP.toString();
        nums = line.split("/");
        number = String.valueOf(nums[0]);
	number2 = String.valueOf(nums[1]);
        System.out.println("Host Name: " + number);
        System.out.println("Host IP: " + number2 + "\n");
    }
    
    void getInput(){
        scanner = new Scanner(System.in);    
	System.out.print("Enter your username: ");
	name = scanner.nextLine();
        //name = number; //uncomment for host name to be same as username
	System.out.print("Enter a valid port for " + number + " to listen on: ");
	myPort = Integer.parseInt(scanner.nextLine());	   
	System.out.print("Enter the IP Address of the recipent: ");
	recieveingIP = scanner.nextLine();
	System.out.print("Enter the Port of the recipient: ");
	recieveingPort = Integer.parseInt(scanner.nextLine());  
    }
    
    void doOperations() throws SocketException, IOException{
    	server = new Server();
	server.port(myPort);
	server.startThread();		
	System.out.println("\nSession has started! \nType 'log-out' to end session\n");		
	address = new InetSocketAddress(recieveingIP, recieveingPort);
		
	while(true){
                        message = scanner.nextLine();			
			if(message.matches("log-out"))
			  break;			
			message = name + ": " + message;			
			server.sendMessage(address, message);
			System.out.println(message); 
		    }
	scanner.close();
	server.closeDGS();		
	System.out.println("\nSession has ended!");
    }
    
    public static void main(String[] args) throws IOException {
        Client chat = new Client();
        chat.getHost();
        chat.getInput();
        chat.doOperations();
    }
    
}
