import java.net.*;
import java.io.*;
import java.util.*;

class HttpServerSession extends Thread {
	
	private Socket socket;

	public void run(){

		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String request = reader.readLine();
			String[] parts = request.split(" ");
			String fileName;
			if(parts[1].length() <= 1)
				fileName = "index.html";
			
			if(parts.length == 3 && parts[0].compareTo("GET") == 0 && parts[1].length() > 1){	
				fileName = parts[1].substring(1);			
				System.out.println(fileName);
			}
			else if(parts.length == 3 && parts[0].compareTo("GET") == 0 && parts[1].length() <= 1){
				fileName = "index.html";
			}
			else
				return;
						
			while(true){
				String line = reader.readLine();
				if(line == null){
					reader.close();
					break;
				}
				if(line.compareTo("") == 0)
					break;
			}			
			BufferedOutputStream bosm = new BufferedOutputStream(socket.getOutputStream());
			
			try{
				FileInputStream file = new FileInputStream(new File(fileName));

				println(bosm, "HTTP/1.1 200 OK");
				println(bosm, "");

				int buffSize = 1024;
				byte[] buffer = new byte[buffSize];

				while(true){				
					int rec = file.read(buffer);
					if(rec == -1) break;
					bosm.write(buffer, 0, rec);					
					sleep(1000);
				}
				bosm.flush();
				bosm.close();
				file.close();
			}
			catch(Exception e){
				println(bosm, "HTTP/1.1 404 File Not Found ");
				println(bosm, "");
				System.err.println("404: File not found");
			}
		}
		catch(Exception e){
		}
	}

	public HttpServerSession(Socket s){
		socket = s;
	}	

	public void println(BufferedOutputStream bos, String s) throws IOException {
		String news = s + "\r\n";
		byte[] array = news.getBytes();
		for(int i=0; i<array.length; i++)
			bos.write(array[i]);
		return;
	}
		
}

	
