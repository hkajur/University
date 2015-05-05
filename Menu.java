import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner;
    private static MysqlConnect connect;

    private static final String[] INS_ENROLLMENT_COLUMNS = {"Student", "Term", "SecNum", "Grade"};
    private static final String[] INS_COURSE_COLUMNS = {"CourseTitle", "Hours", "Department"};
    private static final String[] INS_INSTRUCTOR_COLUMNS = {"LName", "FName", "Department", "Office", "Phone", "Email"};
    private static final String[] INS_STUDENT_COLUMNS = {"LName", "FName", "Username", "Phone", "Street", "City", "State", "Zip", "Degree", "Department" };
    private static final String[] INS_SECTION_COLUMNS = {"Term", "SecNum", "Course", "Instructor", "Capacity"};

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
        System.out.print("Enter option to continue: ");
        int option = scanner.nextInt();

        while(true){

            switch(option){
                case 1:
                    studentMenu();
                    showMainMenu();
                    System.out.print("Enter option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 2:
                    administrativeMenu();
                    showMainMenu();
                    System.out.print("Enter option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 3:
                    reportingMenu();
                    showMainMenu();
                    System.out.print("Enter option to continue: ");
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
                    System.out.print("Enter option to continue: ");
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

        int status = connect.insert("CS434_Enrollment", INS_ENROLLMENT_COLUMNS, columnValues);

        if(status == -1){
            System.out.println("Mysql error during insertion");
        } else if(status == 0){
            System.out.println("Course couldn't be registered for some reason");
        } else {
            System.out.println("Course successfully Registered");
        }

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

        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during deletion");
        } else if(status == 0){
            System.out.println("No such course exists");
        } else {
            System.out.println("Student Course successfully dropped");
        }
    }

    public static void registerCourses(){

        System.out.print("Do you want to add a course? ");
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
        String result = connect.select(query, "\t");

        System.out.print(result);
    }

    public static void computeGpa(){

        System.out.print("Enter Student ID: " );
        int id = scanner.nextInt();

        String calculateQuery = "UPDATE CS434_Student " +
                                " SET GPA = ( SELECT AVG(CALCULATE_GPA(grade)) FROM CS434_Enrollment WHERE CALCULATE_GPA(grade) >= 0 AND Student = " + id + ")" +
                                " WHERE Id = " + id;

        connect.update(calculateQuery);

        String query = "SELECT GPA FROM CS434_Student WHERE id = "  + id;
        String result = connect.select(query, "");

        System.out.print("Student GPA: " + result);

    }

    public static void studentMenu(){

        showStudentMenu();
        System.out.print("Enter Student option to continue: ");
        int option = scanner.nextInt();

        while(option != 5){

            switch(option){

                case 1:
                    registerCourses();
                    //showStudentMenu();
                    System.out.print("Enter Student option to continue: ");
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

                    //showStudentMenu();
                    System.out.print("Enter Student option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 3:
                    requestTranscript();
                    //showStudentMenu();
                    System.out.print("Enter Student option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 4:
                    computeGpa();
                    //showStudentMenu();
                    System.out.print("Enter Student option to continue: ");
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
                    System.out.print("Enter Student option to continue: ");
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

        int status = connect.insert("CS434_Course", INS_COURSE_COLUMNS, columnValues);

        if(status == -1){
            System.out.println("Mysql error during insertion");
        } else if(status == 0){
            System.out.println("Course couldn't be created for some reason");
        } else {
            System.out.println("Course successfully Inserted");
        }

    }

    public static void dropCourse(){

        System.out.print("Enter the name of course to drop: ");
        String courseTitle = scanner.next();

        String query = "DELETE FROM CS434_Course WHERE CourseTitle = '" + courseTitle + "'";
        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during deletion");
        } else if(status == 0){
            System.out.println("No such course exists");
        } else {
            System.out.println("Course successfully deleted");
        }


    }

    public static void addSection(){

        System.out.print("Enter the term info: ");
        String term = scanner.next();

        System.out.print("Enter the section number: ");
        int secNum = scanner.nextInt();

        System.out.print("Enter the Course Id: ");
        int courseId = scanner.nextInt();

        System.out.print("Enter the Instructor Id: ");
        int instrId = scanner.nextInt();

        System.out.print("Enter the number of maximum students: ");
        int capacity = scanner.nextInt();

        Object[] columnValues = new Object[5];

        columnValues[0] = term;
        columnValues[1] = secNum;
        columnValues[2] = courseId;
        columnValues[3] = instrId;
        columnValues[4] = capacity;

        int status = connect.insert("CS434_Section", INS_SECTION_COLUMNS, columnValues);

        if(status == -1){
            System.out.println("Mysql error during insertion");
        } else if(status == 0){
            System.out.println("Section couldn't be created for some reason");
        } else {
            System.out.println("Section successfully Inserted");
        }
    }

    public static void dropSection(){

        System.out.print("Enter the term info: ");
        String term = scanner.next();

        System.out.print("Enter the section number: ");
        int secNum = scanner.nextInt();

        System.out.print("Enter the Course Id: ");
        int courseId = scanner.nextInt();

        String query = "DELETE FROM CS434_Section" +
                       " WHERE Term = '" + term + "'" +
                       " AND SecNum = " + secNum +
                       " AND Course = " + courseId;

        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during deletion");
        } else if(status == 0){
            System.out.println("Section couldn't be deleted for some reason");
        } else {
            System.out.println("Section successfully Deleted");
        }
    }

    public static void updateSection(){

        System.out.print("Enter the term info: ");
        String term = scanner.next();

        System.out.print("Enter the section number: ");
        int secNum = scanner.nextInt();

        System.out.print("Enter the Course Id: ");
        int courseId = scanner.nextInt();

        System.out.print("Enter the Instructor Id: ");
        int instrId = scanner.nextInt();

        System.out.print("Enter the number of maximum students: ");
        int capacity = scanner.nextInt();

        String query = "UPDATE CS434_Section" +
                       " SET Instructor = " + instrId + "," +
                       " Capacity = " + capacity +
                       " WHERE Term = '" + term + "'" +
                       " AND SecNum = " + secNum +
                       " AND Course = " + courseId;

        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during update");
        } else if(status == 0){
            System.out.println("Section couldn't be updated for some reason");
        } else {
            System.out.println("Section successfully Updated");
        }
    }

    public static void addSections(){

        System.out.print("Do you want to continue adding sections? ");
        String option = scanner.next();

        while(option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes")){
            addSection();
            System.out.print("Do you want to continue adding sections? ");
            option = scanner.next();
        }
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

        int status = connect.insert("CS434_Instructor", INS_INSTRUCTOR_COLUMNS, columnValues);

        if(status == -1){
            System.out.println("Mysql error during insertion");
        } else if(status == 0){
            System.out.println("Section couldn't be created for some reason");
        } else {
            System.out.println("Section successfully Inserted");
        }
    }

    public static void dropProfessor(){

        System.out.print("Enter the email of the professor to drop: ");
        String email = scanner.next();

        String query = "DELETE FROM CS434_Instructor" +
                       " WHERE Email = '" + email + "'";

        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during deletion");
        } else if(status == 0){
            System.out.println("No such professor exists");
        } else {
            System.out.println("Professor successfully deleted");
        }
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

        int status = connect.insert("CS434_Student", INS_STUDENT_COLUMNS, columnValues);

        if(status == -1){
            System.out.println("Mysql error during insertion");
        } else if(status == 0){
            System.out.println("Section couldn't be created for some reason");
        } else {
            System.out.println("Section successfully Inserted");
        }

    }

    public static void dropStudent(){

        System.out.print("Enter the username of the student to drop: ");
        String username = scanner.next();

        String query = "DELETE FROM CS434_Student" +
                       " WHERE Username = '" + username + "'";

        int status = connect.delete(query);

        if(status == -1){
            System.out.println("Mysql error during deletion");
        } else if(status == 0){
            System.out.println("No such student exists");
        } else {
            System.out.println("Student successfully deleted");
        }
    }

    public static void instructorTeaching(){

        String query = "SELECT Term, concat(LName, ', ', FName) as FullName, count(*) as CoursesTeaching" +
                       " FROM CS434_Section, CS434_Instructor" +
                       " WHERE CS434_Section.Instructor = CS434_Instructor.ID" +
                       " GROUP BY Term, Instructor";

        String result = connect.select(query, "\t");

        System.out.print(result);
        System.out.flush();
    }

    public static void administrativeMenu(){

        showAdministrativeMenu();
        System.out.print("Enter Admin option to continue: ");
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

                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
                    option = scanner.nextInt();

                    break;
                case 2:
                    addSections();
                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
                    option = scanner.nextInt();

                    break;
                case 3:

                    System.out.print("Press 1 to insert or press 2 to drop professor: ");
                    int professorOp = scanner.nextInt();

                    if(professorOp == 1){
                        insertProfessor();
                    } else if(professorOp == 2){
                        dropProfessor();
                    }

                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 4:

                    System.out.print("Press 1 to add, Press 2 to drop, Press 3 to Update Section: ");
                    int sectionOp = scanner.nextInt();

                    if(sectionOp == 1){
                        addSection();
                    } else if(sectionOp == 2){
                        dropSection();
                    } else if(sectionOp == 3){
                        updateSection();
                    }

                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
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

                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 6:
                    instructorTeaching();
                    //showAdministrativeMenu();
                    System.out.print("Enter Admin option to continue: ");
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
                    System.out.print("Enter Admin option to continue: ");
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

        String result = connect.select(query, "\t");

        System.out.print(result);

    }

    public static void printCatalog(){

        System.out.print("Please enter the term to print catalog for: ");
        String term = scanner.next();

        String query = "SELECT CourseTitle, SecNum, concat(LName, ', ', FName) AS FullName, Capacity " +
                       "FROM CS434_Section S, CS434_Course C, CS434_Instructor I " +
                       "WHERE S.Course = C.ID and S.Instructor = I.ID and Term = '" + term + "'";

        String result = connect.select(query, "\t");

        System.out.print(result);

    }

    public static void printHonorsList(){

        System.out.print("Please enter the department to print honors list for: ");
        int deptId = scanner.nextInt();

        String query = "SELECT concat(LName, ', ', FName) AS FullName, GPA " +
                       "FROM CS434_Student " +
                       "WHERE Department = " + deptId + " AND GPA > 3.8";

        String result = connect.select(query, "\t");

        if(result.trim().equals("")){
            System.out.println("There are no honors students in this department");
        } else {
            System.out.print(result);
        }

    }

    public static void reportingMenu(){

        showReportingMenu();
        System.out.print("Enter Reporing option to continue: ");
        int option = scanner.nextInt();

        while(option != 4){

            switch(option){
                case 1:
                    printSchedules();
                    //showReportingMenu();
                    System.out.print("Enter Reporting option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 2:
                    printCatalog();
                    //showReportingMenu();
                    System.out.print("Enter Reporting option to continue: ");
                    option = scanner.nextInt();
                    break;
                case 3:
                    printHonorsList();
                    //showReportingMenu();
                    System.out.print("Enter Reporting option to continue: ");
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
                    System.out.print("Enter Reporting option to continue: ");
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
