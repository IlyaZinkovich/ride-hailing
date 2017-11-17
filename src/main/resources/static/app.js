var driverStompClient;

function loginDriver() {
  var email = $("#driver-email").val();
  var password = $("#driver-password").val();

  $.ajax({
    type: 'POST',
    url: 'http://localhost:8282/login/driver',
    headers: {
      'Accept': 'application/json; charset=utf-8',
      'Content-Type': 'application/json; charset=utf-8'
    },
    data: JSON.stringify({ 'email': email, 'password': password }),
    success: function (response) {
      var socket = new SockJS('/ride-hailing/driver-app');
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/drivers/' + response.driverId, function (data) {
          console.log('Received from server' + data);
        });
        stompClient.send('/app/drivers/' + response.driverId + '/location', {}, '{"lat": 52.520645, "lng": 13.409779}');
      });
    }
  });
}

function loginPassenger() {
  var email = $("#passenger-email").val();
  var password = $("#passenger-password").val();

  $.ajax({
    type: 'POST',
    url: 'http://localhost:8282/login/passenger',
    headers: {
      'Accept': 'application/json; charset=utf-8',
      'Content-Type': 'application/json; charset=utf-8'
    },
    data: JSON.stringify({ 'email': email, 'password': password }),
    success: function (response) {
      var socket = new SockJS('/ride-hailing/passenger-app');
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/passengers/' + response.passengerId, function (data) {
          console.log('Received from server' + data);
        });
      });
    }
  });
}

function requestRide() {
  var passengerName = $("#passenger-email").val().split("@")[0];

  $.ajax({
    type: 'POST',
    url: 'http://localhost:8282/rides',
    headers: {
      'Accept': 'application/json; charset=utf-8',
      'Content-Type': 'application/json; charset=utf-8'
    },
    data: JSON.stringify({ 'passengerName': passengerName }),
    success: function (response) {
      console.log(response);
    }
  });
}

var passengerStompClient;

function connect(endpoint) {
  var socket = new SockJS(endpoint);
  var stompClient = Stomp.over(socket);
  stompClient.connect();
  return stompClient;
}

$(function () {
  $("#connectDriver").click(function () { driverStompClient = connect('/ride-hailing/driver'); });
  $("#connectPassenger").click(function () { passengerStompClient = connect('/ride-hailing/passenger'); });
  $("#login-driver").click(function () { loginDriver(); });
  $("#login-passenger").click(function () { loginPassenger(); });
  $("#request-ride").click(function () { requestRide(); });
});

var appsNumber = 0;

function initializeNewApp() {
  var source = $("#base-template").html();
  var template = Handlebars.compile(source);
  appNumber = appsNumber + 1;
  var data = { 'appNumber':  appNumber};
  $("#apps").append(template(data));
  $("#connect-passenger-" + appNumber).click(function () { 
    openPassengerApp(appNumber);
    initializeNewApp();
  });
  $("#connect-driver-" + appNumber).click(function () { 
    openDriverApp(appNumber);
    initializeNewApp();
  });
  appsNumber = appNumber;
}

function openPassengerApp(appNumber) {
  console.log("app " + appNumber + " is opened");
}

function openDriverApp(appNumber) {
  console.log("app " + appNumber + " is opened");
}

initializeNewApp();
