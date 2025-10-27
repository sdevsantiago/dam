$(document).ready(function()
{
	$("p").mousedown(function(e)
	{
		if (e.which === 1)
			alert("Click izquierdo");
		else if (e.which === 2)
			alert("Click central");
		else if (e.which === 3)
			alert("Click derecho");
	});
});
