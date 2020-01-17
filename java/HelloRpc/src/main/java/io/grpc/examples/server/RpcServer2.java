package io.grpc.examples.server;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.internal.GrpcUtil;

public class RpcServer2 {
	private final static int port = 50051;
	private final Server server;
	
	/**
	 * 构造函数
	 */
	public RpcServer2() {
		String NAME = "grpc-worker-executor";
		Executor executor = Executors.newFixedThreadPool(5, GrpcUtil.getThreadFactory(NAME + "-%d", true)) ;

		server = ServerBuilder.forPort(port).addService(new GreeterImpl()).executor(executor).build();
	}
	
	public void start() {
		try {
			server.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					RpcServer2.this.stop();
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
		RpcServer2 server = new RpcServer2();
		server.start();
		
		server.blockUntilShutdown();
	}

}
