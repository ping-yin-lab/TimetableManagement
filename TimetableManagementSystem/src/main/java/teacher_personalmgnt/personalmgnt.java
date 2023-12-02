package teacher_personalmgnt;

import java.util.*;

public class personalmgnt {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Welcome to Teacher System");
		System.out.println("==========================");
		System.out.println("Choose the menu below");
		System.out.println("1. Personal Schedule Management");
		System.out.println("2. Module Management");
		System.out.println("3. Class Management");
		System.out.println("Input : ");
		String choice = reader.nextLine();
		switch(choice) {
		case "1":
			System.out.println("Personal Schedule Management");
			System.out.println("1. Add new Personal Schedule");
			System.out.println("2. Show my schedule");
			System.out.println("3. Update schedule");
			System.out.println("4. Delete schedule");
			System.out.println("5. Back to main menu");
			System.out.println("Input : ");
			String PerSche = reader.nextLine();
			switch(PerSche) {
			case "1":
				
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			default: 
			}
			break;
		case "2":
			break;
		case "3":
			break;
		}
	}

}
