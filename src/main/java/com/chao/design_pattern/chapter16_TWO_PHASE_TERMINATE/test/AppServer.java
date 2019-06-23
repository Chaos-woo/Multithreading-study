package com.chao.design_pattern.chapter16_TWO_PHASE_TERMINATE.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer extends Thread {
	private static final int DEFAULT_PORT = 18989;
	private ServerSocket serverSocket;
	private boolean start;

	private ExecutorService executor = Executors.newFixedThreadPool(10);

	public AppServer() {
		this(DEFAULT_PORT);
	}

	public AppServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
			this.start = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (start) {
				Socket socket = null;
				socket = serverSocket.accept();
				if (socket == null) {
					continue;
				}
				executor.submit(new ClientHandle(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			doDestroy();
		}

	}

	private void doDestroy() {
		try {
			serverSocket.close();
			executor.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		this.start = false;
	}
}
