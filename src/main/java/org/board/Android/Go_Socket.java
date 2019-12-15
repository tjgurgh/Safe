package org.board.Android;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Go_Socket {	
	public static void main(String[] args) throws IOException {  // �����Լ�, IOException
		ServerSocket serverSocket = null; //�������� ����
		Socket clientSocket = null; // ���������� ������ ��Ʈ�� Ÿ��� ��� ip�� ������ �� �ִ�.
		PrintWriter out = null;  // String Ÿ���� ���ڸ� ������ �ִ� �Լ�.
		BufferedReader in = null;//stream Ÿ���� ���ڸ� �о ������ �� �ִ� �Լ�.

		serverSocket = new ServerSocket(7597); //�������� ����

		try {
			System.out.println("���� �����");
			// ���� ������ ����� ������ ��ٸ���.
			clientSocket = serverSocket.accept(); //Ŭ���̾�Ʈ�κ��� �����Ͱ� ���°��� �����Ѵ�.
			System.out.println("Ŭ���̾�Ʈ ����");
	
			// Ŭ���̾�Ʈ�� ���� �����͸� �޴´�.
			out = new PrintWriter(clientSocket.getOutputStream(), true); //String Ÿ���� stream ���·� ��ȯ�Ͽ� �����Ѵٴ� ��.
			                                                                                                            
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //���Ͽ��� �ѿ��� stream ������ ���ڸ� ���� �� �о� �� bufferstream ���·� in �� ����.
	
			while (true) {
				String inputLine = null; //in ���� �޾Ƶ��� �����͸� ������ string ����
				inputLine = in.readLine(); //in�� ����� �����͸� String ���·� ��ȯ �� �о�� String�� ����
				System.out.println("Ŭ���̾�Ʈ�� ���� ���� ���ڿ�:" + inputLine); //����Ȱ� �ܼ� ���
				out.println(inputLine); //���ƿ°��� �ٽ� �ǵ��� ������. //String�� stream���� ��ȯ�Ǿ� ���۵�.
				if (inputLine.equals("quit")) //���� ���� ���� quit �ϰ�� ����
				break;
			}
				out.close();
				in.close();
				clientSocket.close();
				serverSocket.close();  //���� ������ �ݾ��ش�.
				System.out.println("�������");
			} catch (Exception e) {
				e.printStackTrace();
		}
	}
}