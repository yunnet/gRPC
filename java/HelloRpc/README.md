# gRPC
gRPC for java

### 下载工具
	1、protoc.exe
	https://github.com/protocolbuffers/protobuf/releases  当前版本protoc-3.11.2-win64.zip
	2、插件protoc-gen-grpc-java
	https://search.maven.org/search?q=a:protoc-gen-grpc-java 当前版本protoc-gen-grpc-java-1.26.0-windows-x86_64.exe
### 生成代码
	// 生成protobuf类
	protoc.exe --java_out="D:/Temp" --proto_path="D:/Temp"  helloworld.proto
	// 生成rpc调用类
	protoc.exe --plugin=protoc-gen-grpc-java=d:/protobuf/protoc-gen-grpc-java-1.26.0-windows-x86_64.exe --grpc-java_out="d:/protobuf/" --proto_path="d:/" d:/helloworld.proto
	注：将生成的代码放到工程中，或生成的时候就指定工程路径

	
	
