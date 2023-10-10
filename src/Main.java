public class Main {

    static final int TIME = 2000;
    static final int COUNT = 5;
    static volatile boolean toggle = false;

    public static void toggleSwitch(boolean value) {
        toggle = value;
        if (toggle) {
            System.out.println("Тумблер включен");
        } else {
            System.out.println("Тумблер выключен");
        }
    }

    public static void main(String[] args) {
        Thread user = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                toggleSwitch(true);
                try {
                    Thread.currentThread().sleep(TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        user.start();

        Thread toy = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) return;
                while (toggle) {
                    toggleSwitch(false);
                }
            }
        });
        toy.start();

        try {
            user.join();
            toy.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}