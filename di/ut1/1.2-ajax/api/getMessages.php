<?php

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
	// discard requests other than GET
	http_response_code(405);
	return;
}

$MESSAGES_FILE = dirname(__DIR__) .	'/messages/' . $_GET['chatRoom'] . '-messages.json';

if (!file_exists($MESSAGES_FILE)) {
	// no messages file, so no messages
	http_response_code(204);
	return;
}

$last_write_time = filemtime($MESSAGES_FILE);
$timestamp = $_GET['timestamp']; // seconds

if ($timestamp <= $last_write_time) {
	// new messages available
	http_response_code(200);
	$data = json_decode(file_get_contents($MESSAGES_FILE), true);
	$messages = array_filter($data, function ($message) use ($timestamp) {
		return $timestamp <= $message['timestamp'];
	});
	echo json_encode(array_values($messages), JSON_UNESCAPED_UNICODE);
} else {
	// no new messages
	http_response_code(204);
}

?>
