var express = require('express')
var router = express.Router()

router.get('/', function(req, res) {
  //res.render('index', {
    //title: 'Home'
  //})
  res.send('hello world')
})

router.get('/login', function(req, res) {
  res.render('login', {
    title: 'About'
  })
})

module.exports = router;
