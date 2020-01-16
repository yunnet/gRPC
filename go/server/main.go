package main

import (
	"context"
	pb "github.com/yunnet/gRpc/go/helloworld"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"log"
	"net"
)

const port = ":50051"

type serve struct {}

func (c *serve)SayHello(ctx context.Context, in *pb.HelloRequest)(*pb.HelloReply, error)  {
	log.Println("recv: " + in.Name)
	return &pb.HelloReply{Message:"Hello " + in.Name}, nil
}

func main() {
	listen, err := net.Listen("tcp", port)
	if err != nil{
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterGreeterServer(s, &serve{})

	reflection.Register(s)
	if err := s.Serve(listen); err != nil{
		log.Fatalf("failed to serve: %v", err)
	}
}
