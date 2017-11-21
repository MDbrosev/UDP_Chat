package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class Server extends Thread{
    private DatagramSocket dgSocket;
    private boolean setbool;
    private byte[] arr = new byte[1024];
    private DatagramPacket dgPacket;
    private String message;
    private Thread thread;
    
    public void port(int port) throws SocketException{
        dgSocket = new DatagramSocket(port);
    }
	
    public void startThread(){
	thread = new Thread(this);
	thread.start();
    }
	
    public void closeDGS(){
	setbool = false;
	dgSocket.close();
    }

    @Override
    public void run(){
	dgPacket = new DatagramPacket(arr, arr.length);		
	setbool = true;            
        while(setbool){
	    try{
		    dgSocket.receive(dgPacket);
		    message = new String(arr, 0, dgPacket.getLength());
		    System.out.println(message);
	    }catch(IOException e){
		    break;
	    }
	}
    }
	
    public void sendMessage(SocketAddress address, String message) throws IOException{
	arr = message.getBytes();
	dgPacket = new DatagramPacket(arr, arr.length);
	dgPacket.setSocketAddress(address);
	dgSocket.send(dgPacket);
    }
}
