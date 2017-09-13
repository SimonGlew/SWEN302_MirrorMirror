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
var requestLastImages = 'request last images event';
var requestImages = 'request images event'
var requestWeights = 'request weights event'
var requestWeightsSuccess = 'request weights success event'
var requestLastImagesSuccess = 'request last images success event';
var requestImagesSuccess = 'request images success event'
var requestWeightsSuccess = 'request weights success event'

server.listen(port, function() {
	console.log('Listening on port ' + port);
});

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use('/', router);

io.on('connection', function(socket) {

	println('device connection, with id: ' + socket.id);

	socket.on(loginEvent, function(data){
		println("Login request received.");
		var username = data.username;
		var password = data.password;

		db.checkLoginDetails(username, password, function(results){
			if(results != null){
				println("Login successful, id: " + results.uid);
				socket.emit(loginSuccessEvent, { 'uid' : results.uid});
			}
		});
	});

	socket.on(requestWeights, function(data){
		println("Previous weights event received");
		var uid = data.uid;
		var numDays = data.numDays;
		db.getPreviousWeights(uid, numDays, function(results){
			var dataToSend = [];
			var prevDay = results[0].datetime.getDate();
			var dayWeight = 0;
			var countOfWeights = 0;
			for(var i = 0; i < results.length; i++){
				var event = results[i];

				if(event.datetime.getDate() == prevDay.getDate()){
					dayWeight += event.weight;
					countOfWeights ++;
				}else{
					dataToSend.push[{'date' : prevDay, 'weight' : (dayWeight / countOfWeights)}];
					dayWeight = event.weight;
					countOfWeights = 1;
				}

				prevDay = event.datetime;
			}
			//past 7 days of weights, could have more than 1 per day, has rows of (datetime, weight)

			socket.emit(requestWeightsSuccess, dataToSend);
		});
	});

	socket.on(imageEvent, function(data) {
		println("New image event received");
		var uid = data.uid;
		var datetime = data.datetime;
		var imageString = data.image;
		saveImage(imageString, uid, datetime);
	});

	socket.on(weightEvent, function(data) {
		println("New weight event received");
		var uid = data.uid;
		var weight = data.weight;
		db.saveWeight(uid, new Date(), weight);
	});

	socket.on(requestLastImages, function(data) {
		println("Request for previous " + data.numImages + " images received.");
		var uid = data.uid;
		var offset = 0;
		var numImages = data.numImages;
		var images = db.getImages(uid, numImages, offset, function(results) {
			var dataToSend = [];
			for(index = 0; index < results.length; index++){
				var imageString = getStringFromImage(results[index].FilePath);
				var time = results[index].DateTime;
				dataToSend.push({ 'imageString' : imageString, 'time' : time });
			}
			println("Emiting images");
			socket.emit(requestLastImagesSuccess, dataToSend);
		});
	});

	socket.on(requestImages, function(data) {
		println("Request for previous " + data.numImages + " images received.");
		var uid = data.uid;
		var offset = data.offset | 0;
		var numImages = data.numImages;
		var images = db.getImages(uid, numImages, offset, function(results) {
			var dataToSend = [];
			for(index = 0; index < results.length; index++){
				var imageString = getStringFromImage(results[index].FilePath);
				var time = results[index].DateTime;
				dataToSend.push({ 'imageString' : imageString, 'time' : time });
			}
			println("Emiting images");
			socket.emit(requestImagesSuccess, dataToSend);
		});
	});
});

function println(message){
	console.log("[" + new Date() + "]" + message);
}

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
