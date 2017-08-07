var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var router = require('./router');
var fs = require('file-system');


var port = 3000;

var image_event = "image event";

server.listen(port, function(){
  console.log("Listening on port " + port);
});

app.use('/', router);

io.on('connnection', function(socket){

  console.log("device connection, with id: " + socket.id);

  socket.on(image_event, function(data){
  	var username = data.username;
  	var image_string = data.image;

  	var filepath = "/photos/" + username + "/" + getCurrentDate() + ".jpg";

  	returnImage(image_string, filepath);

  	 //DATA HAS IMAGE AND USERNAME 
  });

  socket.on("chat", function(data){
    console.log(data);
  });
});


function returnImage(image_string, filepath){
	var bitmap = new Buffer(image_string, 'base64');
	fs.writeFileSync(filepath, bitmap);
};

function getCurrentDate(){
	var currentDate = new Date();

	var year = currentDate.getFullYear().substring(2);
	var month = currentDate.getMonth();
	var day = currentDate.getDay();
	var hours = currentDate.getHours();
	var minutes = currentDate.getMinutes();
	var seconds = currentDate.getSeconds();

	return year + "" + month + "" + day + "" + hours + "" + minutes + "" + seconds;
};
