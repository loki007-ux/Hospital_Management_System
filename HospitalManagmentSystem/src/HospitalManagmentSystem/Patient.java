package HospitalManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

    }

    public void addPatients() {
        System.out.print("Enter the name of the patient= ");
        String name = scanner.next();
        System.out.print("Enter the age of the patient=  ");
        int age = scanner.nextInt();
        System.out.print("Enter the gender of the patient = ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int rowsEffected = preparedStatement.executeUpdate();
            if (rowsEffected != 0) {
                System.out.println("the data has been updated succesfully");
            } else {
                System.out.println("data has not uploaded");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        try {
            String query = "SELECT * FROM patients";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("patients ");
            System.out.println("+-----------+---------------+------+----------+");
            System.out.println("| patient id| name          |age   |  gender  |");
            System.out.println("+-----------+---------------+------+----------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-16s|%-7s|%-11s\n", id, name, age, gender);
                System.out.println("+-----------+---------------+------+----------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatient(int id) {
        try {
            String query = "SELECT * FROM patients WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}