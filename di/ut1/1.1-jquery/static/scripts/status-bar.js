const	cookieName = 'color-scheme';


$(document).ready(function ()
{
	setColorScheme();
	getTime("time");
	setInterval(getTime, 1000, "time");
	$("#color-scheme").on('click', function ()
	{
		swapColors();
	});
	$("#power").on("click", function ()
	{
		if (!confirm('Are you sure you want to shutdown?'))
			return ;
		// Redirect to a blank page since window.close() doesn't work for main windows
		window.location.href = 'about:blank';
	});
});

function getTime(
	containerId)
{
	const time = new Date();

	$("#" + containerId).text(
		time.getHours().toString().padStart(2, '0')
		+ ' '
		+ time.getMinutes().toString().padStart(2, '0'));
}

function swapColors()
{
	// Toggle between light and dark
	let newScheme = $.cookie(cookieName) === 'dark' ? 'light' : 'dark';

	// Save new scheme to cookie
	$.cookie(cookieName, newScheme, { secure: true });

	setColorScheme();
}

function setColorScheme()
{
	const currentScheme = $.cookie(cookieName) || 'dark';

	// Apply the current scheme
	$(`#${cookieName} .icon`).html(currentScheme === 'dark' ? 'bedtime' : 'light_mode');
	$('body').css('color-scheme', currentScheme);
}
