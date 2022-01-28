using System.Collections.Generic;
using System.Threading;

class BlockingPriorityQueue<TElement,TPriority> : PriorityQueue<TElement,TPriority>
{

    private ManualResetEvent mre = new ManualResetEvent(false);

    public BlockingPriorityQueue()
    {
    }

    public BlockingPriorityQueue(IComparer<TPriority> comparer) : base(comparer)
    {
    }

    public BlockingPriorityQueue(IEnumerable<ValueTuple<TElement,TPriority>> items) : base(items)
    {
        mre.Set();
    }

    public BlockingPriorityQueue(
            IEnumerable<ValueTuple<TElement,TPriority>> initialCapacity,
            IComparer<TPriority> comparer) : base(initialCapacity, comparer)
    {
    }

    public BlockingPriorityQueue(Int32 initialCapacity) : base(initialCapacity)
    {
    }

    public BlockingPriorityQueue(
            Int32 initialCapacity,
            IComparer<TPriority> comparer) : base(initialCapacity, comparer)
    {
    }

    new public TElement Dequeue()
    {
        return this.Dequeue(-1, false);
    }

    public TElement Dequeue(Int32 millisecondsTimeout)
    {
        return this.Dequeue(millisecondsTimeout, false);
    }

    public TElement Dequeue(TimeSpan timeout)
    {
        return this.Dequeue(timeout, false);
    }

    public TElement Dequeue(int millisecondsTimeout, bool exitContext)
    {
        if(mre.WaitOne(millisecondsTimeout, exitContext))
        {
            return this.BlockingDequeue();
        }
        else
        {
            throw new TimeoutException($"でキュー待ちから {millisecondsTimeout}ms が経過しました。");
        }
    }

    public TElement Dequeue(TimeSpan timeout, bool exitContext)
    {
        if(mre.WaitOne(timeout, exitContext))
        {
            return this.BlockingDequeue();
        }
        else
        {
            throw new TimeoutException($"でキュー待ちから {timeout} が経過しました。");
        }
    }

    new public void Enqueue(TElement element, TPriority priority)
    {
        base.Enqueue(element, priority);
        mre.Set();
    }

    new public void EnqueueRange(IEnumerable<TElement> elements, TPriority priority)
    {
        base.EnqueueRange(elements, priority);
        mre.Set();
    }

    new public void EnqueueRange(IEnumerable<(TElement,TPriority)> items)
    {
        base.EnqueueRange(items);
        mre.Set();
    }

    new public TElement EnqueueDequeue(TElement element, TPriority priority)
    {
        mre.Reset();
        base.Enqueue(element, priority);

        return this.BlockingDequeue();
    }

    new public bool TryDequeue(out TElement? element, out TPriority? priority)
    {
        return this.TryDequeue(-1, false, out element, out priority);
    }

    public bool TryDequeue(Int32 millisecondsTimeout, out TElement? element, out TPriority? priority)
    {
        return this.TryDequeue(millisecondsTimeout, false, out element, out priority);
    }

    public bool TryDequeue(Int32 millisecondsTimeout, bool exitContext, out TElement? element, out TPriority? priority)
    {

        if(mre.WaitOne(millisecondsTimeout, exitContext))
        {
            return this.BlockingTryDequeue(out element, out priority);
        }
        else
        {
            element = default(TElement);
            priority = default(TPriority);

            return false;
        }
    }

    public bool TryDequeue(TimeSpan timeout, out TElement? element, out TPriority? priority)
    {
        return this.TryDequeue(timeout, false, out element, out priority);
    }

    public bool TryDequeue(TimeSpan timeout, bool exitContext, out TElement? element, out TPriority? priority)
    {

        if(mre.WaitOne(timeout, exitContext))
        {
            return this.BlockingTryDequeue(out element, out priority);
        }
        else
        {
            element = default(TElement);
            priority = default(TPriority);

            return false;
        }
    }

    private TElement BlockingDequeue()
    {
        var item = base.Dequeue();

        // キューが空になったら、イベント待ちさせるようにする
        if(this.Count == 0)
        {
            mre.Reset();
        }

        return item;
    }

    private bool BlockingTryDequeue(out TElement? element, out TPriority? priority)
    {
        // 要素が追加されるまで待つから false が変えることはないはず
        var isNotEmpty = base.TryDequeue(out element, out priority);

        // キューが空になったら、イベント待ちさせるようにする
        if(this.Count == 0)
        {
            mre.Reset();
        }

        return isNotEmpty;
    }
}

