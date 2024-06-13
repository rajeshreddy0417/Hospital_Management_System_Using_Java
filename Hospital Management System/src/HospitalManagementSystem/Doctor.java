package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;



    public Doctor(Connection connection){
        this.connection=connection;


    }



    //view Patient
    public void viewDoctors(){
        String query="select * from doctors";

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultset=preparedStatement.executeQuery();
            //trying to print the data in good format
            System.out.println("Doctors : ");
            System.out.println("+------------+-----------------+--------------------+");
            System.out.println("| Doctor Id  | Name            | Specialization     |");
            System.out.println("+------------+-----------------+--------------------+");
            while(resultset.next()){
                int id=resultset.getInt("id");
                String name=resultset.getString("name");
                String specialization=resultset.getString("specialization");
                System.out.printf("|%-12s|%-17s|%-20s|\n",id,name,specialization);
                System.out.println("+------------+-----------------+--------------------+");

            }

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //get doctor by id...to check exists
    public boolean getDoctorById(int id){
        String query="SELECT * FROM doctors WHERE id=?";
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
