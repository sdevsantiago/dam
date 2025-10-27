$(document).ready(function ()
{
	$("#mayoria_edad").change(function ()
	{
		if (this.checked)
		{
			$("#verdatos ul").empty();
			$("#formulariomayores").show();
			$("#formulariomenores").hide();
			$datosmayores.forEach(e =>
			{
				$("#verdatos ul").append("<li>" + e + "</li>");
			});
		}
		else
		{
			$("#verdatos ul").empty();
			$("#formulariomayores").hide();
			$("#formulariomenores").show();
			$datosmenores.forEach(e =>
			{
				$("#verdatos ul").append("<li>" + e + "</li>");
			});
		}
	});

	var	$datosmayores = [];
	var $datosmenores = [];

	$("#ver").click(function ()
	{
		if ($("#mayoria_edad").is(":checked"))
		{
			$datosmayores.push(
				$("#nombre").val() + ";"
				+ $("#profesion").val() + ";"
				+ $("#experiencia").val() + ";"
				+ $('input[name="edad"]:first').val());
		}
		else
		{
			$datosmenores.push(
				$("#nombre").val() + ";"
				+ $('input[name="edad"]:last').val() + ";"
				+ $("#estudios").val());
		}

		$("#verdatos ul").empty();

		if ($("#mayoria_edad").is(":checked"))
		{
			$datosmayores.forEach(e =>
			{
				$("#verdatos ul").append("<li>" + e + "</li>");
			});
		}
		else
		{
			$datosmenores.forEach(e =>
			{
				$("#verdatos ul").append("<li>" + e + "</li>");
			});
		}
	});

	$("#restaurar").click(function ()
	{
		$datosmayores = [];
		$datosmenores = [];
		$("#verdatos ul").empty();
	});
});
