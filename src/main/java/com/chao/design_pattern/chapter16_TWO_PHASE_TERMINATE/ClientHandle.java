package com.chao.design_pattern.chapter16_TWO_PHASE_TERMINATE;

import java.io.*;
import java.net.Socket;

public class ClientHandle implements Runnable {
	private Socket socket;

	public ClientHandle(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (OutputStream outputStream = socket.getOutputStream();
			 InputStream inputStream = socket.getInputStream();
			 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			 PrintWriter pw = new PrintWriter(outputStream)) {
			while (true) {
				String msg = br.readLine();
				if ("quit".equals(msg)) {
					break;
				}
				System.out.println("Client send message > " + msg);
				pw.print(msg + "\n");
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			doClose();
		}
	}

	private void doClose() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
