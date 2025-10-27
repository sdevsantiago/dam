import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Lab
{
	public static void main(
		String[] args)
	{
		Scanner	sc;
		File	in_file, out_file;
		Process	p, p_blood, p_urine;

		sc = new Scanner(System.in);
		if (args.length > 1)
		{
			sc.close();
			System.err.println("Invalid arguments");
			System.exit(1);
			return ; // silence data_file uninitialized error
		}
		else if (args.length == 0)
		{
			System.out.printf("Please indicate data file: ");
			in_file = new File(sc.nextLine());
		}
		else
			in_file = new File(args[0]);

		try
		{
			if (!Lab.checkDataFile(in_file))
			{
				System.err.println("Invalid file");
				System.exit(1);
			}
		}
		catch (Exception e)
		{

		}
		finally
		{
			sc.close();
		}

		out_file = new File("results.txt");

		try
		{
			p = Lab.runTest(new ProcessBuilder(
				"java", "-cp", "src", "tests.Blood", in_file.getName(), out_file.getName()));
			p.waitFor();
			p = Lab.runTest(new ProcessBuilder(
				"java", "-cp", "src", "tests.Urine", in_file.getName(), out_file.getName()));
			p.waitFor();
		}
		catch (IOException | InterruptedException e)
		{
			System.exit(1);
		}
	}

	public static Process	runTest(
		ProcessBuilder pb)
		throws IOException
	{
		Process	p;

		p = pb.start();	// IOException
		return (p);
	}

	public static String	getResult(
		Process p)
	{
		return ((p.exitValue() == 0) ? "OK" : "KO");
	}

	public static boolean	checkDataFile(
		File f)
	{
		if (!f.exists()
			|| f.isDirectory()
			|| !f.canRead())
			return (false);
		return (true);
	}
}
