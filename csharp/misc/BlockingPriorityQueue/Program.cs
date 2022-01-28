using System.Threading.Tasks;

class BlockingPriorityQueueExample
{

    static void Main(string[] args)
    {
        // TryDequeue を利用した値取得
        {
            BlockingPriorityQueue<string, int> bpq1 = new BlockingPriorityQueue<string, int>();

            Task task = Task.Run(() => {
                Console.WriteLine("1 秒ごとにアイテムをひとつ追加します。(全 4 つ)");

                Thread.Sleep(1000);
                bpq1.Enqueue("pri2, ddd.", 2);
                Thread.Sleep(1000);
                bpq1.Enqueue("pri2, ccc.", 2);
                Thread.Sleep(1000);
                bpq1.Enqueue("pri1, aaa.", 1);
                Thread.Sleep(1000);
                bpq1.Enqueue("pri1, bbb.", 1);
            });

            // キューに詰めたものを順番に取り出す。
            // キューが空の場合は、最大 5 秒待つ。
            while (bpq1.TryDequeue(5000, out string? item, out int priority))
            {
                Console.WriteLine($"Popped Item : {item}. Priority Was : {priority}");
            }
        }

        {
            BlockingPriorityQueue<string, int> bpq2 = new BlockingPriorityQueue<string, int>();

            Task task = Task.Run(() => {
                Console.WriteLine("1 秒ごとにアイテムをひとつ追加します。(全 4 つ)");

                Thread.Sleep(1000);
                bpq2.Enqueue("pri2, ddd.", 2);
                Thread.Sleep(1000);
                bpq2.Enqueue("pri2, ccc.", 2);
                Thread.Sleep(1000);
                bpq2.Enqueue("pri1, aaa.", 1);
                Thread.Sleep(1000);
                bpq2.Enqueue("pri1, bbb.", 1);
            });

            try
            {
                // キューに詰めたものを順番に取り出す。
                // キューが空の場合は、最大 5 秒待つ。
                while (true)
                {
                    string v = bpq2.Dequeue(5000);
                    Console.WriteLine(v);
                }
            }
            catch (InvalidOperationException e)
            {
                // キューが空の場合、 InvalidOperationException が発生する。
                // タイムアウトの例外が出るので、ここには来ないはず。
                Console.WriteLine(e);
            }
        }

    }

}


