<?php
// check if the request starts with /api/
if (preg_match('/^\/api\//', $_SERVER["REQUEST_URI"])) {
    // extract the filename from the request URI and ignore query parameters
    $filename = explode('?', ltrim($_SERVER["REQUEST_URI"], '/'))[0];
    $path = __DIR__ . '/../' . $filename;
    if (file_exists($path)) {
        // execute the requested file
        require $path;
        return;
    } else {
        // file not found, return 404
        http_response_code(404);
        return;
    }
}
else {
    // not an API request, let the server handle it normally from the public directory
    return false;
}
?>
