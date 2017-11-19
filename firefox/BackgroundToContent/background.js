var myPorts = [];
var connectHandler = function(port) {
    console.log("connected \"" + port.name + "\"(tab id: " + port.sender.tab.id + ")");
    myPorts.push(port);
}

browser.runtime.onConnect.addListener(connectHandler);

function ok(responceMessage) {
    console.log(responceMessage);
}

function ng(error) {
    console.log(error);
}

browser.browserAction.onClicked.addListener(function() {
    console.log("start action.");

    for (myPort of myPorts) {
        myPort.postMessage("message from content");
    }
});

