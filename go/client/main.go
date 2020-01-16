package main

import (
	pb "github.com/yunnet/gRpc/go/helloworld"
	"golang.org/x/net/context"
	"google.golang.org/grpc"
	"log"
	"os"
)

const (
	address     = "localhost:50051"
	defaultName = "golang"
)

func main() {
	// Set up a connection to the server.
	conn, err := grpc.Dial(address, grpc.WithInsecure())
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	c := pb.NewGreeterClient(conn)

	// Contact the server and print out its response.
	name := defaultName
	if len(os.Args) > 1 {
		name = os.Args[1]
	}
	r, err := c.SayHello(context.Background(), &pb.HelloRequest{Name: name})
	if err != nil {
		log.Fatalf("could not greet: %v", err)
	}
	log.Printf("Greeting response: %s", r.Message)
}
