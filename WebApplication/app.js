var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var path = require('path');
var router = require('./router');
var fs = require('file-system');

var port = 3000;

var imageEvent = 'image event';

server.listen(port, function() {
	console.log('Listening on port ' + port);
});

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/', router);

io.on('connection', function(socket) {

	console.log('device connection, with id: ' + socket.id);

	socket.on(imageEvent, function(data) {
		var username = data.username;
		var imageString = data.image;
		var filepath = './public/images/' + getCurrentDate() + '.jpg';
		returnImage(imageString, filepath);
	});

	socket.on('chat', function(data) {
		console.log(data);
	});

});

function returnImage(imageString, filepath) {
	var bitmap = new Buffer(imageString, 'base64');
	fs.writeFileSync(filepath, bitmap);
};

function getCurrentDate() {
	var currentDate = new Date();
	var year = currentDate.getFullYear().toString().substring(2);
	var month = currentDate.getMonth();
	var day = currentDate.getDate();
	var hours = currentDate.getHours();
	var minutes = currentDate.getMinutes();
	var seconds = currentDate.getSeconds();

	return year + '' + month + '' + day + '' + hours + '' + minutes + '' + seconds;
};
