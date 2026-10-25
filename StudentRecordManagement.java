import java.io.*;
import java.util.ArrayList;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    String name;
    String course;
    double marks;

    Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("--------------------");
    }
}

public class StudentRecordManagement {
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE = "student_records.ser";

    static void saveRecords() {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(students);
            System.out.println("Records saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving records.");
        }
    }

    @SuppressWarnings("unchecked")
    static void loadRecords() {
        File file = new File(FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(file))) {
            students = (ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading records.");
        }
    }

    static void addStudent(BufferedReader br) throws IOException {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(br.readLine());

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student ID already exists.");
                return;
            }
        }

        System.out.print("Enter student name: ");
        String name = br.readLine();

        System.out.print("Enter course: ");
        String course = br.readLine();

        System.out.print("Enter marks: ");
        double marks = Double.parseDouble(br.readLine());

        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        students.add(new Student(id, name, course, marks));
        saveRecords();
        System.out.println("Student added successfully.");
    }

    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    static void searchStudent(BufferedReader br)
            throws IOException {
        System.out.print("Enter student ID to search: ");
        int id = Integer.parseInt(br.readLine());

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student found:");
                s.display();
                return;
            }
        }

        System.out.println("Student not found.");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));

        loadRecords();

        while (true) {
            System.out.println("\nSTUDENT RECORD MANAGEMENT");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        addStudent(br);
                        break;
                    case 2:
                        displayStudents();
                        break;
                    case 3:
                        searchStudent(br);
                        break;
                    case 4:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter valid numeric input.");
            }
        }
    }
}