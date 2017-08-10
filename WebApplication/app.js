var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var path = require('path');
var fs = require('file-system');

var router = require('./src/js/router');
var db = require('./src/js/dbManager');

var port = 3000;

var imageEvent = 'image event';
var weightEvent = 'weight event';

server.listen(port, function() {
	console.log('Listening on port ' + port);
});

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/', router);

io.on('connection', function(socket) {

	console.log('device connection, with id: ' + socket.id);
	//TODO: SEND UID TO DEVICE

	socket.on(imageEvent, function(data) {
		var uid = data.uid;
		var imageString = data.image;
		saveImage(imageString, uid);
	});

	socket.on(weightEvent, function(data) {
		var username = data.username;
		var weight = data.weight;
		db.saveWeight(uid, new Date(), weight);
	});

});


function saveImage(imageString, uid) {
	//TODO: GET UID FROM DEVICE

	var filepath = './public/images/' + getCurrentDate() + '.jpg';
	var bitmap = new Buffer(imageString, 'base64');
	//Save Photo to filepath
	fs.writeFileSync(filepath, bitmap);
	//Record filepath in database
	db.savePhoto(uid, new Date(), filepath);
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

	