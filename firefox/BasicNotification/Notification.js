browser.browserAction.onClicked.addListener(function() {
    browser.notifications.create({
        "type": "basic",
        "title": "Notification Test",
        "message": "Notification message."
    });
});

