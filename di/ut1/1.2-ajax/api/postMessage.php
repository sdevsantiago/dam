<?php

session_write_close();

// check if request is of type POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
	// discard requests other than POST
	http_response_code(405);
	exit;
}

if (!strlen(trim($_POST['message']))) {
	// do nothing if message is empty, this is done also on client side, but just in case
	return;
}

$MESSAGES_FILE = dirname(__DIR__) .	'/messages/' . $_POST['chatRoom'] . '-messages.json';
if (file_exists($MESSAGES_FILE)) {
	// read messages file
	$jsonData = json_decode(file_get_contents($MESSAGES_FILE));
} else {
	// create directory and file
	mkdir(dirname($MESSAGES_FILE));
	chmod(dirname($MESSAGES_FILE), 0777);
	touch($MESSAGES_FILE);
	chmod($MESSAGES_FILE, 0777);
	// as file didn't exist, store empty data
	$jsonData = [];
}

// prepare data to add
$data = [
	'id' => uniqid(), // id based on current time in microseconds
	'username' => $_POST['username'],
	'message' => $_POST['message'],
	'timestamp' => $_POST['timestamp']
];
// add received data to json
array_push($jsonData, $data);
// write in file, all unicode characters (JSON_UNESCAPED_UNICODE) and indented (JSON_PRETTY_PRINT)
file_put_contents($MESSAGES_FILE, json_encode($jsonData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

?>
