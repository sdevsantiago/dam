import java.util.Scanner;

public class RockPaperScissors
{
	public static void main(
		String[] args)
	{
		ProcessBuilder	p;
		Scanner			sc;
		int				player_selection;
		int				cpu_selection;
		int				last_winner;

		sc = new Scanner(System.in);
		while (true)
		{
			do
			{
				System.out.println("[R]ock, [P]aper, [S]cissors, SHOOT!");
				player_selection = toSelection(Character.toUpperCase(sc.nextLine().charAt(0)));
			} while (player_selection < 0);
			cpu_selection = (int)(Math.random() * 3);
			last_winner = getWinner(player_selection, cpu_selection);
			System.out.println(showWinner(last_winner));
			System.out.println("Try again? Y/n");
			if (Character.toUpperCase(sc.nextLine().charAt(0)) != 'N')
				break ;
		}
		sc.close();
		if (last_winner == 0)
		{
			p = new ProcessBuilder("firefox", "https://www.youtube.com/watch?v=oKqIFhQreKM&pp=ygUQY2VsZWJyYXRpb24gbWVtZQ%3D%3D");
			try {
				p.start();
			} catch (Exception e) {
				System.err.printf("Error while launching process %s\n", p.command().get(0));
			}
		}
	}

	private static int	toSelection(char c)
	{
		switch (c)
		{
			case 'R':
				return (0);
			case 'P':
				return (1);
			case 'S':
				return (2);
			default:
				return (-1);
		}
	}

	private static int	getWinner(
		int player_selection,
		int cpu_selection)
	{
		if (player_selection == 0 && cpu_selection == 2
			|| player_selection == 1 && cpu_selection == 0
			|| player_selection == 2 && cpu_selection == 1)
			return (0);
		return (1);
	}

	private static String	showWinner(
		int winner)
	{
		if (winner == 0)
			return ("You win!");
		return ("You lose");
	}

}
