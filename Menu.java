import java.util.Scanner;

public class Menu {

    private static Scanner scanner;

    public static void mainMenu(){

        System.out.println("Main Menu");
        System.out.println("(1) Students functions");
        System.out.println("(2) Administrative functions");
        System.out.println("(3) Reporting functions");
        System.out.println("(4) Quit");

        int option = scanner.nextInt();

        while(true){

            switch(option){
                case 1:
                    studentMenu();
                    option = scanner.nextInt();
                    break;
                case 2:
                    administrativeMenu();
                    option = scanner.nextInt();
                    break;
                case 3:
                    option = scanner.nextInt();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid option");
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }

    }

    public static void studentMenu(){

        System.out.println("Student Functions Menu");
        System.out.println("(1) Register for courses");
        System.out.println("(2) Add/Drop a course");
        System.out.println("(3) Request Transcript");
        System.out.println("(4) Compute GPA");
        System.out.println("(5) Quit");

        int option = scanner.nextInt();

        while(true){

            switch(option){
                case 1:
                    option = scanner.nextInt();
                    break;
                case 2:
                    option = scanner.nextInt();
                    break;
                case 3:
                    option = scanner.nextInt();
                    break;
                case 4:
                    option = scanner.nextInt();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void administrativeMenu(){

        System.out.println("Administrative Functions Menu");
        System.out.println("(1) Create a new course/drop course");
        System.out.println("(2) Prepare term schedule (add sections)");
        System.out.println("(3) Add/drop instructors");
        System.out.println("(4) Alter term schedule (add/drop/update sections)");
        System.out.println("(5) Add/drop students");
        System.out.println("(6) Print the list of instructors and the number of courses each of them is teaching");
        System.out.println("(7) Quit");

        int option = scanner.nextInt();

        while(true){

            switch(option){
                case 1:
                    option = scanner.nextInt();
                    break;
                case 2:
                    option = scanner.nextInt();
                    break;
                case 3:
                    option = scanner.nextInt();
                    break;
                case 4:
                    option = scanner.nextInt();
                    break;
                case 5:
                    option = scanner.nextInt();
                    break;
                case 6:
                    option = scanner.nextInt();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void reportingMenu(){

        System.out.println("Administrative Functions Menu");
        System.out.println("(1) Print schedule of classes (for a term)");
        System.out.println("(2) Print the catalog");
        System.out.println("(3) Print the honors list of students for a department (e.g. students who GPA > 3.8)");
        System.out.println("(4) Quit");

        int option = scanner.nextInt();

        while(true){

            switch(option){
                case 1:
                    option = scanner.nextInt();
                    break;
                case 2:
                    option = scanner.nextInt();
                    break;
                case 3:
                    option = scanner.nextInt();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void main(String[] args){

        scanner = new Scanner(System.in);
        mainMenu();

    }
}
