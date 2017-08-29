var sqlite3 = require('sqlite3').verbose();
var db;
var dateFormat = require('dateformat');

function formatDateTime(datetime){
	return dateFormat(datetime, "yyyy-mm-dd_hh-MM-ss");
}

function savePhoto(uid, datetime, filepath){
	var stmt = db.prepare('INSERT INTO photos(UID, DateTime, FilePath) VALUES (?, ?, ?)');
	stmt.run(uid, datetime, filepath);
	stmt.finalize();
}

function getLastImages(uid, numImages, callback){
	db.all("SELECT * FROM photos WHERE UID = " + uid + " ORDER BY DateTime DESC",  function (err, results){
        if(err){
            console.log(err);
        }else{
        	if(results.length < numImages){
        		callback(results.slice(0, results.length));
        	}else{
         		callback(results.slice(0,numImages));

        	}
        }
      });
}

function saveWeight(uid, datetime, weight){
  weight = weight.toFixed(1);
	var stmt = db.prepare('INSERT INTO weights(UID, DateTime, Weight) VALUES (?, ?, ?)');
	stmt.run(uid, formatDateTime(datetime), weight);
	stmt.finalize();
}

function getPreviousWeight(uid){
  db.all("SELECT datetime, weight FROM weights WHERE DateTime BETWEEN datetime('now','-6 days') AND datetime('now', 'localtime') AND uid = " + uid + " ORDER BY DateTime DESC", function(err, results){
    if(err){
      console.log(err);
    }else{
      return results;
    }
  });
}

function checkLoginDetails(username, password, callback){
  db.all("SELECT UID FROM Users WHERE Username = " + username + " AND Password = " + password, function(err, results){
    if(err){
      console.log(err);
    }else{
      if(results.length == 0){
        callback(null);
      }else{
        callback(results[0]);
      }
    }
  });
}

function openDatabase(dbname){
  db = new sqlite3.Database(dbname);
  require('fs').readFile('./databaseCreatorScript.sql', function(err, script){
    if(err){
      throw err;
    }
    db.serialize(function(){
      db.run(script.toString(), function(){
        console.log("Database " + dbname + " created.");
      });
    });
  });
  db.savePhoto = savePhoto;
  db.saveWeight = saveWeight;
  db.getLastImages = getLastImages;
  db.checkLoginDetails = checkLoginDetails;
  db.getPreviousWeight = getPreviousWeight;

  return db;
}

module.exports = openDatabase;
