package HospitalManagmentSystem;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.net.StandardSocketOptions;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;

public class HospitalManagmentSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="root";

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("HospitalManagmentSystem ");
                System.out.println("1. Add paitent  = ");
                System.out.println("2. Veiw patient = " );
                System.out.println("3. Veiw Doctor = ");
                System.out.println("4. Book appointment= ");
                System.out.println("5. Exit= ");
                System.out.println("enter your choice = ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatients();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewdoctor();
                        break;
                    case 4:
                         bookAppointment(patient,doctor,connection,scanner);
                         break;
                    case 5:
                        System.out.println("THANKYOU FOR USING THIS SYSTEM");
                        return;
                    default:
                        System.out.println("enter a valid choice");
                        break;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter patient id = ");
        int patient_id=scanner.nextInt();
        System.out.println("Enter the doctor id = ");
        int doctor_id=scanner.nextInt();
        System.out.println("Enter the date (YYYY-MM-DD)");
        String appointmentdate =scanner.next();
        if(patient.getPatient(patient_id) && doctor.getDoctorId(doctor_id)){
            if(checkDoctorAvailability(doctor_id,appointmentdate,connection)){
                String appointmentQuery=("INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)");
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointmentdate);
                    int effectedRows=preparedStatement.executeUpdate();
                    if(effectedRows != 0 ){
                        System.out.println("appointment booked");
                    }else {
                        System.out.println("appointment didnot book");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("doctor or patient not available");
        }
}
public static boolean checkDoctorAvailability(int doctor_id,String appointmentdate,Connection connection){
    String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";

    try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if(count== 0){
                    return  true;
                }else {
                    return  false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
}

}
