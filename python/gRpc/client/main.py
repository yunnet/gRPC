# -*- coding: utf-8 -*-

from __future__ import print_function

import time

from proto.helloworld_pb2 import *
from proto.helloworld_pb2_grpc import *


def client():
    ipAddr = "127.0.0.1:50051"
    channel = grpc.insecure_channel(ipAddr)

    stub = GreeterStub(channel)
    t = time.time()
    for i in range(0, 10000):
        response = stub.SayHello(HelloRequest(name="python" + str(i)))
        print("Greeter client received: " + response.message)
    print("spend time:" + str(time.time() - t))


if __name__ == '__main__':
    client()
