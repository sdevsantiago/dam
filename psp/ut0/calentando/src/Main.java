import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		File		file;
		Scanner		scanner;
		String[]	line;
		String		seleccion;
		Double		media;
		int			alumnos;
		// BufferedReader	fileReader;
		// Writer			writer;
		// BufferedWriter	fileWriter;

		if (args.length != 1)
		{
			System.err.println("Argumentos invalidos");
			return ;
		}
		file = new File(args[0]);
		if (file.isDirectory())
		{
			System.err.println(file.getName() + "no es un archivo");
			return ;
		}
		if (file.exists())
		{
			if (!file.canRead())
			{
				System.err.println("No se puede leer el archivo");
				return ;
			}

			try
			{
				scanner = new Scanner(file);
				media = 0d;
				alumnos = 0;
				while (scanner.hasNextLine())
				{
					line = scanner.nextLine().split(";");
					System.out.printf("%s %s estudia %s y su nota media es %s\n", line[0], line[1], line[2], line[3]);
					media += Double.parseDouble(line[3]);
					alumnos++;
				}
				System.out.println("Nota media: " + Double.toString(media/alumnos));
			}
			catch (FileNotFoundException e)
			{
				System.err.println("FileNotFoundException");
			}
		}
		else
		{
			// creamos archivo y asignamos permisos
			try
			{
				file.createNewFile();
			}
			catch (Exception e)
			{
				System.err.println("Fallo al crear el archivo");
			}
			if (file.setWritable(true) == false || file.setReadable(true) == false || file.setExecutable(false) == false)
			{
				file.delete(); // borramos si ocurre un error
				System.err.println("Fallo al asignar permisos");
				return ;
			}

			scanner = new Scanner(System.in);
			while (true)
			{
				String			nombre, apellido, modulo;
				Double			nota;
				BufferedWriter	writer;

				System.out.printf("Nombre: ");
				nombre = scanner.nextLine();
				System.out.printf("Apellido: ");
				apellido = scanner.nextLine();
				System.out.printf("Módulo: ");
				modulo = scanner.nextLine();
				System.out.printf("Nota: ");
				nota = scanner.nextDouble();

				try
				{
					writer = new BufferedWriter(new FileWriter(file));
					writer.write(nombre + ";" + apellido + ";" + modulo + ";" + nota.toString() + "\n");
					writer.close();
				}
				catch (IOException e)
				{
					System.err.printf("error al escribir en %s\n", file.getName());
				}
				System.out.printf("¿Añadir más? [s/N]: ");
				seleccion = scanner.nextLine();
				if (seleccion.length() == 0 || seleccion.toLowerCase().contains("n"))
					break ;
			}
			scanner.close();
		}
		return ;
	}
}
