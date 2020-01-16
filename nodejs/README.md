# gRPC
gRPC for nodejs

### 安装依赖
    npm install grpc-tools --save-dev
    npm install google-protobuf --save
    npm install grpc --save
    npm install --save grpc @grpc/proto-loader
### 生成代码
    node_modules\grpc-tools\bin\protoc.exe --js_out=import_style=commonjs,binary:./ --plugin=protoc-gen-grpc=./node_modules/grpc-tools/bin/grpc_node_plugin.exe --grpc_out=./ helloworld.proto
