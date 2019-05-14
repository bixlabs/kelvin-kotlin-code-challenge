var express = require('express')
var app = express()
var server = require('http').Server(app)
var io = require('socket.io')(server)
var _ = require('lodash')

function startHttpServer() {
  server.listen(3000, '0.0.0.0');
  // WARNING: app.listen(80) will NOT work here!
  
  app.use(express.static('public'))
  
  app.get('/', function (req, res) {
    res.sendFile(__dirname + '/static/index.html');
  });  
}

function startSocketServer() {
  io.on('connection', function (socket) {
    socket.on('location', function (data) {
      console.log(socket.id + ' location: ', data)
      let location = _.isString(data) ? JSON.parse(data) : data; 
      console.log('uid: ', location.uid)
      io.emit("location", location)
    });
  
    socket.on('disconnect', function(){
      console.log(socket.id + ' disconnected')
    }); 
  });
}

module.exports = {
  startHttpServer: startHttpServer,
  startSocketServer: startSocketServer 
}