package io.grpc.examples.server;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase{
	
	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> response) {
		System.out.println("recv: " + request.getName());
		String msg = "Hello " + request.getName(); 
		
		HelloReply reply = HelloReply.newBuilder().setMessage(msg).build();
		response.onNext(reply);
		response.onCompleted();
	}
}
