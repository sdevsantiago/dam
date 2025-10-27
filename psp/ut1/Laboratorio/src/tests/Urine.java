package tests;
public class Urine
{
	private static int	exitCode;

	public static void main(
		String[] args)
		throws InterruptedException
	{
		Urine.analyze();
		System.exit(Urine.getExitCode());
	}

	public static void setExitCode(
		int exitCode)
	{
		Urine.exitCode = exitCode;
		if (exitCode < 0)
			Urine.exitCode = 255;
	}

	public static int getExitCode()
	{
		return exitCode;
	}

	public static int analyze()
		throws InterruptedException
	{
		Thread.sleep(1 + (int)(Math.random() * 5) * 1000);
		return ((int)Math.round(Math.random()));
	}
}
