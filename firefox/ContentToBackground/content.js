function ok(responceMessage) {
    console.log(responceMessage);
}

function ng(error) {
    console.log(error);
}

document.addEventListener("keydown",
        function(evt) {
            var sending = browser.runtime.sendMessage("message from content");
            sending.then(ok, ng)
        }
    , true);

