export class BlockingQueue {
    constructor() {
        this.queue = [];
    }

    push(data) {
        return this.queue.push(data);
    }

    shift() {
        return this.queue.shift();
    }

    size() {
        return this.queue.length;
    }
}

