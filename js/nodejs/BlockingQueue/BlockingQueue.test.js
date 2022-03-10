import { BlockingQueue } from './BlockingQueue.js';

test('Simple push and pop.', () => {
    const first = 1;
    const second = 2;
    const third = 3;

    const bq = new BlockingQueue();

    var size = bq.push(first);
    expect(size).toBe(1);
    expect(bq.size()).toBe(1);

    size = bq.push(second);
    expect(size).toBe(2);
    expect(bq.size()).toBe(2);

    size = bq.push(third);
    expect(size).toBe(3);
    expect(bq.size()).toBe(3);

    expect(bq.shift()).toBe(1);
    expect(bq.size()).toBe(2);

    expect(bq.shift()).toBe(2);
    expect(bq.size()).toBe(1);

    expect(bq.shift()).toBe(3);
    expect(bq.size()).toBe(0);

});


