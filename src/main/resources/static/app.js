var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ride-hailing');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function loginDriver() {
    stompClient.send("/app/login/driver", {}, JSON.stringify({'driverName': $("#driver-name").val()}));
}

function loginPassenger() {
    stompClient.send("/app/login/passenger", {}, JSON.stringify({'passengerName': $("#passenger-name").val()}));
}

function requestRide() {
    stompClient.send("/app/ride", {}, JSON.stringify({'passengerName': $("#ride-passenger-name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#login-driver" ).click(function() { loginDriver(); });
    $( "#login-passenger" ).click(function() { loginPassenger(); });
    $( "#request-ride" ).click(function() { requestRide(); });
});