import  java.sql.*;
import java.util.*;

public class Studentmanagementsystem {
    private static final String URL = "jdbc:mysql://localhost:3306/Studentms";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            runMethod(connection);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void runMethod(Connection connection){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println();
            System.out.println("=== Student Management System ===");
            System.out.println("1. Enter to add Student details");
            System.out.println("2. Enter to update Student details");
            System.out.println("3. Enter to delete Student details");
            System.out.println("4. Enter to display Student details");
            System.out.println("5. Enter to Exit");
            System.out.println();
            int choice  = scanner.nextInt();

            switch (choice){
                case 1 -> Insertion(connection, scanner);
                case 2 -> Updatetion(connection, scanner);
                case 3 -> Deletion(connection, scanner);
                case 4 -> Display(connection);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }

    }

    public static void Insertion(Connection connection, Scanner scanner){
        System.out.print("Enter Student Id : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter name : ");
        String name = scanner.nextLine();

        System.out.print("Enter age : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter gender : ");
        String gender = scanner.nextLine();

        String insertQuery = "INSERT INTO studentdb (student_id, name, age, gender) VALUES(?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertQuery)){
            ps.setInt(1,id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4 , gender);
            int row = ps.executeUpdate();

            if(row > 0 ){
                System.out.println("Successfull");
            }else {
                System.out.println("Unsuccessull");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void Updatetion(Connection connection, Scanner scanner){
        System.out.println("Enter Student Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter gender: ");
        String gender = scanner.nextLine();

        String updateQuery = "UPDATE studentdb SET name = ?, age = ?, gender = ? WHERE student_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(updateQuery)){
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3 , gender);
            ps.setInt(4, id);
            int row = ps.executeUpdate();

            if(row > 0 ){
                System.out.println("Successfull");
            }else {
                System.out.println("Unsuccessfull");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void Deletion(Connection connection, Scanner scanner){
        System.out.println("Enter student id: ");
        int id = scanner.nextInt();

        String deleteQuery = "DELETE FROM studentdb WHERE student_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            if(row > 0){
                System.out.println("successfull");
            }else{
                System.out.println("Unsuccessfull");

            }        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void Display(Connection connection) {
        String displayQuery = "SELECT * FROM studentdb";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(displayQuery);

            // Pass each column header as a separate argument
            System.out.printf("%-10s %-20s %-5s %-10s%n", "Student Id", "Name", "Age", "Gender");
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("%-10s %-20s %-5s %-10s%n", id, name, age, gender);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

