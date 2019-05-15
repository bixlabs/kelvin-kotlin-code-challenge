const express = require('express')
const app = express()
const server = require('http').Server(app)
const io = require('socket.io')(server)
const ngrok = require('ngrok');
const _ = require('lodash')

function startHttpServer() {
  server.listen(3000, '0.0.0.0');  
  app.use(express.static('public'))
  
  app.get('/', function (req, res) {
    res.sendFile(__dirname + '/static/index.html');
  });
  
  ngrok.connect({
    proto: 'http',
    addr: 3000, 
    authtoken: process.env.NGROK_TOKEN,
    region: 'us', 
  }).then(res => {
    console.log(res)
  }).catch(error => {
    console.log(error)
  })
}

function startSocketServer() {
  io.on('connection', function (socket) {
    socket.on('location', function (data) {
      let location = _.isString(data) ? JSON.parse(data) : data; 
      io.emit("location", location)
    });
  });
}

module.exports = {
  startHttpServer: startHttpServer,
  startSocketServer: startSocketServer 
}