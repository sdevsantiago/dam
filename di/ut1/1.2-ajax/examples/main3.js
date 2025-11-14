$(document).ready(function () {
    var datos;
    var indiceActual = 0; // inicializamos índice que controla qué información mostramos

	// Manejamos submit del formulario
    $('form').submit(function (e) {
        e.preventDefault();
		
		// Petición AJAX al archivo json.php
        $.ajax({
            url: 'json.json',
            type: 'post',
            dataType: 'json',
            beforeSend: function () {  // antes de enviar la petición
                $('.fa').css('display', 'inline');
            }
        })
            .done(function (respuesta) {  // petición exitosa
			
                datos = respuesta;
                mostrarDatos();
            })
            .fail(function () {  // petición fallida
                $('span').html("Falso");
            })
            .always(function () {  // se ejecuta siempre
                setTimeout(function () {
                    $('.fa').hide();
                }, 1000);
            });
    })
	

	// Función que muestra los datos correspondientes en el formulario
    function mostrarDatos() {
		
        $("#nombre").val(datos[indiceActual].nombre);
        $("#direccion").val(datos[indiceActual].direccion);
        $("#telefono").val(datos[indiceActual].telefono);
        $("#edad").val(datos[indiceActual].edad);
    }

	
	// Funcion siguiente hay que poner id al botón siguiente
	$("#siguiente").on("click",function(e){
		if (indiceActual < datos.length - 1) {
                indiceActual++;
                mostrarDatos();
            }
   
	});
	// Funcion anterior hay que poner id al botón anterior
	$("#anterior").on("click",function(e){
		if (indiceActual > 0) {
                indiceActual--;
                mostrarDatos();
            }
	});
	
	
	$("#anadir").on("click",function(e){
		var datosFormulario =$('form').serialize();
		$.ajax({
				url: 'guardar_datos.php',
				type: 'POST',
				data: datosFormulario,
				success: function(respuesta) {
				console.log('Datos guardados:', respuesta);
				},
				error: function(err) {
				console.error('Error al guardar:', err);
				}
				});
   
	});
})