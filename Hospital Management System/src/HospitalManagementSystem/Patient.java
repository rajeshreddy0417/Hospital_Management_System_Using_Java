package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Patient {
    private Connection connection;

    private Scanner sc;

    public Patient(Connection connection,Scanner sc){
        this.connection=connection;
        this.sc=sc;

    }

    //creating a method to add patients
    public void addPatient(){
        System.out.println("Enter Patient name : ");
        String name=sc.next();
        System.out.println("Enter Patient age : ");
        int age=sc.nextInt();
        System.out.println("Enter Patient Gender : ");
        String gender=sc.next();

        try{
            String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows=preparedStatement.executeUpdate();//returns how many get updated
            //checking the update through affected rows
            if(affectedRows>0){
                System.out.println("Patient added Successfully...!!!");
            }
            else{
                System.out.println("Failed to add Patient details...???");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    //view Patient
    public void viewPatient(){
        String query="select * from patients";

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultset=preparedStatement.executeQuery();
            //trying to print the data in good format
            System.out.println("Patients : ");
            System.out.println("+------------+-----------------+---------+-----------+");
            System.out.println("| Patient Id | Name            | Age     | Gender    |");
            System.out.println("+------------+-----------------+---------+-----------+");
            while(resultset.next()){
                int id=resultset.getInt("id");
                String name=resultset.getString("name");
                int age=resultset.getInt("age");
                String gender=resultset.getString("gender");
                System.out.printf("|%-12s|%-17s|%-9s|%-11s|\n",id,name,age,gender);
                System.out.println("+------------+-----------------+---------+-----------+");
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //get patient by id...to check exists
    public boolean getPatientById(int id){
        String query="SELECT * FROM patients WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){//if data exist return true else false
                return true;
            }
            else{
                return false;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


}
