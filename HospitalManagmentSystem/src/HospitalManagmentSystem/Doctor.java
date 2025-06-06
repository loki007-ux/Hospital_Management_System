package HospitalManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }


    public void viewdoctor() {
        String query = "select * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("doctors");
            System.out.println("+-----------+--------------+------------------+");
            System.out.println("| doctor id | name         | specialization   |");
            System.out.println("+-----------+--------------+------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-12s|%-15s|%-19s\n" , id,name,specialization );
                System.out.println("+-----------+--------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean getDoctorId(int id) {
        try {
            String query = "SELECT * FROM doctors WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
