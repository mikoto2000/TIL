using System;
using System.Threading;
using System.Threading.Tasks;

class Program
{

    // イベント待ち用オブジェクト
    static ManualResetEvent mre = new ManualResetEvent(false);

    static void Main(string[] args)
    {
        Console.WriteLine("Hello, World!");

        // イベントをリセット
        // リセット中は、 `mre.WaitOne()` から先に進まなくなる。
        mre.Reset();

        Task task = Task.Run(() => {
            Console.WriteLine("新スレッドで 3 秒待ちます。");

            Thread.Sleep(1000);
            Console.WriteLine("1,");
            Thread.Sleep(1000);
            Console.WriteLine("2,");
            Thread.Sleep(1000);
            Console.WriteLine("3.");

            // イベントをセット
            // これにより、 `mre.WaitOne()` で待っているスレッドが先に進むようになる。
            Console.WriteLine("ManualResetEvent 開放します。");
            mre.Set();
            Console.WriteLine("ManualResetEvent 開放しました。");

            Console.WriteLine("新スレッド終了。");
        });

        // `mre.WaitOne()` で、 mre にイベントがセットされるまで待ち状態になる。
        // これをコメントアウトすると、
        // 新スレッドが文字出力する前に Main スレッドが終わるため、
        // `Hello, World!\nMain 関数終了。` しか表示されない。
        mre.WaitOne();

        Console.WriteLine("Main 関数終了。");

    }
}

