import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner;
    private static MysqlConnect connect;

    private static final String[] ENROLLMENT_COLUMNS = {"Student", "Term", "SecNum", "Grade"};
    private static final String[] COURSE_COLUMNS = {"CourseTitle", "Hours", "Department"};
    private static final String[] INSTRUCTOR_COLUMNS = {"LName", "FName", "Department", "Office", "Phone", "Email"};
    private static final String[] STUDENT_COLUMNS = {"LName", "FName", "Username", "Phone", "Street", "City", "State", "Zip", "Degree", "Department" };

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
                    showMainMenu();
                    option = scanner.nextInt();
                    break;
                case 2:
                    administrativeMenu();
                    showMainMenu();
                    option = scanner.nextInt();
                    break;
                case 3:
                    reportingMenu();
                    showMainMenu();
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

    public static void studentAddCourse(){

        System.out.print("Enter student id: ");
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

    public static void studentDropCourse(){

        System.out.print("Enter student id: ");
        String id = scanner.next();

        System.out.print("Enter term to register: ");
        String term = scanner.next();

        System.out.print("Enter Section Number to register: " );
        String secNum = scanner.next();

        String query = "DELETE FROM CS434_Enrollment" +
                       " WHERE Student = " + id +
                       " AND Term = '" + term + "'" +
                       " AND SecNum = " + secNum;

        connect.delete(query);
        System.out.println("Course Successfully dropped");
    }

    public static void registerCourses(){

        System.out.print("Do you want to add a course?");
        String option = scanner.next();

        while(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            studentAddCourse();
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

        while(option != 5){

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
                        studentAddCourse();
                    } else if(addDropOption == 2){
                        studentDropCourse();
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
                    // try {
                    //     connect.close();
                    //     System.exit(0);
                    // } catch(IOException io){
                    //     System.err.println(io);
                    // }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void insertCourse(){

        System.out.print("Enter the name of the course: ");
        String courseTitle = scanner.next();

        System.out.print("Enter the number of hours: ");
        int hours = scanner.nextInt();

        System.out.print("Enter the Department ID: ");
        int deptId = scanner.nextInt();

        Object[] columnValues = new Object[3];

        columnValues[0] = courseTitle;
        columnValues[1] = new Integer(hours);
        columnValues[2] = new Integer(deptId);

        connect.insert("CS434_Course", COURSE_COLUMNS, columnValues);
        System.out.println("Course successfully inserted");

    }

    public static void dropCourse(){

        System.out.print("Enter the name of course to drop: ");
        String courseTitle = scanner.next();

        connect.delete("DELETE FROM CS434_Course WHERE CourseTitle = '" + courseTitle + "'");
        System.out.println("Course successfully deleted");

    }

    public static void insertProfessor(){

        System.out.print("Enter the Last Name of Instructor: ");
        String lname = scanner.next();

        System.out.print("Enter the First Name of Instructor: ");
        String fname = scanner.next();

        System.out.print("Enter the Department Id: ");
        int deptId = scanner.nextInt();

        System.out.print("Enter the Office Info: ");
        String office = scanner.next();

        System.out.print("Enter the Phone Number: ");
        String phone = scanner.next();

        System.out.print("Enter the Email: ");
        String email = scanner.next();

        Object[] columnValues = new Object[6];

        columnValues[0] = lname;
        columnValues[1] = fname;
        columnValues[2] = deptId;
        columnValues[3] = office;
        columnValues[4] = phone;
        columnValues[5] = email;

        connect.insert("CS434_Instructor", INSTRUCTOR_COLUMNS, columnValues);
        System.out.println("Instructor successfully added");
    }

    public static void dropProfessor(){

        System.out.print("Enter the email of the professor to drop: ");
        String email = scanner.next();

        String query = "DELETE FROM CS434_Instructor" +
                       " WHERE Email = '" + email + "'";

        connect.delete(query);
        System.out.println("Professor successfully deleted");
    }

    public static void insertStudent(){

        System.out.print("Enter the Last Name of Student: ");
        String lname = scanner.next();

        System.out.print("Enter the First Name of Student: ");
        String fname = scanner.next();

        System.out.print("Enter the Username of Student: ");
        String username = scanner.next();

        System.out.print("Enter the Phone Number: ");
        String phone = scanner.next();

        System.out.print("Enter the Student Street Address: ");
        String streetAddress = scanner.next();

        System.out.print("Enter the Student City: ");
        String city = scanner.next();

        System.out.print("Enter the Student State: ");
        String state = scanner.next();

        System.out.print("Enter the Student Zipcode: ");
        String zipCode = scanner.next();

        System.out.print("Enter the Student Degree Type: ");
        String degreeType = scanner.next();

        System.out.print("Enter the Student Department: ");
        String dept = scanner.next();

        Object[] columnValues = new Object[10];

        columnValues[0] = lname;
        columnValues[1] = fname;
        columnValues[2] = username;
        columnValues[3] = phone;
        columnValues[4] = streetAddress;
        columnValues[5] = city;
        columnValues[6] = state;
        columnValues[7] = zipCode;
        columnValues[8] = degreeType;
        columnValues[9] = dept;

        connect.insert("CS434_Student", STUDENT_COLUMNS, columnValues);
        System.out.println("Student successfully added");

    }

    public static void dropStudent(){

        System.out.print("Enter the username of the student to drop: ");
        String username = scanner.next();

        String query = "DELETE FROM CS434_Student" +
                       " WHERE Username = '" + username + "'";

        connect.delete(query);
        System.out.println("Student successfully deleted");
    }

    public static void instructorTeaching(){

        String query = "SELECT Term, concat(LName, ', ', FName) as FullName, count(*) as CoursesTeaching" +
                       " FROM CS434_Section, CS434_Instructor" +
                       " WHERE CS434_Section.Instructor = CS434_Instructor.ID" +
                       " GROUP BY Term, Instructor";

        String result = connect.select(query, "\t", "Term", "FullName", "CoursesTeaching");

        System.out.print(result);
        System.out.flush();
    }

    public static void administrativeMenu(){

        showAdministrativeMenu();
        int option = scanner.nextInt();

        while(option != 7){

            switch(option){
                case 1:

                    System.out.print("Press 1 to insert or press 2 to drop course: ");
                    int op = scanner.nextInt();

                    if(op == 1){
                        insertCourse();
                    } else {
                        dropCourse();
                    }

                    showAdministrativeMenu();
                    option = scanner.nextInt();

                    break;
                case 2:

                    showAdministrativeMenu();
                    option = scanner.nextInt();

                    break;
                case 3:

                    System.out.print("Press 1 to insert or press 2 to drop professor: ");
                    int professorOp = scanner.nextInt();

                    if(professorOp == 1){
                        insertProfessor();
                    } else {
                        dropProfessor();
                    }

                    showAdministrativeMenu();
                    option = scanner.nextInt();
                    break;
                case 4:
                    showAdministrativeMenu();
                    option = scanner.nextInt();
                    break;
                case 5:

                    System.out.print("Press 1 to insert or press 2 to drop student: ");
                    int studentOp = scanner.nextInt();

                    if(studentOp == 1){
                        insertStudent();
                    } else {
                        dropStudent();
                    }

                    showAdministrativeMenu();
                    option = scanner.nextInt();
                    break;
                case 6:
                    instructorTeaching();
                    showAdministrativeMenu();
                    option = scanner.nextInt();
                    break;
                case 7:
                    // try {
                    //     connect.close();
                    //     System.exit(0);
                    // } catch(IOException io){
                    //     System.err.println(io);
                    // }
                    break;
                default:
                    System.err.println("Invalid option, please enter again");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    public static void printSchedules(){

        System.out.print("Please enter the term to print schedule for: ");
        String term = scanner.next();

        String query = "SELECT CourseTitle, M.SecNum, Room, Day, StartTime, EndTIme " +
                       "FROM CS434_MeetingTime M, CS434_Section S, CS434_Course C " +
                       "WHERE M.SecNum = S.SecNum and S.Course = C.ID and M.Term = '" + term + "'";

        String result = connect.select(query, "\t", "CourseTitle", "M.SecNum", "Room", "Day", "StartTime", "EndTime");

        System.out.print(result);

    }

    public static void printCatalog(){

        System.out.print("Please enter the term to print catalog for: ");
        String term = scanner.next();

        String query = "SELECT CourseTitle, SecNum, concat(LName, ', ', FName) AS FullName, Capacity " +
                       "FROM CS434_Section S, CS434_Course C, CS434_Instructor I " +
                       "WHERE S.Course = C.ID and S.Instructor = I.ID and Term = '" + term + "'";

        String result = connect.select(query, "\t", "CourseTitle", "SecNum", "FullName", "Capacity");

        System.out.print(result);

    }

    public static void printHonorsList(){

        System.out.print("Please enter the department to print honors list for: ");
        int deptId = scanner.nextInt();

        String query = "select concat(LName, ', ', FName) AS FullName, GPA " +
                       "from CS434_Student " +
                       "where Department = " + deptId + " AND GPA > 3.8";
        String result = connect.select(query, "\t", "FullName", "GPA");

        if(result.equals("")){
            System.out.println("There are no honors students in this department");
        } else {
            System.out.print(result);
        }

    }

    public static void reportingMenu(){

        showReportingMenu();
        int option = scanner.nextInt();

        while(option != 4){

            switch(option){
                case 1:
                    printSchedules();
                    showReportingMenu();
                    option = scanner.nextInt();
                    break;
                case 2:
                    printCatalog();
                    showReportingMenu();
                    option = scanner.nextInt();
                    break;
                case 3:
                    printHonorsList();
                    showReportingMenu();
                    option = scanner.nextInt();
                    break;
                case 4:
                    // try {
                    //     connect.close();
                    //     System.exit(0);
                    // } catch(IOException io){
                    //     System.err.println(io);
                    // }
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
