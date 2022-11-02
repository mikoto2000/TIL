package dev.mikoto2000.study.java.multithread.await;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println("main の開始");

        ExecutorService es1 = Executors.newFixedThreadPool(5);

        es1.execute(new Task("前半戦"));
        es1.execute(new Task("前半戦"));
        es1.execute(new Task("前半戦"));
        es1.execute(new Task("前半戦"));
        es1.execute(new Task("前半戦"));

        // 全スレッドの終了待ち
        es1.shutdown();
        es1.awaitTermination(1, TimeUnit.SECONDS);

        // es1 の全タスクが終了してから、以降の処理が実行される

        ExecutorService es2 = Executors.newFixedThreadPool(5);

        es2.execute(new Task("後半戦"));
        es2.execute(new Task("後半戦"));
        es2.execute(new Task("後半戦"));
        es2.execute(new Task("後半戦"));
        es2.execute(new Task("後半戦"));

        // 全スレッドの終了待ち
        es2.shutdown();
        es2.awaitTermination(1, TimeUnit.SECONDS);

        // es2 の全タスクが終了してから、以降の処理が実行される

        System.out.println("main の終了");
    }

    private static class Task implements Runnable {

        private String label;

        public Task(String label) {
            this.label = label;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("[%s %s]: looping %d", this.label, Thread.currentThread().getName(), i));
            }
            System.out.println(String.format("[%s %s]: Done", this.label, Thread.currentThread().getName()));
        }
    }

}
