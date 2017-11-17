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
      var stompClient = Stomp.over(socket);
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

$(function () {
  $("#login-driver").click(function () { loginDriver(); });
  $("#login-passenger").click(function () { loginPassenger(); });
  $("#request-ride").click(function () { requestRide(); });

  var appsNumber = 0;
  
  function initializeNewApp() {
    var source = $("#home-template").html();
    var template = Handlebars.compile(source);
    appNumber = appsNumber + 1;
    var data = { 'appNumber': appNumber };
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
  
  initializeNewApp();
  
  function openPassengerApp(appNumber) {
    var source = $("#login-passenger-template").html();
    var template = Handlebars.compile(source);
    var data = { 'appNumber': appNumber };
    $("#app-" + appNumber).replaceWith(template(data));
    $("#login-passenger-" + appNumber).click(function () {
      loginPassenger(appNumber);
    });
  }
  
  function openDriverApp(appNumber) {
    var source = $("#login-driver-template").html();
    var template = Handlebars.compile(source);
    var data = { 'appNumber': appNumber };
    $("#app-" + appNumber).replaceWith(template(data));
    $("#login-driver-" + appNumber).click(function () {
      loginDriver(appNumber);
    });
  }
  
  function loginPassenger(appNumber) {
    var email = $("#passenger-" + appNumber + "-email").val();
    var password = $("#passenger-" + appNumber + "-password").val();
    var url = 'http://localhost:8282/login/passenger';
    var data = JSON.stringify({ 'email': email, 'password': password });
    post(url, data, function(response) {
      var socket = new SockJS('/ride-hailing/passenger-app');
      var stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/passengers/' + response.passengerId, function (data) {
          console.log('Received from server' + data);
        });
      });
    });
  }
  
  function loginDriver(appNumber) {
    var email = $("#driver-" + appNumber + "-email").val();
    var password = $("#driver-" + appNumber + "-password").val();
    var data = JSON.stringify({ 'email': email, 'password': password });
    var url = 'http://localhost:8282/login/driver';
    post(url, data, function(response) {
      var socket = new SockJS('/ride-hailing/driver-app');
      var stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/drivers/' + response.driverId, function (data) {
          console.log('Received from server' + data);
        });
      });
    });
  }
  
  function post(url, data, callback) {
    $.ajax({
      'type': 'POST',
      'url': url,
      'headers': {
        'Accept': 'application/json; charset=utf-8',
        'Content-Type': 'application/json; charset=utf-8'
      },
      'data': data,
      'success': callback
    });
  }
});
