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
var loginEvent = 'login event';
var loginSuccessEvent = 'login success event';
var previousWeightsEvent = 'previous weight event';

server.listen(port, function() {
	console.log('Listening on port ' + port);
});

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/', router);

io.on('connection', function(socket) {

	console.log('device connection, with id: ' + socket.id);

	socket.on(loginEvent, function(data){
		var username = data.username;
		var password = data.password;

		db.checkLoginDetails(username, password, function(results){
			if(results != null){
				socket.emit(loginSuccessEvent, { uid : results.uid});
			}
		});
	});

	socket.on(previousWeightsEvent, function(data){
		var uid = data.uid;
		db.getPreviousWeights(uid, function(results){
			//past 7 days of weights, could have more than 1 per day, has rows of (datetime, weight)
		});
	});

	socket.on(imageEvent, function(data) {
		var uid = data.uid;
		var datetime = data.datetime;
		var imageString = data.image;
		saveImage(imageString, uid, datetime);
	});

	socket.on(weightEvent, function(data) {
		var uid = data.uid;
		var weight = data.weight;
		db.saveWeight(uid, new Date(), weight);
	});

});

function saveImage(imageString, uid, datetime) {
	//TODO: GET UID FROM DEVICE

	var filepath = './public/images/' + uid + '_' + datetime + '.jpg';
	var bitmap = new Buffer(imageString, 'base64');
	//Save Photo to filepath
	fs.writeFileSync(filepath, bitmap);
	//Record filepath in database
	db.savePhoto(uid, datetime, filepath);
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
