public class Car implements Runnable {
	private String teamName;

	public Car(String teamName) {
		this.teamName = teamName;
	}

	@Override
    public void run() {
		if (Thread.currentThread().isInterrupted()) {
			System.out.println("Car from team " + teamName + " was interrupted.");
		}
		System.out.println("Car from team " + teamName + " is running.");
	}
}
