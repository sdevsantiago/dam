$(document).ready(function()
{
	$("#b1").click(function()
	{
		$("a[target=_blank]").hide();
	});

	$("#b2").click(function()
	{
		$("a[target!=_blank]").hide();
	})
});
