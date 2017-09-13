var chai = require('chai');
var expect = chai.expect;


describe('DBTests', function(){

  var db;

  it('Create Test Database', function(done){
    db = require('./../src/js/dbManager')('MirrorMirrorTest', done);
  });

  it('Empty Users Table', function(){
    db.run('DELETE FROM Users', function(){
      db.get('SELECT COUNT(*) from Users', function(err, result){
        expect(result['COUNT(*)'] == 0);
      });
    });
  });

  it('Empty Photos Table', function(){
    db.run('DELETE FROM Photos', function(){
      db.get('SELECT COUNT(*) from Photos', function(err, result){
        expect(result['COUNT(*)'] == 0);
      });
    });
  });

  it('Empty Weights Table', function(){
    db.run('DELETE FROM Weights', function(){
      db.get('SELECT COUNT(*) from Weights', function(err, result){
        expect(result['COUNT(*)'] == 0);
      });
    });
  });
});
