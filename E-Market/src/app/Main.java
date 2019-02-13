package app;

import net.proteanit.sql.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main  {
    private JFrame frame ;
    private JPanel table_Panel;
    private JPanel category_Panel ;
    private Connection connection = null ;
    public JLayeredPane layeredPane = null ;

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

        Font font = new Font("Arial",Font.PLAIN,16);
        JLabel phone = new JLabel("Phone : 012 34 56 78");
        phone.setFont(font);
        phone.setBounds(1000,80,200,20);
        JLabel email = new JLabel("Email : e-market@gmail.com");
        email.setFont(font);
        email.setBounds(1000,110,300,20);
        JLabel fb = new JLabel("Fb :       E-Market ");
        fb.setFont(font);
        fb.setBounds(1000,140,200,20);


        topPanel.add(phone);
        topPanel.add(email);
        topPanel.add(fb);

    }

    private void init_bottom(){
        layeredPane = new JLayeredPane();

        layeredPane.setBackground(Color.WHITE);
        layeredPane.setOpaque(true);
        layeredPane.setBounds(0,180,frame.getWidth(), frame.getHeight() - 180);
        layeredPane.add(new JLabel("Hello"));
        init_categoryPanel();
        init_tablePanel();

        layeredPane.removeAll();
        layeredPane.add(category_Panel);
//        layeredPane.add(table_Panel);
        layeredPane.revalidate();
        frame.add(layeredPane);
    }

    private void init_back_button(){
        JButton button = new JButton("");
        button.setLocation(0,0);
        button.setSize(new Dimension(35 ,35));
        button.setBackground(Color.gray);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.transferFocus();
        try{
            button.setIcon(new ImageIcon(scaleImage(new File("Image\\back.png"),(int)(button.getWidth() * 0.7),(int)(button.getHeight() * 0.7))));

        }catch (NullPointerException e){
            e.printStackTrace();
            button.setText("Back");
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layeredPane.removeAll();
                layeredPane.add(category_Panel);
                layeredPane.repaint();
                layeredPane.revalidate();
            }
        });
        table_Panel.add(button);

    }
    private void init_tablePanel(){
        table_Panel = new JPanel(null);
        table_Panel.setBounds(0,0,layeredPane.getWidth(),layeredPane.getHeight());
        layeredPane.removeAll();
        table_Panel.setLayout(null);
        JPanel table = TableLayout.getTableLayout();
        table.setBounds(0,40,layeredPane.getWidth(),layeredPane.getHeight());
//        table.setLocation(0,40);
        System.out.println(table.getWidth() + "   " + table.getHeight() );

        init_back_button();

        table.setBackground(Color.white);
        table.setOpaque(true);
        table_Panel.add(table);
        layeredPane.add(table_Panel);
    }
    private void init_categoryPanel(){
        layeredPane.removeAll();
        category_Panel = new JPanel(null);
        category_Panel.setLocation(0,0);
        category_Panel.setSize(new Dimension((int)layeredPane.getWidth(),(int)layeredPane.getHeight()));
        category_Panel.setBackground(Color.WHITE);

        Color color[] = {Color.YELLOW,Color.BLUE,Color.GREEN};
        String[] category = Category.getAll();
        CategoryLabel categoryLabel = new CategoryLabel();
        JLabel temp = new JLabel();
        short index = 0 ;
        for(short i = 0 ; i < category.length / 4 ; i++){
            for(short j = 0 ; j < 4 ; j++){
                temp = CategoryLabel.getCategoryLabel(category[index],color[i],Color.WHITE,(j * 80 + j * 220) + 70, (i*110 + i * 50)+ 40);
                temp.addMouseListener(new MouseListener(){

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.print("Click");
                        layeredPane.removeAll();
                        layeredPane.add(table_Panel);
                        layeredPane.repaint();
                        layeredPane.revalidate();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        mouseClicked(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        mouseClicked(e);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                category_Panel.add(temp);
                index++;
            }
        }

        layeredPane.add(category_Panel);
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
