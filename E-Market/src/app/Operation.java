package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.sql.*;


public class Operation {
    public static void insertData(String NAME, String CATEGORIES, String PRICE, int QTY, String PATH){
        String sqlQuery = "INSERT INTO market (NAME, CATEGORIES, PRICE, QTY, IMAGE) VALUES (?, ?, ?, ?, ?);";
        byte[] IMG = null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            if(!PATH.isEmpty()) {
                File imgpath = new File(PATH);
                InputStream IMAGE = new FileInputStream(imgpath);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = IMAGE.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
                IMG = bos.toByteArray();
            }
            statement.setString(1, NAME);
            statement.setString(2, CATEGORIES);
            statement.setString(3, PRICE);
            statement.setInt(4, QTY);
            statement.setBytes(5, IMG);
            statement.executeUpdate();
            System.out.println("Data has been inserted");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR file: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR read: " + e.getMessage());
        }
    }

    public static ImageIcon getImage(){
        ImageIcon format = null;
        try {
            Connection con = Database.getConnection();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT id, name, categories, price, qty, image FROM market");
            if(result.isBeforeFirst()){
                while (result.next()){
                    byte[] photo = result.getBytes("IMAGE");
                    Image resize = new ImageIcon(photo).getImage().getScaledInstance(256, 144, Image.SCALE_SMOOTH);
                    format = new ImageIcon(resize);
                }
                System.out.println("Data Model has been generated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR model: " + e.getMessage());
        }
        return format;
    }

    public static DefaultTableModel customModel(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = "SELECT id, name, categories, price, qty, image FROM market";
        model.setRowCount(0);
//        String[] column = {"ID", "Name", "Categories", "Price", "Qty", "Image"};
//        Object[][] dataObject = new Object[0][];
        try {
            Connection con = Database.getConnection();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            if(result.isBeforeFirst()){
                while (result.next()){
                    byte[] photo = result.getBytes("IMAGE");
                    if(photo != null) {
                        Image resize = new ImageIcon(photo).getImage().getScaledInstance(256, 144, Image.SCALE_SMOOTH);
                        ImageIcon format = new ImageIcon(resize);
                        Object[] dataObject = {result.getInt("ID"), result.getString("NAME"), result.getString("CATEGORIES"), result.getFloat("PRICE") + " $", result.getInt("QTY"), format};
                        model.addRow(dataObject);
                    }
                    else{
                        Object[] dataObject = {result.getInt("ID"), result.getString("NAME"), result.getString("CATEGORIES"), result.getFloat("PRICE") + " $", result.getInt("QTY"), null};
                        model.addRow(dataObject);
                    }
                }
                System.out.println("Data Model has been generated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR model: " + e.getMessage());
        }
        return model;
    }

    public static DefaultTableModel search(String SEARCH, String CATEGORIES, JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = "SELECT * FROM market WHERE name LIKE ? AND categories LIKE ?";
        model.setRowCount(0);
//        String[] column = {"ID", "Name", "Categories", "Price", "Qty", "Image"};
//        String[] column = {"ID", "Name", "Categories", "Price", "Qty", "Image"};
//        Object[][] dataObject = new Object[0][];
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, "%" + SEARCH + "%");
            statement.setString(2, "%"  + CATEGORIES + "%");
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst()){
                while (result.next()){
                    byte[] photo = result.getBytes("IMAGE");
                    if(photo != null) {
                        Image resize = new ImageIcon(photo).getImage().getScaledInstance(256, 144, Image.SCALE_SMOOTH);
                        ImageIcon format = new ImageIcon(resize);
                        Object[] dataObject = {result.getInt("ID"), result.getString("NAME"), result.getString("CATEGORIES"), result.getFloat("PRICE") + " $", result.getInt("QTY"), format};
                        model.addRow(dataObject);
                    }
                    else{
                        Object[] dataObject = {result.getInt("ID"), result.getString("NAME"), result.getString("CATEGORIES"), result.getFloat("PRICE") + " $", result.getInt("QTY"), null};
                        model.addRow(dataObject);
                    }
                }
                System.out.println("Data Model has been generated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR model: " + e.getMessage());
        }
        return model;
    }

}
