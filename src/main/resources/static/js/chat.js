var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function openChatWindow() {
  $('#chatWindow').modal({show:true});
  connect();
}

function connect() {
  var chatPage = document.querySelector('#chat-page');
    username = document.querySelector('#name').value.trim();

    if(username) {
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/chat-messaging');
        stompClient = Stomp.over(socket);

        stompClient.connect({"X-CSRF-TOKEN": $("input[name='_csrf']").val()}, onConnected, onError);
    }
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/addChatUser", {}, JSON.stringify({from: username, type: 'JOIN'}) );
    var connectingElement = document.querySelector('.connecting');
    connectingElement.classList.add('d-none');
}


function onError(error) {
  var connectingElement = document.querySelector('.connecting');
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage() {
  var messageInput = document.querySelector('#message');
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            from: username,
            message: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.message = message.from + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.message = message.from + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.from.substring(0, 1));
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.from);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.from);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.message);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(from) {
    var hash = 0;
    for (var i = 0; i < from.length; i++) {
        hash = 31 * hash + from.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function disconnect() {
  stompClient.disconnect();
}