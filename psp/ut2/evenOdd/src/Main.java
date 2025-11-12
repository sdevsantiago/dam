import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int endNumber;
        if (args != null && args.length == 1) {
            endNumber = Integer.parseInt(args[0]);
        } else {
            // ask for number if not passed as argument
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a number: ");
            endNumber = sc.nextInt();
            // flush line
            sc.nextLine();
            sc.close();
        }

        // create threads and assign numbers
        Thread threads[] = new Thread[2];
        if (endNumber % 2 == 0) {
            threads[0] = new Thread(new NumberSum(endNumber));
            threads[1] = new Thread(new NumberSum(endNumber - 1));
        } else {
            threads[0] = new Thread(new NumberSum(endNumber - 1));
            threads[1] = new Thread(new NumberSum(endNumber));
        }
        for (Thread thread : threads) {
            // stop thread if main thread ends
            thread.setDaemon(true);
            // set max priority (10)
            thread.setPriority(Thread.MAX_PRIORITY);
        }

        // log start time
        long startTime = System.currentTimeMillis();

        // run threads
        for (Thread thread : threads) {
            thread.start();
        }

        // wait for threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                // terminate execution on error
                System.exit(1);
            }
        }

        // log end time
        long endTime = System.currentTimeMillis();
        System.out.printf("Time taken: %d ms\n", (endTime - startTime));
    }
}

class NumberSum implements Runnable {
    private int end;

    public NumberSum(int end) {
        this.end = end;
    }

    @Override
    public void run() {
        int sum = 0;

        for (int i = this.end; i >= 1; i -= 2) {
            sum += i;
        }
        System.out.printf("Sum of %s is: %d\n",
            (this.end % 2 == 0) ? "even" : "odd",
            sum);
    }
}
