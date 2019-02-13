package app;

import javax.swing.*;
import java.io.File;
import java.sql.*;

public class Database {
    static Connection con = null;
    static String sqliteServer = "jdbc:sqlite:";
    static String resetPath = "";

    public static boolean isDatabaseExist(String DBFilePath){
        File DBFile = new File(DBFilePath);
        return DBFile.exists();
    }

    public static Connection getConnection(){
        String getFilePath = new File("").getAbsolutePath();
        //concat add together
        //we can add more direction file on concat to add where folder is
        //EX: String fileAbsolutePath = getFilePath.concat("\\src\\");
        String fileAbsolutePath = getFilePath.concat("\\Market.db");
        if(isDatabaseExist(fileAbsolutePath)){
            try {
                con = DriverManager.getConnection(sqliteServer+fileAbsolutePath);
                System.out.println("Database already have and Connected");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error isn't have: " + e.getMessage());
            }
        }
        else{
            createNewDatabase("", "Market");
            try {
                con = DriverManager.getConnection(sqliteServer+fileAbsolutePath);
                System.out.println("Database have been create and connected");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error Create in con: " + e.getMessage());
            }
        }
        return con;
    }

    public static void createNewDatabase(String fileSubFolder, String fileName){
        String getFilePath = new File("").getAbsolutePath();
        String fileAbsolutePath = "";
        if(fileSubFolder.isEmpty()){
            fileAbsolutePath = getFilePath.concat("\\" + fileName + ".db");
            resetPath = fileAbsolutePath;
        }
        else{
            fileAbsolutePath = getFilePath.concat("\\" + fileSubFolder + "\\" + fileName + ".db");
            resetPath = fileAbsolutePath;
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(sqliteServer+resetPath);
            if(conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                try {
                    Statement statment = conn.createStatement();
                    statment.executeUpdate("CREATE TABLE market (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, CATEGORIES TEXT NOT NULL, PRICE REAL NOT NULL, QTY INTEGER NOT NULL, IMAGE BLOB )");
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null,"Error Create Table: " + e1.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error Create in function: " + e.getMessage());
        }

    }
}
