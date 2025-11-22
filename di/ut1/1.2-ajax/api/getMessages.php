<?php

session_write_close();

// check if request is of type GET
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

// check if the user has new messages based on the timestamp
if ($timestamp <= $last_write_time) {
	// new messages available
	http_response_code(200);
	header('Content-Type: application/json; charset=utf-8');
	sendJsonResponse(
		getNewMessages($MESSAGES_FILE, $timestamp),
		getEditedMessages($MESSAGES_FILE, $timestamp),
		getDeletedMessages($MESSAGES_FILE, $timestamp)
	);
} else {
	// no new messages
	http_response_code(204);
}

function getNewMessages($messages_file, $timestamp) {
	$data = json_decode(file_get_contents($messages_file), true);
	$messages = array_filter($data, function ($message) use ($timestamp) {
		return $timestamp <= $message['timestamp'];
	});
	return array_values($messages);
}

function getDeletedMessages($messages_file, $timestamp) {
	$data = json_decode(file_get_contents($messages_file), true);
	$deleted_messages = array_filter($data, function ($message) use ($timestamp) {
		return isset($message['deleted_timestamp']) && $timestamp <= $message['deleted_timestamp'];
	});
	return array_values($deleted_messages);
}

function getEditedMessages($messages_file, $timestamp) {
	$data = json_decode(file_get_contents($messages_file), true);
	$edited_messages = array_filter($data, function ($message) use ($timestamp) {
		return isset($message['edit_timestamp']) && $timestamp <= $message['edit_timestamp'];
	});
	return array_values($edited_messages);
}

function parseMessagesToJson($messages) {
	$result = [];
	foreach ($messages as $message) {
		$result = array_merge($result, array_values($message));
	}
	return json_encode($result, JSON_UNESCAPED_UNICODE);
}

function sendJsonResponse($newMessages = null, $editedMessages = null, $deletedMessages = null) {
	$result = [];
	$result[0] = json_decode(json_encode($newMessages), true);
	$result[1] = json_decode(json_encode($editedMessages), true);
	$result[2] = json_decode(json_encode($deletedMessages), true);
	echo json_encode($result, JSON_UNESCAPED_UNICODE);
}

?>
