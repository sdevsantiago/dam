$(document).ready(function ()
{
	$("#btn").on("click", function ()
	{
		if ($("#anadir").val().trim().length < 1)
			return ;
		$.cookie(
			"item" + ($("#listaCompra li").length + 1),
			$("#anadir").val(),
			{
				expires: 1,
				secure: true
			});
		$("#listaCompra").append("<li>" + $("#anadir").val() + "</li>");
		$("#anadir").val("");
	});

	$("#listaCompra").on("dblclick", "li", function ()
	{
		$.removeCookie($(this));
	});

	$("#resetear").on("click", function ()
	{
		for (var cookie in $.cookie())
			$.removeCookie(cookie);
		$("#listaCompra li").remove();
	});
});
