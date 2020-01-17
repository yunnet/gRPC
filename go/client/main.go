package main

import (
	pb "github.com/yunnet/gRpc/go/helloworld"
	"golang.org/x/net/context"
	"google.golang.org/grpc"
	"log"
	"strconv"
	"time"
)

const (
	address = "localhost:50051"
)

func main() {
	// Set up a connection to the server.
	conn, err := grpc.Dial(address, grpc.WithInsecure())
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	c := pb.NewGreeterClient(conn)

	t := time.Now()

	for i := 0; i < 100; i++ {
		r, err := c.SayHello(context.Background(), &pb.HelloRequest{Name: "golang" + strconv.Itoa(i)})
		if err != nil {
			log.Fatalf("could not greet: %v", err)
		}
		log.Printf("Greeting response: %s", r.Message)
	}

	t_end := time.Now()
	log.Printf("spend time: %f", t_end.Sub(t).Seconds())
}
