var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('MirrorMirror');
var dateFormat = require('dateformat');

db.serialize(function() {

  db.run('CREATE TABLE IF NOT EXISTS users ' +
  	'(UID INTEGER PRIMARY KEY AUTOINCREMENT, ' +
  	'LastName varchar(255) NOT NULL, ' +
  	'FirstName varchar(255) NOT NULL, ' +
  	'Username varchar(255) NOT NULL, ' +
  	'Password varchar(255) NOT NULL)');

  db.run('CREATE TABLE IF NOT EXISTS photos ' + 
  	'(UID INTEGER NOT NULL, ' +
  	'DateTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, ' +
  	'FilePath varchar(255) NOT NULL, ' + 
  	'PRIMARY KEY (UID, DateTime))');

  db.run('CREATE TABLE IF NOT EXISTS weights ' + 
  	'(UID INTEGER NOT NULL, ' +
  	'DateTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, ' +
  	'Weight REAL NOT NULL, ' + 
  	'PRIMARY KEY (UID, DateTime))');

});

function formatDateTime(datetime){
	return dateFormat(datetime, "yyyy-mm-dd_hh:MM:ss");
}
 
function savePhoto(uid, datetime, filepath){
	var stmt = db.prepare('INSERT INTO photos(UID, DateTime, FilePath) VALUES (?, ?, ?)');
	stmt.run(uid, datetime, filepath);
	stmt.finalize();
}
db.savePhoto = savePhoto;

function saveWeight(uid, datetime, weight){
	var stmt = db.prepare('INSERT INTO weights(UID, DateTime, Weight) VALUES (?, ?, ?)');
	stmt.run(uid, formatDateTime(datetime), weight);
	stmt.finalize();
}
db.saveWeight = saveWeight;

module.exports = db;