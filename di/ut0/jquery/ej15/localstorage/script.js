$(document).ready(function ()
{
	var	i = 0;

	$("#btn").on("click", function ()
	{
		if ($("#anadir").val().trim().length < 1)
			return ;
		let id = "item" + ++i;

		localStorage.setItem(
			id,
			$("#anadir").val());
		$("#listaCompra").append(
			"<li>"
				+ $("#anadir").val()
			+ "</li>");
		$("#listaCompra li").last().attr("id", id);
		$("#anadir").val("");
	});

	$("#resetear").on("click", function ()
	{
		localStorage.clear();
		$("#listaCompra li").remove();
	});

	$("#listaCompra li").on("dblclick", "li", function ()
	{
		localStorage.removeItem($(this).attr("id"));
		$(this).remove();
	});
});
