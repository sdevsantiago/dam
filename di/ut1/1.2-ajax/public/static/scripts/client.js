$(document).ready(function () {
	$('#sendMessageButton').on('click', function ()
	{
		sendMessage($('input').val());
	});
	// get new messages every second
	setInterval(() => {
		getMessages();
	}, 1000);
});

function getCurrentTimestamp() {
	return (getCurrentTimestampSeconds());
}

function getCurrentTimestampMilliseconds() {
	return (Date.now());
}

function getCurrentTimestampSeconds() {
	return (Math.floor(getCurrentTimestampMilliseconds() / 1000));
}

function sendMessage(messageString) {
	if (!messageString.trim().length) {
		// do nothing if message is empty
		return;
	}

	const timestamp = getCurrentTimestamp();

	// send message to server
	$.ajax({
		url: 'api/postMessage.php',
		type: 'post',
		data: {
			chatRoom: 'chatRoom',
			username: 'user',
			message: messageString,
			timestamp: timestamp.toString()
		}
	});
}

function getMessages() {
	// check if the timestamp has been initialized
    if (typeof getMessages.timestamp == 'undefined') {
		const ALL_MESSAGES = 0;
		const NEW_MESSAGES = getCurrentTimestamp();

		// initialize timestamp (behaves as a static variables)
        getMessages.timestamp = ALL_MESSAGES;
    }


	// get messages from server
	$.ajax({
		url: 'api/getMessages.php',
		type: 'get',
		data: {
			chatRoom: 'chatRoom',
			timestamp: getMessages.timestamp.toString()
		},
		success: function (response) {
			if (response) {
				showMessages(JSON.parse(response)); // parse response back to JSON to iterate inside showMessages
				// update timestamp for next call
				getMessages.timestamp = getCurrentTimestamp();
			}
		}
	});


}

function showMessages(messages) {
	messages.forEach(function (message) {
		if (!$(`#messages .message[data-timestamp="${message.timestamp}"]`).length) {
			// append only new messages (not already displayed)
			$('#messages').append(
				$(`<div class="message" data-timestamp="${message.timestamp}">`)
					.text(
						'[' + new Date(message.timestamp * 1000).toLocaleString() + '] ' +
						message.username + ': ' +
						message.message
					)
			);
		}
	});
}
