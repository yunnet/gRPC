# -*- coding: utf-8 -*-

from __future__ import print_function

from proto.helloworld_pb2 import *
from proto.helloworld_pb2_grpc import *

def client():
    ipAddr = "127.0.0.1:50051"
    channel = grpc.insecure_channel(ipAddr)

    stub = GreeterStub(channel)
    response = stub.SayHello(HelloRequest(name="python"))
    print("Greeter client received: " + response.message)

if __name__ == '__main__':
    client()
