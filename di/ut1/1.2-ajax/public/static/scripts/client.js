$(document).ready(function () {
	do {
		user = prompt("Enter your username:");
	} while (user === null || !user.trim().length);

	// store username in session storage
	sessionStorage.setItem('username', user);

	// send message on button click
	$('#sendMessageButton').on('click', function ()
	{
		sendMessage($('input').val());
	});

	// get new messages every second
	setInterval(() => {
		getMessages();
	}, 1000);
});

/**
 * Gets the current timestamp by calling getCurrentTimestampSeconds function.
 *
 * @returns {number} The current timestamp in seconds
 */
function getCurrentTimestamp() {
	return (getCurrentTimestampSeconds());
}

/**
 * Gets the current timestamp in milliseconds since the Unix epoch.
 *
 * @returns {number} The current timestamp in milliseconds
 */
function getCurrentTimestampMilliseconds() {
	return (Date.now());
}

/**
 * Gets the current timestamp in seconds since Unix epoch.
 *
 * @returns {number} The current timestamp in seconds
 */
function getCurrentTimestampSeconds() {
	return (Math.floor(getCurrentTimestampMilliseconds() / 1000));
}

/**
 * Sends a message to the chat server via AJAX POST request.
 *
 * @param {string} messageString - The message content to be sent. Empty or whitespace-only messages are ignored.
 *
 * @description This function validates the message content, generates a timestamp,
 * and sends the message data to the server endpoint 'api/postMessage.php' along with
 * the chat room identifier, username from session storage, and timestamp.
 */
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
			username: sessionStorage.getItem('username'),
			message: messageString,
			timestamp: timestamp.toString()
		}
	});
}

/**
 * Retrieves messages from the server and updates the chat interface.
 *
 * @description This function uses AJAX to request new, deleted, and edited messages
 * from the server since the last known timestamp. It updates the chat display by
 * showing new messages, removing deleted messages, and updating edited messages.
 */
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
				console.log(response);

				showNewMessages(response[0]);
				updateEditedMessages(response[1]);
				removeDeletedMessages(response[2]);

				// update timestamp for next call
				getMessages.timestamp = getCurrentTimestamp();
			}
		}
	});
}

function editMessage(messageId, newMessage) {
	const timestamp = getCurrentTimestamp();

	$.ajax({
		url: 'api/editMessage.php',
		type: 'post',
		data: {
			chatRoom: 'chatRoom',
			username: sessionStorage.getItem('username'),
			id: messageId,
			message: newMessage,
			timestamp: timestamp.toString()
		}
	});
}

function deleteMessage(messageId) {
	const timestamp = getCurrentTimestamp();

	$.ajax({
		url: 'api/deleteMessage.php',
		type: 'post',
		data: {
			chatRoom: 'chatRoom',
			username: sessionStorage.getItem('username'),
			id: messageId,
			timestamp: timestamp.toString()
		}
	});
}

/**
 * Displays new chat messages that haven't been shown before.
 *
 * @description This function iterates through an array of message objects and appends
 * only new messages (those not already displayed) to the #messages container. Each message
 * is displayed with a formatted timestamp, username, and message content.
 *
 * @param {Array<Object>} messages - Array of message objects to display
 */
function showNewMessages(messages) {
	messages.forEach(function (message) {
		if (!$(`#messages .message[data-id="${message.id}"]`).length) {
			// append only new messages (not already displayed)
			$('#messages').append(
				$(`<div class="message" data-id="${message.id}">`)
					.append(
						$(`<div class="message-data">`)
							.append(`<span class="timestamp">[${new Date(message.timestamp * 1000).toLocaleString()}]</span>`)
							.append(' ')
							.append(`<span class="username">${message.username}</span>`)
					)
					.append(`<div class="message-content">${message.message}</div>`)
			);

			// check if the message was sent by the current user
			if (message.username === sessionStorage.getItem('username')) {
				// add buttons and functionality for editing and deleting messages
				$(`#messages .message[data-id="${message.id}"]`).append(
					$(`<div class="message-actions">`)
						.append(`<button class="edit-message"><span class="material-symbols-outlined">edit</span></button>`)
						.append(`<button class="delete-message"><span class="material-symbols-outlined">delete</span></button>`)
				);
				// attach event handler for edit button
				$(`#messages .message[data-id="${message.id}"] .edit-message`)
					.off('click') // remove previous handlers to avoid event duplication
					.on('click', function () {
						let editedMessage = prompt("Edit your message:", message.message);
						if (editedMessage !== null && editedMessage.trim().length && editedMessage !== message.message) {
							editMessage(message.id, editedMessage);
						}
					});
				// attach event handler for delete button
				$(`#messages .message[data-id="${message.id}"] .delete-message`)
					.off('click') // remove previous handlers to avoid event duplication
					.on('click', function () {
						deleteMessage(message.id);
					});
				// add styling for messages from the current user
				$(`#messages .message[data-id="${message.id}"]`).addClass('user-message');

			} else {
				// add styling for messages from other users
				$(`#messages .message[data-id="${message.id}"]`).addClass('other-user-message');
			}
		}
	});
}

/**
 * Removes deleted messages from the DOM based on their IDs.
 *
 * @description This function iterates through an array of message objects and removes
 * the corresponding DOM elements for any messages that have been deleted.
 *
 * @param {Array<Object>} messages - Array of message objects containing ID information
 */
function removeDeletedMessages(messages) {
	messages.forEach(function (message) {
		$(`#messages .message[data-id="${message.id}"]`).remove();
	});
}

function updateEditedMessages(messages) {
	let $message;

	messages.forEach(function (message) {
		$message = $(`#messages .message[data-id="${message.id}"]`);
		// remove existing edited label, if any, to avoid tag duplication if edited multiple times
		$message.find('.edited-label').remove();
		// change message
		$message.find('.message-content')
			.text(message.message)
			.append(' ')
			.append(`<span class="edited-label"><i>(edited)</i></span>`);
	});
}
