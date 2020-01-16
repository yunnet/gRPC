var PROTO_PATH = __dirname + '/helloworld.proto'

console.log(PROTO_PATH)

var grpc = require('grpc')
var protoLoader = require('@grpc/proto-loader')
var packageDefinition = protoLoader.loadSync(
  PROTO_PATH, 
  {
    keepcase: true,
    longs: String, 
    enums: String,
    defaults: true,
    oneofs: true
  }
)

var hello_proto = grpc.loadPackageDefinition(packageDefinition).helloworld

function main() {
  var client = new hello_proto.Greeter('localhost:50051', grpc.credentials.createInsecure())

  var user = 'nodejs'
  client.sayHello({name: user}, function (err, resp){
    console.log('Greeting: ', resp.message)
  })
}

main()