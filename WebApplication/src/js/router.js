var express = require('express');
var router = express.Router();
var db = require('./dbManager')('MirrorMirror');

router.get('/', function(req, res) {
	db.getLastImages(3, 1, function(result){
		console.log(result[0]);
		res.render('index', {
			photo: result[0]
		});
	});
});

router.get('/login', function(req, res) {
	res.render('login', {
		title: 'About'
	});
});

module.exports = router;
