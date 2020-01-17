package io.grpc.examples.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class RpcServer3 {
	private final static int port = 50051;
	private final Server server;
	
	/**
	 * 构造函数
	 */
	public RpcServer3() {
		server = ServerBuilder.forPort(port).addService(new GreeterImpl()).directExecutor().build();
	}
	
	public void start() {
		try {
			server.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					RpcServer3.this.stop();
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
		RpcServer3 server = new RpcServer3();
		server.start();
		
		server.blockUntilShutdown();
	}

}
