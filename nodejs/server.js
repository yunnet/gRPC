var services = require('./helloworld_grpc_pb')
var messages = require('./helloworld_pb')

var grpc = require('grpc')

function sayHello(call, callback){
  console.log('recv: ' + call.request.getName())

  var reply = new messages.HelloReply()
  reply.setMessage("from Node server, Hello " + call.request.getName())
  
  callback(null, reply)
}

function main(){
  var port = '0.0.0.0:50051'
  var server = new grpc.Server()
  server.addService(
    services.GreeterService, {sayHello: sayHello}
  )
  server.bind(port, grpc.ServerCredentials.createInsecure())
  server.start()
  console.log('server start (' + port + ')')
}

main()
