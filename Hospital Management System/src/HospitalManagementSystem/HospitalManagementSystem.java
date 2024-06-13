package HospitalManagementSystem;


import java.sql.*;
import java.util.Scanner;

//main driver class
public class HospitalManagementSystem {
      //making our database credentials making private or static
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Rajesh@0417";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,sc);
            Doctor doctor=new Doctor(connection);

            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient :");
                System.out.println("2. View patients :");
                System.out.println("3. View Doctors :");
                System.out.println("4. Book Appointments :");
                System.out.println("5. Exit :");
                System.out.println("enter your choice : -");

                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        //add patients
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view patients
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //view doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book appointments
                        BookAppointment(patient,doctor,connection,sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank you for using Hospital Management System...");
                        return;
                    default:
                        System.out.println("enter valid choice :");
                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void BookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner sc){
        System.out.println("Enter Patient Id :");
        int patientId=sc.nextInt();
        System.out.println("Enter doctor Id :");
        int doctorId=sc.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD):");
        String appointmentDate=sc.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvalablity(doctorId,appointmentDate,connection)){
                String appointmentQuery="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("appointment Booked");
                    }
                    else{
                        System.out.println("appointment booking Fails");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist");
        }

    }

    public static boolean checkDoctorAvalablity(int doctorId,String appointmentDate,Connection connection){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
