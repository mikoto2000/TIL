import { BlockingQueue } from './BlockingQueue.js';

test('Simple push and pop.', async () => {
    const first = 1;
    const second = 2;
    const third = 3;

    const bq = new BlockingQueue();

    let size = bq.push(first);
    expect(size).toBe(1);
    expect(bq.size()).toBe(1);

    size = bq.push(second);
    expect(size).toBe(2);
    expect(bq.size()).toBe(2);

    size = bq.push(third);
    expect(size).toBe(3);
    expect(bq.size()).toBe(3);

    let e = await bq.shift();
    expect(e).toBe(first);
    expect(bq.size()).toBe(2);

    e = await bq.shift();
    expect(e).toBe(second);
    expect(bq.size()).toBe(1);

    e = await bq.shift();
    expect(e).toBe(third);
    expect(bq.size()).toBe(0);

});

test('Blocking push and pop.', done => {

    blockingPushAndPop(done);

});

async function blockingPushAndPop(done) {
    const first = 1;
    const second = 2;
    const third = 3;

    const bq = new BlockingQueue();

    setTimeout( async () => {
        bq.push(first);
        await new Promise(_ => setTimeout(_, 100));
        bq.push(second);
        await new Promise(_ => setTimeout(_, 100));
        bq.push(third);
    }, 100);

    let e = await bq.shift();
    expect(e).toBe(first);
    expect(bq.size()).toBe(0);

    e = await bq.shift();
    expect(e).toBe(second);
    expect(bq.size()).toBe(0);

    e = await bq.shift();
    expect(e).toBe(third);
    expect(bq.size()).toBe(0);

    done();
}
