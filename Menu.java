import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner;
    private static MysqlConnect connect;

    private static final String[] ENROLLMENT_COLUMNS = {"Student", "Term", "SecNum", "Grade"};

    public static void showMainMenu(){

        System.out.println("Main Menu");
        System.out.println("(1) Students functions");
        System.out.println("(2) Administrative functions");
        System.out.println("(3) Reporting functions");
        System.out.println("(4) Quit");

    }

    public static void showStudentMenu(){

        System.out.println("Student Functions Menu");
        System.out.println("(1) Register for courses");
        System.out.println("(2) Add/Drop a course");
        System.out.println("(3) Request Transcript");
        System.out.println("(4) Compute GPA");
        System.out.println("(5) Quit");

    }

    public static void showAdministrativeMenu(){

        System.out.println("Administrative Functions Menu");
        System.out.println("(1) Create a new course/drop course");
        System.out.println("(2) Prepare term schedule (add sections)");
        System.out.println("(3) Add/drop instructors");
        System.out.println("(4) Alter term schedule (add/drop/update sections)");
        System.out.println("(5) Add/drop students");
        System.out.println("(6) Print the list of instructors and the number of courses each of them is teaching");
        System.out.println("(7) Quit");

    }

    public static void showReportingMenu(){

        System.out.println("Reporting Functions Menu");
        System.out.println("(1) Print schedule of classes (for a term)");
        System.out.println("(2) Print the catalog");
        System.out.println("(3) Print the honors list of students for a department (e.g. students who GPA > 3.8)");
        System.out.println("(4) Quit");

    }

    public static void mainMenu(){

        showMainMenu();
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
                    reportingMenu();
                    option = scanner.nextInt();
                    break;
                case 4:
                    try {
                        connect.close();
                        System.exit(0);
                    } catch(IOException io){
                        System.err.println(io);
                    }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }

    }

    public static void addCourse(){

        System.out.print("Enter student id: " );
        String id = scanner.next();

        System.out.print("Enter term to register: ");
        String term = scanner.next();

        System.out.print("Enter Section Number to register: " );
        String secNum = scanner.next();

        Object[] columnValues = new Object[4];

        columnValues[0] = new Integer(id);
        columnValues[1] = term;
        columnValues[2] = new Integer(secNum);
        columnValues[3] = new Integer(-1);

        connect.insert("CS434_Enrollment", ENROLLMENT_COLUMNS, columnValues);
        System.out.println("Course Successfully Registered");

    }

    public static void registerCourses(){

        System.out.print("Do you want to add a course?");
        String option = scanner.next();

        while(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            addCourse();
            System.out.print("Do you want to add a course? ");
            option = scanner.next();
        }
    }

    public static void requestTranscript(){

        System.out.print("Enter Student ID: " );
        int id = scanner.nextInt();

        String query = "SELECT * FROM CS434_Enrollment WHERE Student = "  + id;
        String result = connect.select(query, ",", "Student", "Term", "SecNum", "Grade");

        System.out.print(result);
    }

    public static void computeGpa(){

        System.out.print("Enter Student ID: " );
        int id = scanner.nextInt();

        String query = "SELECT GPA FROM CS434_Student WHERE id = "  + id;
        String result = connect.select(query, "", "GPA");
        System.out.print("Student GPA: " + result);

    }

    public static void studentMenu(){

        showStudentMenu();
        int option = scanner.nextInt();

        while(true){

            switch(option){

                case 1:
                    registerCourses();
                    showStudentMenu();
                    option = scanner.nextInt();
                    break;
                case 2:

                    System.out.print("Press 1 to add a course or press 2 to drop a course: ");

                    int addDropOption = scanner.nextInt();

                    if(addDropOption == 1){
                        addCourse();
                    } else if(addDropOption == 2){

                    }

                    showStudentMenu();
                    option = scanner.nextInt();
                    break;
                case 3:
                    requestTranscript();
                    showStudentMenu();
                    option = scanner.nextInt();
                    break;
                case 4:
                    computeGpa();
                    showStudentMenu();
                    option = scanner.nextInt();
                    break;
                case 5:
                    try {
                        connect.close();
                        System.exit(0);
                    } catch(IOException io){
                        System.err.println(io);
                    }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void administrativeMenu(){

        showAdministrativeMenu();
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
                    try {
                        connect.close();
                        System.exit(0);
                    } catch(IOException io){
                        System.err.println(io);
                    }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void reportingMenu(){

        showReportingMenu();
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
                    try {
                        connect.close();
                        System.exit(0);
                    } catch(IOException io){
                        System.err.println(io);
                    }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void main(String[] args){

        try {

            scanner = new Scanner(System.in);
            connect = new MysqlConnect("database.prop");
            mainMenu();
            connect.close();

        } catch(IOException io){
            System.err.println(io);
        }
    }
}
