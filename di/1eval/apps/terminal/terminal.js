$(document).ready(function ()
{
	const	terminal = $('.app-terminal .window');

	$('#sortable').sortable("option", "cancel", '.terminal-wrapper');	// enable text selection
	terminal.terminal([], {
		memory: true,
		history: false,
	});
});
