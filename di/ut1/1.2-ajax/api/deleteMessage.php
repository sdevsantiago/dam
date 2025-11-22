<?php

// discard requests other than POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
	// discard requests other than POST
	http_response_code(405);
	return;
}

$MESSAGES_FILE = dirname(__DIR__) .	'/messages/' . $_POST['chatRoom'] . '-messages.json';

if (!file_exists($MESSAGES_FILE)) {
	// no messages file, so no messages
	http_response_code(204);
	return;
}

$messages = json_decode(file_get_contents($MESSAGES_FILE), true);
for ($i = count($messages) - 1; $i >= 0; $i--) {
	// check if the message matches with the data provided
	if ($messages[$i]['id'] == $_POST['id']
		&& $messages[$i]['username'] == $_POST['username']) {
		// mark message as deleted
		$messages[$i]['deleted_timestamp'] = $_POST['timestamp'];
		// write in file
		file_put_contents($MESSAGES_FILE, json_encode($messages, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		// set response to HTTP 200 OK
		http_response_code(200);
		return;
	}
}

// if no message has been modified, set response code to HTTP 204 No content
http_response_code(204);

?>
