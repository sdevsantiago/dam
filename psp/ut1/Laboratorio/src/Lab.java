import java.io.IOException;

public class Lab
{
	public static void main(
		String[] args)
	{
		ProcessBuilder	pb_blood, pb_urine;
		Process			p_blood, p_urine;

		pb_blood = new ProcessBuilder("java", "-cp", "src", "tests.Blood");
		pb_urine = new ProcessBuilder("java", "-cp", "src", "tests.Urine");
		try
		{
			p_blood = Lab.runTest(pb_blood);
			p_urine = Lab.runTest(pb_urine);
			p_blood.waitFor();
			p_urine.waitFor();
			System.out.printf("Blood values: %s\n", Lab.getResult(p_blood));
			System.out.printf("Urine values: %s\n", Lab.getResult(p_urine));
			if (p_blood.exitValue() != 0 || p_urine.exitValue() != 0)
				System.exit(1);
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
}
