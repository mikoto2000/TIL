using System.Collections.Generic;

class PriorityQueueExample
{

    static void Main(string[] args)
    {
        // TryDequeue を利用した値取得
        {
            var priorityQueue1 = new PriorityQueue<string, int>();

            priorityQueue1.Enqueue("pri2, ddd.", 2);
            priorityQueue1.Enqueue("pri2, ccc.", 2);
            priorityQueue1.Enqueue("pri1, aaa.", 1);
            priorityQueue1.Enqueue("pri1, bbb.", 1);

            // キューに詰めたものを順番に取り出す
            // 優先度の高いものを FIFO で取得した後、
            // 優先度の低いものが FIFO で取得できる。
            while (priorityQueue1.TryDequeue(out string item, out int priority))
            {
                Console.WriteLine($"Popped Item : {item}. Priority Was : {priority}");
            }
        }

        // Dequeue を利用した値取得
        {
            var priorityQueue2 = new PriorityQueue<string, int>();

            priorityQueue2.Enqueue("pri2, ddd.", 2);
            priorityQueue2.Enqueue("pri2, ccc.", 2);
            priorityQueue2.Enqueue("pri1, aaa.", 1);
            priorityQueue2.Enqueue("pri1, bbb.", 1);

            try
            {
                // キューに詰めたものを順番に取り出す
                // 優先度の高いものを FIFO で取得した後、
                // 優先度の低いものが FIFO で取得できる。
                while (true)
                {
                    string v = priorityQueue2.Dequeue();
                    Console.WriteLine(v);
                }
            }
            catch (InvalidOperationException e)
            {
                // キューが空の場合、 InvalidOperationException が発生する
                Console.WriteLine(e);
            }
        }

    }
}

