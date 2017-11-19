var messageHandler = function(message) {
    console.log("Message from content: " + message);
}

var port = browser.runtime.connect({name: "test_port"});
port.onMessage.addListener(messageHandler);


