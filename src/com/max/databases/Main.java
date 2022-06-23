package com.max.databases;


import org.apache.derby.impl.store.raw.log.Scan;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static DatabaseHandler handler;
    public static void addMember(String id, String name, String email, String nickName){ // Add members to member table
        String qu = "INSERT INTO MEMBER VALUES (" +
                "'" + id + "'," +
                "'" + name + "'," +
                "'" + email + "'," +
                "'" + nickName + "')";
        handler.execAction(qu);
    }


    public static void retrieveInfoMember() { // Retrieve info from member table
        String qu = "SELECT * FROM MEMBER";
        ResultSet resultSet = handler.execQuery(qu);
        try{
            while (resultSet.next()){
                String id = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                System.out.println("Entry: ID" + id + "\tName: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void retrieveFile(String table) { // Retrieve info from the table given by user

//table selects directory -> if directory path is valid, then retrieve data from directory
        String qu = "SELECT * FROM "+ table;
        ResultSet resultSet = handler.execQuery(qu);
        try{
            while (resultSet.next()){
                String name = resultSet.getString("NAME");
                String path = resultSet.getString("PATH");
                String extension = resultSet.getString("EXTENSION");
                String fileSize = resultSet.getString("FILE_SIZE");
                System.out.println("File Name: " + name + "\t| File Path: " + path + "\t| File Extension: "+ extension+"\t| File Size: " + fileSize +" bytes");
            }
        } catch (SQLException e) {
            System.out.println("There is no such table.");
//            e.printStackTrace();
        }
    }

    public static void addFiles(String name, String path, String extension, String fileSize){ // Add files infos from given folder through its absolute path
//insert data from table -> mysql database

        String qu = "INSERT INTO "+ DatabaseHandler.name +" (name, path, extension, file_size) VALUES (" +
                "'" + name + "'," +
                "'" + path + "'," +
                "'" + extension + "'," +
                "'" + fileSize + "')";
        handler.execAction(qu);
    }

    public static void main(String[] args) {
        Scanner Obj = new Scanner(System.in);
        System.out.println("Enter 1 to create a new table / Enter 2 to retrieve information: ");
        if (Obj.nextLine().equals("1")) { // Option for user to either input the absolute path of the folder and create table or check the old SQLs.
            System.out.println("Enter the absolute path of your folder: ");
            String p = Obj.nextLine();
            File folder = new File(p);
            DatabaseHandler.name= folder.getName();
            handler = DatabaseHandler.getHandler(); // Set up handler through folder name
            ReadFolder.ReadFiles(folder);
        } else {
            System.out.println("Enter your folder name to retrieve your information: ");
            String tableName = Obj.nextLine();
            handler = DatabaseHandler.getHandler(); // Set up a null handler since no folder name is given
            retrieveFile(tableName);
        }

    }
}
