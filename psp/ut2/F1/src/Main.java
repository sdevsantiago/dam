public class Main {
    public static void main(String[] args) {
        Car car1 = new Car("Red Bull");
        Car car2 = new Car("Mercedes");

        Thread thread1 = new Thread(car1, car1.getTeamName());
        Thread thread2 = new Thread(car2, car2.getTeamName());

        thread1.start();
        thread1.interrupt();

        thread2.start();


    }
}

class Car implements Runnable {
	private String teamName;

	public Car(String teamName) {
		this.teamName = teamName;
	}

    public String getTeamName() {
        return teamName;
    }

	@Override
    public void run() {
        while (Thread.currentThread().isInterrupted()) {
            Thread.yield();
        }
        System.out.println("Car from team " + teamName + " is running.");
	}
}
