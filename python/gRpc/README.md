# gRPC
gRPC for python

### 安装依赖
    pip install grpcio
    pip install protobuf
    pip install grpcio-tools
### 生成代码
    python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. ./helloworld.proto
