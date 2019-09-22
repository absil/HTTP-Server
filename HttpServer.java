import java.net.*;
import java.io.*;
import java.util.*;

class HttpServer{
	public static void main(String args[]){
		
		try{		
			ServerSocket ss = new ServerSocket(8080);

			while(true){
				System.out.println("Waiting for connection...");
				Socket client = ss.accept();
				System.out.println("Connection made with " + client.getInetAddress().getHostAddress());
				HttpServerSession thread = new HttpServerSession(client);
				thread.start();
			}
		}
		catch(Exception e){
		}		

}
}
		
