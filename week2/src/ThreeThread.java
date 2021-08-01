//多线程交替打印
public class ThreeThread {
    public static class ThreadPrinter implements Runnable {
        private Object prev;
        private Object self;
        private ThreadPrinter( Object prev, Object self) {
            this.prev = prev;
            this.self = self;
        }
        @Override
        public void run() {
            int count = 1;
            while (count <= 120) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.print(count);
                        count++;

                        self.notifyAll();// 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。
                    }
                    //此时执行完self的同步块，这时self锁才释放。
                    try {
                        prev.wait(); // 立即释放 prev锁，当前线程休眠，等待唤醒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrinter pa = new ThreadPrinter( c, a);
        ThreadPrinter pb = new ThreadPrinter( a, b);
        ThreadPrinter pc = new ThreadPrinter( b, c);

        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }
}
