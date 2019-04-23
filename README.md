# kelvin-code-challenge

## 1. create an android app (use kotlin)
- display current position on google map, update position on map as device location changes. 
- using Socket.IO-client Java and RxAndroid
- create an event stream using Rx Observables and Subjects wherever needed that contains a json object with the structure:

```json
{
  latitude: 145.67,
  longitude: 754.43
}
```

(your json structure may have more, but not less information depending on your implementation.)

- publish updates to this rx stream  as the device location updates.
- subscribe to this rx event stream elsewhere in your app and publish these events to a socket.io server
- listen to events from the socket.io server and if the event was not published by the current user 
publish the socket.io event to an Rx stream (Rx Subject or Observable) that i now refer to as "OtherUsersStream"
- listen to events from the Rx OtherUsersStream and display the position of these users on the google map along with your position. 

If multiple people are running your app all of the people should be displayed on the map and their positions updating in real time. 

## 2. Create a socket.io server that communicates with your app. 
- the socket.io server should be served as a nodejs app. (I recommend using expressjs as your webserver.)
- create a Docker container that runs the socket.io server. 
You may wish to use ngrok https://ngrok.com to proxy a tunnel to your localhost if you wish to test on an actual android device in addition to the simulators through Android Studio.

# Requirements
1. All code should be accompanied with passing unit tests. 100% coverage is not necessarily required. Use your judgement as what level of coverage seems reasonable to you.
2. Some potentially useful links:

- https://github.com/socketio/socket.io-client-java
- https://socket.io
- https://github.com/ReactiveX/RxAndroid
- https://socket.io/blog/native-socket-io-and-android/

# Submission
1. Create a pull request to the master branch of this repository.

# Recommendations
1. Feel free to take whatever creative license you want on this code challenge by adding any other features, functionality, configuration, or tests that may help to demonstrate your abilities.


# Evaluation Criteria

1. Completion of the requirements listed above
2. Creative license (some instructions above are explicite for brevity, if you find a better implementation, that is equivalent feel free to do it!)
3. Testability
4. On time submission. You will be given 48 hours to complete this challenge. 
# amine-code-challenge

## 1. create an android app (use kotlin)
- display current position on google map, update position on map as device location changes. 
- using Socket.IO-client Java and RxAndroid
- create an event stream using Rx Observables and Subjects wherever needed that contains a json object with the structure:

```json
{
  latitude: 145.67,
  longitude: 754.43
}
```

(your json structure may have more, but not less information depending on your implementation.)

- publish updates to this rx stream  as the device location updates.
- subscribe to this rx event stream elsewhere in your app and publish these events to a socket.io server
- listen to events from the socket.io server and if the event was not published by the current user 
publish the socket.io event to an Rx stream (Rx Subject or Observable) that i now refer to as "OtherUsersStream"
- listen to events from the Rx OtherUsersStream and display the position of these users on the google map along with your position. 

If multiple people are running your app all of the people should be displayed on the map and their positions updating in real time. 

## 2. Create a socket.io server that communicates with your app. 
- the socket.io server should be served as a nodejs app. (I recommend using expressjs as your webserver.)
- create a Docker container that runs the socket.io server. 
You may wish to use ngrok https://ngrok.com to proxy a tunnel to your localhost if you wish to test on an actual android device in addition to the simulators through Android Studio.

# Requirements
1. All code should be accompanied with passing unit tests. 100% coverage is not necessarily required. Use your judgement as what level of coverage seems reasonable to you.
2. Some potentially useful links:

- https://github.com/socketio/socket.io-client-java
- https://socket.io
- https://github.com/ReactiveX/RxAndroid
- https://socket.io/blog/native-socket-io-and-android/

# Submission
1. Create a pull request to the master branch of this repository.

# Recommendations
1. Feel free to take whatever creative license you want on this code challenge by adding any other features, functionality, configuration, or tests that may help to demonstrate your abilities.


# Evaluation Criteria

1. Completion of the requirements listed above
2. Creative license (some instructions above are explicite for brevity, if you find a better implementation, that is equivalent feel free to do it!)
3. Testability
4. On time submission. You will be given 48 hours to complete this challenge. 
