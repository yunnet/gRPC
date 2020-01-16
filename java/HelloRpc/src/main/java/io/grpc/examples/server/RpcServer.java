package io.grpc.examples.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class RpcServer {
	private final static int port = 50051;
	private final Server server;
	
	/**
	 * 构造函数
	 */
	public RpcServer() {
		server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build();
	}
	
	public void start() {
		try {
			server.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					RpcServer.this.stop();
				}
			});
			
			System.out.println("server start [" + port + "].");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 阻塞， 一直到退出程序 
	 * @throws Exception
	 */
    private void blockUntilShutdown() throws Exception {
    	server.awaitTermination();
    }
	
	public void stop() {
		server.shutdown();
	}

	public static void main(String[] args) throws Exception {
		RpcServer server = new RpcServer();
		server.start();
		
		server.blockUntilShutdown();
	}

}
