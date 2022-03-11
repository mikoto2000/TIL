export class BlockingQueue {
    constructor() {
        this.resolverQueue = [];
        this.promiseQueue = [];
    }

    push(data) {
        // リゾルバがひとつもない時には、
        // 新しいプロミス(とそれ用のリゾルバ)を追加する
        if (this.resolverQueue.length <= 0) {
            this.registerPromise();
        }

        // リゾルバキューの先頭を使って、
        // プロミスに値を格納
        this.resolverQueue.shift()(data);

        // 残り要素数を返却
        return this.promiseQueue.length - this.resolverQueue.length;
    }

    shift() {
        // プロミスがひとつもない時には、
        // 新しいプロミス(とそれ用のリゾルバ)を追加する
        if (this.promiseQueue.length <= 0) {
            this.registerPromise();
        }

        // プロミスキューの先頭を返却
        return this.promiseQueue.shift();
    }

    /**
     * プロミスキューにプロミス追加し、
     * そのプロミス用のリゾルバをリゾルバキューへ格納する
     */
    registerPromise() {
        this.promiseQueue.push(new Promise(resolve => {
            this.resolverQueue.push(resolve);
        }));
    }

    size() {
        return this.promiseQueue.length;
    }
}

