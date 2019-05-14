var socket = io('https://4616f333.ngrok.io');
socket.on('connect', function () {
  socket.emit("location", {latitude: 34.0522, longitude: -118.2437, uid: "iuye"})
  socket.on('location', function (location) {
    // location
    console.log('location ', location);
  });
});

