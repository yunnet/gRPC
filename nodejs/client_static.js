var messages = require('./helloworld_pb')
var services = require('./helloworld_grpc_pb')

var grpc = require('grpc')

function main() {
  var client = new services.GreeterClient('localhost:50051', grpc.credentials.createInsecure())

  for(var i = 0; i < 1000; i++) {
    var request = new messages.HelloRequest()
    request.setName('Node.js' + i)

    client.sayHello(request, function(err, resp){
      console.log('Greeting: ', resp.getMessage())
    })
  }
}

main()