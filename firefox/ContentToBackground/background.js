var messageHandler = function(message, sender, sendResponse) {
    console.log("Message from content: " + message);
    sendResponse("Message received.");
}

browser.runtime.onMessage.addListener(messageHandler);

