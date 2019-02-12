package app;

import net.proteanit.sql.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main  {
    private JFrame frame ;
    private JPanel table_Panel;
    private JPanel category_Panel ;
    private Connection connection = null ;

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        Main main = new Main();
        main.frame.setVisible(true);

    }

    Main(){
        initialize();
    }

    private void initialize(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1280,720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("E-Market");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Image\\logo.png"));
        init_top();
        init_bottom();
    }


    private void init_top(){
        JPanel topPanel = new JPanel( null);
        topPanel.setBounds(0,0,frame.getWidth(),180);
        topPanel.setBackground(new Color(230,230,230));
        topPanel.setOpaque(true);

        //Logo
        JLabel logo = new JLabel();
        File file = new File("Image\\logo.png");
        try{
            logo.setIcon(new ImageIcon(scaleImage(file,300,75)));
        }catch (NullPointerException e){
            e.printStackTrace();
            logo.setFont(new Font("Helvetic",Font.PLAIN,32));
            logo.setText("No Image");
        }
        logo.setSize(300,75);
        logo.setLocation((int)(frame.getWidth() / 2 - logo.getWidth() / 2),(int) (topPanel.getHeight()/2 - logo.getHeight()/2 ) );
        topPanel.add(logo);
        frame.add(topPanel);

    }

    private void init_bottom(){
        JLayeredPane layeredPane = new JLayeredPane();

        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        layeredPane.setBounds(0,180,frame.getWidth(), frame.getHeight() - 180);
        layeredPane.add(new JLabel("Hello"));
        category_Panel = new JPanel(null);
        category_Panel.setBounds(0,0,layeredPane.getWidth(),layeredPane.getHeight());
        category_Panel.setBackground(Color.WHITE);
        category_Panel.add(newCategory("Phnoe Case",Color.YELLOW,Color.WHITE,80,20));
//        layeredPane.add(category_Panel);

        table_Panel = new JPanel(null);
        table_Panel.setBounds(layeredPane.getBounds());
        table_Panel.setBackground(Color.white);
        table_Panel.setOpaque(true);
//        table_Panel.add(table);
//        table_Panel.add(table_scrollPane);
//        table_Panel.setVisible(true);
//        frame.getContentPane().add(table_Panel);
//        layeredPane.add(table_Panel);

        layeredPane.removeAll();
        category_Panel.add(new JLabel("hello word"));
        layeredPane.add(category_Panel);
        layeredPane.repaint();
        layeredPane.revalidate();
        frame.add(layeredPane);
    }

    private JLabel newCategory(String Name, Color backgroundColor, Color foregroundColor, int X, int Y){
        JLabel category = new JLabel(Name);
        category.setBounds(X, Y, 250, 300);
        category.setHorizontalAlignment(JLabel.CENTER);
        category.setFont(new Font("Arial", Font.PLAIN, 40));
        category.setForeground(foregroundColor);
        category.setOpaque(true);
        category.setBackground(backgroundColor);
        return category;
    }

    private void table(){
        JTable table = new JTable();
        table.setBounds(table_Panel.getBounds());
        table.setGridColor(Color.gray);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        File file = new File("Resources\\Market.db");
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath().replace("\\","\\\\"));
            String sqlCommand = "Select * from market";
            ResultSet resultSet = connection.prepareStatement(sqlCommand).executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Can not connect to database");
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
//        table.setEdit
//        table.setModel(new Tabl)
//        JScrollPane table_scrollPane = new JScrollPane(table);
//        table_scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        table_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        table.setModel(tableModel);
//        table.getColumnModel().getColumn(0).setWidth(10);
//        table.getColumnModel().getColumn(1).setWidth(10);
//        table.getColumnModel().getColumn(2).setWidth(10);
//        table.getColumnModel().getColumn(3).setWidth(10);
//        table.getColumnModel().getColumn(4).setWidth(10);
        table.setDefaultEditor(Object.class,null);
//        table_Panel.add(new JLabel(" HI "));
    }

    private Image scaleImage(File image, int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            return bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
