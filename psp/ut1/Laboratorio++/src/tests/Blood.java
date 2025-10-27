package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Blood
{
	private static int	exitCode;

	public static void main(
		String[] args)
	{
		File			data_file;
		String			line[];

		if (args.length != 1)
		{
			System.err.println("Invalid usage");
			System.exit(1);
		}

		line = null;
		data_file = new File(args[0]);
		try (BufferedReader br = new BufferedReader(new FileReader(data_file)))
		{
			line = br.readLine().split(";");
			br.close();
		}
		catch (Exception e)
		{
			System.exit(1);
		}

		try
		{
			Blood.analyze(
				Integer.parseInt(line[2]),
				Integer.parseInt(line[3]),
				Integer.parseInt(line[4]));
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

	public static void analyze(
		int	glucose,
		int cholesterol,
		int hemoglobine)
		throws InterruptedException
	{
		Blood.setExitCode(0);
		if (!Blood.analyzeGlucose(glucose)
			|| !Blood.analyzeCholesterol(cholesterol)
			|| !Blood.analyzeHemoglobine(hemoglobine))
			Blood.setExitCode(1);
		Thread.sleep(1 + (int)(Math.random() * 5) * 1000);
	}

	public static boolean	analyzeGlucose(
		int glucose)
	{
		return (glucose >= 70 && glucose<= 100);
	}

	public static boolean	analyzeCholesterol(
		int cholesterol)
	{
		return (cholesterol < 200);
	}

	public static boolean	analyzeHemoglobine(
		int	hemoglobine)
	{
		return (hemoglobine >= 13 && hemoglobine <= 17);
	}
}
