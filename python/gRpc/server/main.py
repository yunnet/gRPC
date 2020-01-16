# -*- coding: utf-8 -*-

import time
from concurrent import futures

from proto.helloworld_pb2 import *
from proto.helloworld_pb2_grpc import *

_ONE_DAY_IN_SECONDS = 60 * 60 * 24
port = 50051


class Greeter(GreeterServicer):

    def SayHello(self, request, context):
        print(context)
        print(request)
        return HelloReply(message='Hello, %s!' % request.name)


def server():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_GreeterServicer_to_server(Greeter(), server)
    server.add_insecure_port('[::]:' + str(port))
    server.start()
    try:
        while True:
            print("server start. [" + str(port) + "]")
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    server()
