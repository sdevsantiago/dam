package tests;
public class Blood
{
	private static int	exitCode;

	public static void main(
		String[] args)
	{
		try
		{
			Blood.analyze();
		}
		catch (InterruptedException e)
		{
			System.exit(1);
		}
		System.exit(Blood.getExitCode());
	}

	/**
	 * Sets the exit code for the Blood test.
	 *
	 * @param exitCode the exit code to set. If the value is negative,
	 *                 it will be automatically adjusted to 255
	 */
	public static void setExitCode(
		int exitCode)
	{
		Blood.exitCode = exitCode;
		if (exitCode < 0)
			Blood.exitCode = 255;
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
