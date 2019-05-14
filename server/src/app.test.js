var express = require('express')
var app = express()
var server
var io
var _ = require('lodash')

const client = require('socket.io-client');
var socket;

beforeAll((done) => {
  server = require('http').Server(app)  
  server.listen(3000, '0.0.0.0');
  io = require('socket.io')(server)  
  done(); 
})

beforeEach(done => {
  socket = client.connect('http://localhost:3000',{
    'reconnection delay': 0,
    'reopen delay': 0,
    'force new connection': true,
    transports: ['websocket'],
  })
  socket.on('connect', () => {
    done()
  })
})

describe('tests', () => {
  
  
  test('client receives location update', (done) => {
    io.emit('location', {lattitude: 0, longitude: 0, uid: 'abcd'})
    socket.once('location', (data) => {      
      expect(data).toBeDefined()
      done()
    })
  });

  test('server receives location update', (done) => {    
      socket.emit('location', {lattitude: 0, longitude: 0, uid: 'abcd'});
      io.on('connection', (s) => {
        console.log('server connected ', s)
        expect(s).toBeDefined()    
        done()
      })      
  });

})


afterEach(done => {
  if(socket.connected) {        
    socket.disconnect()    
    socket.close()
  }
  done()
})

afterAll((done) => {
  io.close();
  server.close();
  socket.disconnect()
  socket.close()
  done();
});