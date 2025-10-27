package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Urine
{
	private static int	exitCode;

	public static void main(
		String[] args)
	{
		File	data_file;
		String	line[];

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
			Urine.analyze(
				Integer.parseInt(line[0]),
				Double.parseDouble(line[1]));
		}
		catch (InterruptedException e)
		{
			System.exit(1);
		}
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

	public static void analyze(
		int leukocytes,
		double ph)
		throws InterruptedException
	{
		Urine.setExitCode(0);
		if (!Urine.analyzeLeukocytes(leukocytes)
			|| !Urine.analyzePh(ph))
			Urine.setExitCode(1);
		Thread.sleep(1 + (int)(Math.random() * 5) * 1000);
	}

	public static boolean	analyzeLeukocytes(
		int leukocytes)
	{
		return (leukocytes >= 0 && leukocytes <= 5);
	}

	public static boolean	analyzePh(
		double ph)
	{
		return (ph >= 4.5 && ph <= 8);
	}
}
