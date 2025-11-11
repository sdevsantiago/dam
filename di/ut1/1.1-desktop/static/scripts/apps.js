var	appsLimit = (window.innerWidth > 1185 ) ? 10 : 5;

$(document).ready(function ()
{
	openRecentApps();
	setupAppLauncher();
	setDefaultActiveApp();
	$("#sortable").sortable({
		cursor: 'grabbing',				// cursor to set during the whole event
		revert: 100,					// displays an animation, for the specified time in ms,
										//   when the grabbed element is dropped
		scroll: false					// prevents both vertical and horizontal scrolling
	});
});

function setupAppLauncher()
{
	const	appLauncher = $('#app-launcher');

	appLauncher.on('mouseover', function ()
	{
		appLauncher.css('opacity', '1');
	});
	appLauncher.on('mouseout', function ()
	{
		if ($('.app').length > 0)
			appLauncher.css('opacity', '0');
	});
	appLauncher.find('button').on('click', function ()
	{
		launchApp($(this).attr('data-app'));
		saveOpenApps();
	});
	$(window).on('resize', function ()
	{
		appsLimit = (window.innerWidth > 1185 ) ? 10 : 5;
	})
}

function launchApp(appName)
{
	const	appsContainer = $('#sortable');
	var 	appPath;

	if ($('.app').length >= appsLimit)
		return (alert('Too many apps open, please close one before opening a new one'));
	appPath = `${appName}/${appName}`;
	$.get(`./apps/${appPath}.html`, function (data)
	{
		appsContainer.append(data);
		appsContainer.find('li')
			.off('click')				// unload click event to prevent event duplication
			.on('click', setActiveApp)	// load click event on all elements again
			.addClass('app');			// avoids having to indicate tag manually
		appsContainer.find('.button-close')
			.off('click')				// unload click event to prevent event duplication
			.on('click', closeApp);		// load click event on all elements again
	})
	.fail(function ()
	{
		console.error(`Unable to load app '${appName}'`);
	});

	// Load associated script
	$.getScript(`./apps/${appPath}.js`);

	// Load associated css if it doesn't exist already
	if (!$(`head link[href="./apps/${appPath}.css"]`).length)
	{
		$('<link>').attr({
			rel: 'stylesheet',
			type: 'text/css',
			href: `./apps/${appPath}.css`	//! throws 404 error if file doesn't exist
		}).appendTo('head');
	}
}

function closeApp()
{
	const	window = $(this).parent().parent();

	window.remove();
	if ($('.app').length == 0)
	{
		$('#app-launcher').trigger('mouseover');	// display app launcher
		setDefaultActiveApp();
	}
	saveOpenApps();
}

function saveOpenApps()
{
	const	key = 'recentApps';
	const	apps = $('#sortable li .app-name');
	var		value;

	value = apps.map(function() {
		return $(this).text().toLowerCase();
	}).get().join(',');
	localStorage.setItem(key, value);
	if (apps.length == 0)
		localStorage.removeItem(key);
}

function openRecentApps()
{
	const	key = 'recentApps';
	const 	apps = localStorage.getItem(key);

	if (!apps)
		return ;
	apps.split(',').forEach(function(appName)
	{
		launchApp(appName.trim());
	});
}

function setActiveApp()
{
	const	activeAppModule = $('#active-app-module');
	const	previousActiveApp = $('#active-app');
	const	currentApp = $(this);

	previousActiveApp.removeAttr('id');
	currentApp.attr('id', 'active-app');
	activeAppModule
		.find('#active-app-icon')
		.html(currentApp.find('.app-icon').html());
	activeAppModule
		.find('#active-app-name')
		.html(currentApp.find('.app-name').html());
}

function setDefaultActiveApp()
{
	$("#active-app-icon")
		.html("monitor");
	$("#active-app-name")
		.html("Desktop");
}
