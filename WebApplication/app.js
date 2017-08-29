var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var path = require('path');
var filesystem = require('file-system');
var fs = require('fs');

var router = require('./src/js/router');

var db = require('./src/js/dbManager')('MirrorMirror');

var port = 3000;

var imageEvent = 'image event';
var weightEvent = 'weight event';
var loginEvent = 'login event';
var loginSuccessEvent = 'login success event';
var previousWeightsEvent = 'previous weight event';
var requestImages = 'request last images event';
var requestImagesSuccess = 'request last images success event';

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
				socket.emit(loginSuccessEvent, { 'uid' : results.uid});
			}
		});
	});

	socket.on(previousWeightsEvent, function(data){
		var uid = data.uid;
		db.getPreviousWeights(uid, function(results){
			//past 7 days of weights, could have more than 1 per day, has rows of (datetime, weight)
			var dataToSend = [];

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
		console.log('test123');
		db.saveWeight(uid, new Date(), weight);
	});

	socket.on(requestImages, function(data) {
		var uid = data.uid;
		var numImages = data.numImages;
		var images = db.getLastImages(uid, numImages, function(results) {
			var dataToSend = [];
			for(index = 0; index < results.length; index++){
				var imageString = getStringFromImage(results[index].FilePath);
				var time = results[index].DateTime;

				dataToSend.push({ 'imageString' : imageString, 'time' : time });
			}
			socket.emit(requestImagesSuccess, { 'data' : dataToSend});
		});
	});

});

function saveImage(imageString, uid, datetime) {
	var filepath = './public/images/' + uid + '_' + datetime + '.jpg';
	var bitmap = new Buffer(imageString, 'base64');
	//Save Photo to filepath
	filesystem.writeFileSync(filepath, bitmap);
	//Record filepath in database
	db.savePhoto(uid, datetime, filepath);
};

function getStringFromImage(filepath){
	var bitmap = fs.readFileSync(filepath);
	return new Buffer(bitmap).toString('base64');
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
