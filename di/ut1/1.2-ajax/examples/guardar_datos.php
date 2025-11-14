<?php
// Obtener datos del POST
$nombre = $_POST['nombre'];
$direccion = $_POST['direccion'];
$telefono = $_POST['telefono'];
$edad = $_POST['edad'];
// Ruta al archivo JSON
$archivo = 'json.json';

// Leer contenido actual
$contenido = file_get_contents($archivo);
$datos = json_decode($contenido, true);

// Añadir nuevo elemento
$datos[] = ['nombre' => $nombre, 'direccion' => $direccion, 'telefono' => $telefono, 'edad' => $edad];

// Guardar de nuevo
file_put_contents($archivo, json_encode($datos, JSON_PRETTY_PRINT));

echo json_encode(['status' => 'ok']);
?>