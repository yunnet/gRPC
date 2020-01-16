# gRPC
gRPC for golang

### 安装工具
    1、protoc.exe
    https://github.com/protocolbuffers/protobuf/releases  当前版本protoc-3.11.2-win64.zip
    2、go插件
    go install github.com/golang/protobuf/protoc-gen-go 当前会生成protoc-gen-go.exe 在GOPATH\bin目录下
### 生成代码
    protoc --go_out=plugins=grpc:./ helloworld.proto 生成helloworld.pb.go文件
    
    注：
    1、可以把protoc.exe 和protoc-gen-go.exe都放到安装目录\go\bin\目录下
    2、helloworld.proto文件中的package helloworld; 不能去掉
    3、protoc.exe --go_out=. helloworld.proto  生成helloworld.pb.go文件,只能生成不带gRPC的文件