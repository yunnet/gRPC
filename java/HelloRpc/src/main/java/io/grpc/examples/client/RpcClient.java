package io.grpc.examples.client;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListenableFuture;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

public class RpcClient {
	private final ManagedChannel channel;
	private final GreeterGrpc.GreeterBlockingStub blockingStub;
	private final GreeterGrpc.GreeterStub stub;
	private final GreeterGrpc.GreeterFutureStub futureStub;
	
	public RpcClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		
		blockingStub = GreeterGrpc.newBlockingStub(channel);
		stub = GreeterGrpc.newStub(channel);
		futureStub = GreeterGrpc.newFutureStub(channel);
	}
	
	public void shutdown() throws Exception {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	public void doAsync(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		stub.sayHello(request, new StreamObserver<HelloReply>() {
			
			@Override
			public void onNext(HelloReply response) {
				System.out.println("Async Greeting: " + response.getMessage());
			}
			
			@Override
			public void onError(Throwable e) {
				System.err.println("Async failed. " + e);
			}
			
			@Override
			public void onCompleted() {
				
			}
		});
	}
	
	public void doFuture(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response = null;
		try {
			ListenableFuture<HelloReply> future = futureStub.sayHello(request);
			response = future.get();
		} catch (Exception e) {
			System.err.println("Future failed. " + e);
		}
		
		System.out.println("Future Greeting: " + response.getMessage());
	}
	
	public void doSync(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response;
		try {
			response = blockingStub.sayHello(request);
		} catch (Exception e) {
			System.err.println("failed. " + e);
			return;
		}
		
		System.out.println("sync Greeting: " + response.getMessage());
	}
	
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 50051;
		
		RpcClient app = new RpcClient(host, port);
		
		long t = System.currentTimeMillis();
		for(int i = 0; i<10000; i++) {
			app.doSync("master" + i);
		}
		long t_end = (System.currentTimeMillis() - t)/1000;
		
		
		long t1 = System.currentTimeMillis();
		for(int i = 0; i<10000; i++) {
			app.doAsync("boss" + i);
		}
		long t1_end = (System.currentTimeMillis() - t1)/1000;
		
		
		long t2 = System.currentTimeMillis();
		for(int i = 0; i<10000; i++) {
			app.doFuture("guy" + i);
		}
		long t2_end = (System.currentTimeMillis() - t2)/1000;
		
		System.out.println("Sync spend time: " + t_end);
		System.out.println("Async spend time: " + t1_end);
		System.out.println("Future spend time: " + t2_end);
		
		app.shutdown();	
	}
}
