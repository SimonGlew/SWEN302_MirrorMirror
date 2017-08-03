var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var router = require('./router');

var port = 3000;

var image_event = "image event";

server.listen(port, function(){
  console.log("Listening on port " + port);
});

app.use('/', router);

io.on('connnection', function(socket){

  console.log("device connection, with id: " + socket.id);

  socket.on(image_event, function(data){
    //DATA HAS IMAGE AND USERNAME
  });

  socket.on("chat", function(data){
    console.log(data);
  });
});
