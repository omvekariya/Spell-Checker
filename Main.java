import java.util.Scanner;

public class Main {

	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[34m";
	public static final String YELLOW = "\u001B[33m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String BOLD = "\u001B[1m";
	public static final String RESET = "\u001B[0m";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Dictionary d = new Dictionary("mit.txt");
		// Dictionary d = new Dictionary("kaggle.txt");
		Dictionary d = new Dictionary("master.txt");

		while (true) {
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
			System.out.print(YELLOW + BOLD);
			System.out.println("\t\t\t\t\t1. Insert a New Word");
			System.out.println("\t\t\t\t\t2. Remove a Word");
			System.out.println("\t\t\t\t\t3. Spell Checker Mode");
			System.out.println("\t\t\t\t\t4. Exit");
			System.out.print(GREEN + "\t\t\t\t\tEnter your choice: ");
			int ch = sc.nextInt();
			sc.nextLine();
			System.out.println(RESET + "--------------------------------------------------------------------------------------------------------------------------------" + BOLD);
			switch (ch) {
				case 1 -> {
					System.out.println();
					System.out.print(BLUE + "\tEnter a new word: ");
					String newWord = sc.nextLine();
					if (d.addWord(newWord.toLowerCase()))
					System.out.println(GREEN + "\t" + newWord + " Inserted!");
					else
					System.out.println(RED + "\t" + newWord + " already exists.");
				}
				case 2 -> {
					System.out.println();
					System.out.print(BLUE + "\tEnter a word to remove: ");
					String delWord = sc.nextLine();
					if (d.delWord(delWord.toLowerCase()))
					System.out.println(GREEN + "\t" + delWord + " romeved.");
					else
					System.out.println(RED + "\t" + delWord + " doesn't exists");
				}
				case 3 -> {
					while (true) {
						System.out.println();
						System.out.print(CYAN + BOLD + "\tEnter a word(exit to leave the program): " + YELLOW);
						String word = sc.nextLine();
						System.out.print(RESET);
						if (word.equalsIgnoreCase("exit")) {
							break;
						}

						long startTime = System.nanoTime();
						String match = d.findClosest(word.toLowerCase());
						long endTime = System.nanoTime();
						long elapsedTimeNano = endTime - startTime;
						double elapsedTimeMilli = elapsedTimeNano / 1e6;

						if (word.equalsIgnoreCase(match)) {
							System.out.println(GREEN + BOLD + "\t" + word + " is the correct spelling" + RESET);
							System.out.println(RED + BOLD + "\tTaken Time: " + GREEN + elapsedTimeMilli + "ms" + RESET);
						} else {
							System.out.println(RED + BOLD + "\tDid you mean " + GREEN + match + RED + " ?" + RESET);
							System.out.println(RED + BOLD + "\tTaken Time: " + GREEN + elapsedTimeMilli + "ms" + RESET);
						}
					}
				}
				case 4 -> {
					System.out.println(RESET);
					sc.close();
					d.updateDict();
					System.exit(0);
				}
				default -> {
					System.out.println("Invalid Choice!");
				}
			}
		}
	}
}