const intervalHandler = setInterval( () => {
    console.log("fired!");
}, 1000);

setTimeout(() => {
    clearInterval(intervalHandler);
}, 10000);

