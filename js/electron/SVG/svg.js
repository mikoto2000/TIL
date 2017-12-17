const fs = require('fs')

window.addEventListener('load', () => {
    fs.readFile('image/myicon.svg', (status, svgText) => {
        let svgDom = new DOMParser().parseFromString(svgText, 'application/xml');
        let target = document.getElementById("svg");
        target.appendChild(document.importNode(svgDom.documentElement, true));
    });
})

