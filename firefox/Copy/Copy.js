var titleCopyListener = function(evt) {
    evt.stopImmediatePropagation();
    evt.preventDefault();
    evt.clipboardData.setData("text/plain", document.title);
    document.removeEventListener("copy", copyListener);
}

var title_copy = function() {
    document.addEventListener("copy", titleCopyListener, true);
    document.execCommand("Copy");
}

document.addEventListener("keydown",
        function(evt) {
            switch (evt.key) {
                case "c":
                    title_copy();
                default:
            }
        }
    , true);

