public class AlternatingThreads {
    private static final Object lock = new Object();
    private static boolean turn = true; // true - выводит "1", false - "2"

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!turn) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.print("1");
                    turn = false;
                    lock.notifyAll();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turn) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.print("2");
                    turn = true;
                    lock.notifyAll();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
