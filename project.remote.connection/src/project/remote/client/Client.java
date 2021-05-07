package project.remote.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.JsonObject;

import project.remote.common.service.MessageEncode;
import project.remote.common.service.MessageField;
import project.remote.common.service.NetMessage;

/*
 * TODO:
 * 
 * 90. Mechanism: Unexpected disconnection from Server (time out and ??)
 */

public class Client {
	
	public static void main(String[] args) throws IOException {
		
		final String serverIpAddress = "192.168.66.144";
		final String localhost = "localhost";
		final int serverPort = 5056;
		try {
			Scanner scn = new Scanner(System.in);

			// getting localhost ip
			InetAddress ip = InetAddress.getByName(localhost);
			
			// establish the connection with server ip address and port
			Socket s = new Socket(serverIpAddress, serverPort);

			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			// the following loop performs the exchange of
			// information between client and client handler
//			while (true) {
//				System.out.println(dis.readUTF());
//				String tosend = scn.nextLine();
//				dos.writeUTF(tosend);
//
//				// If client sends exit,close this connection
//				// and then break from the while loop
//				if (tosend.equals("Exit")) {
//					System.out.println("Closing this connection : " + s);
//					s.close();
//					System.out.println("Connection closed");
//					break;
//				}
//
//				// printing date or time as requested by client
//				String received = dis.readUTF();
//				System.out.println(received);
//			}
//			System.out.println(dis.readUTF());
			// square method
			JsonObject jsonRequest = MessageEncode.encodeSquare(null, null);
			jsonRequest.addProperty(MessageField.PARAMETERS_OBJ_STRING, 1.5);
			String tosend = NetMessage.netMessageEncode(jsonRequest);
			dos.writeUTF(tosend);
			
			String received = dis.readUTF();
			System.out.println(received);
			
			// getSystemInfo method
			jsonRequest = MessageEncode.encodeSystemInfo(null, null);
			tosend = NetMessage.netMessageEncode(jsonRequest);
			dos.writeUTF(tosend);
			
			received = dis.readUTF();
			System.out.println(received);
			
			// getDateInfo method
			jsonRequest = MessageEncode.encodeDateInfo(null, null);
			tosend = NetMessage.netMessageEncode(jsonRequest);
			dos.writeUTF(tosend);
			
			received = dis.readUTF();
			System.out.println(received);	
			
			dos.writeUTF("Exit");
			s.close();
			
			// closing resources
			scn.close();
			dis.close();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}