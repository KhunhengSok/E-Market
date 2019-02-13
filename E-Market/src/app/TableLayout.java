package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javafx.beans.binding.ObjectBinding;
public class TableLayout {
    public static JPanel  getTableLayout(){
        JPanel frame = new JPanel();
        JPanel panelSearch = new JPanel();//80
//        JButton addButton = new JButton("Add Item");//40x120
        JTextField search = new JTextField(); //40x595
        JPanel panelCenterSearch = new JPanel(new BorderLayout());
        JButton searchButton = new JButton("Search");
        JComboBox searchCategories = new JComboBox();
        //add image to table by return a column which is we put image in and return type.class
        JTable table = new JTable(){
            public Class getColumnClass(int column) {
                return (column == 5) ? ImageIcon.class : Object.class;
            }
        };;
        table.setDefaultEditor(Object.class, null);
        JScrollPane panelTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultTableModel modelTable = new DefaultTableModel();

        JDialog dialog = new JDialog();
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel name = new JLabel("Name"); //120,40
        JTextField textName = new JTextField(); //750
        JPanel categoriesPanel = new JPanel(new BorderLayout());
        JLabel categories = new JLabel("Categories");
        JComboBox boxCategories = new JComboBox();
        JPanel pricePanel = new JPanel(new BorderLayout());
        JLabel price = new JLabel("Price");
        JTextField textPrice = new JTextField();
        JPanel qtyPanel = new JPanel(new BorderLayout());
        JLabel qty = new JLabel("Qty");
        JTextField textQty = new JTextField();
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel image = new JLabel("Image");
        JLabel imageShow = new JLabel();
        final String[] imagePath = {""};
        JButton choose = new JButton("Choose");
        JPanel savePanel = new JPanel(new BorderLayout());
        JButton save = new JButton("Save");

        //dialog
        dialog.setSize(700, 500);
        dialog.setTitle("Insert");
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        //name
        namePanel.add(name, BorderLayout.WEST);
        namePanel.add(textName, BorderLayout.EAST);
        namePanel.add(categoriesPanel, BorderLayout.SOUTH);
        name.setPreferredSize(new Dimension(120, 40));
        textName.setPreferredSize(new Dimension(dialog.getWidth() - name.getPreferredSize().width, 40));

        //categories
        String[] categoriesString = {"All Departments", "Arts & Crafts", "Automotive", "Baby", "Beauty & Personal Care" , "Computers" , "Electronics" , "Woman's Fashion" , "Men's Fashion" , "Health & Household" , "Home & Kitchen" , "Industrial & Sccientific" ,  "Sports & Outdoors" , "Tools & Home Improvement"};
        categoriesPanel.add(categories, BorderLayout.WEST);
        categoriesPanel.add(boxCategories, BorderLayout.EAST);
        categoriesPanel.add(pricePanel, BorderLayout.SOUTH);
        categories.setPreferredSize(new Dimension(name.getPreferredSize()));
        boxCategories.setPreferredSize(new Dimension(textName.getPreferredSize()));
        for (String a: categoriesString) {
            if(a != "All Departments") {
                boxCategories.addItem(a);
            }
        }

        //price
        pricePanel.add(price, BorderLayout.WEST);
        pricePanel.add(textPrice, BorderLayout.EAST);
        pricePanel.add(qtyPanel, BorderLayout.SOUTH);
        price.setPreferredSize(new Dimension(name.getPreferredSize()));
        textPrice.setPreferredSize(new Dimension(textName.getPreferredSize()));

        //qty
        qtyPanel.add(qty, BorderLayout.WEST);
        qtyPanel.add(textQty, BorderLayout.EAST);
        qtyPanel.add(imagePanel, BorderLayout.SOUTH);
        qty.setPreferredSize(new Dimension(name.getPreferredSize()));
        textQty.setPreferredSize(new Dimension(textName.getPreferredSize()));

        //image
        imagePanel.add(image, BorderLayout.NORTH);
        imagePanel.add(imageShow, BorderLayout.CENTER);
        imagePanel.add(choose, BorderLayout.SOUTH);
        image.setBorder(new EmptyBorder(0,dialog.getWidth()/2 - name.getPreferredSize().width/2 + 30,0,0));
        image.setPreferredSize(new Dimension(name.getPreferredSize()));
        imageShow.setOpaque(true);
        imageShow.setBackground(Color.lightGray);
        imageShow.setPreferredSize(new Dimension(256, 144));
        imageShow.setBorder(BorderFactory.createMatteBorder(0, dialog.getWidth()/2 - imageShow.getPreferredSize().width/2, 0, dialog.getWidth()/2 - imageShow.getPreferredSize().width/2, new JPanel().getBackground()));
        choose.setPreferredSize(new Dimension(name.getPreferredSize()));
        choose.setBorder(BorderFactory.createMatteBorder(0,dialog.getWidth()/2 - name.getPreferredSize().width/2,0,dialog.getWidth()/2 - name.getPreferredSize().width/2, new JPanel().getBackground()));

        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    Image images = new ImageIcon(path).getImage().getScaledInstance(imageShow.getPreferredSize().width, imageShow.getPreferredSize().height, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(images);
                    imageShow.setIcon(imageIcon);
                    imagePath[0] = path;
                }
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No Data");
                }
            }
        });

        //save
        savePanel.add(save, BorderLayout.EAST);
        save.setPreferredSize(name.getPreferredSize());

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String query = "SELECT id, name, categories, price FROM market";
                if(textName.getText().isEmpty() && textPrice.getText().isEmpty() && textQty.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Fill The Field");
                }
                else {
                    Operation.insertData(textName.getText(), boxCategories.getSelectedItem().toString(), textPrice.getText(), Integer.parseInt(textQty.getText()), imagePath[0]);
                    table.setModel(Operation.customModel(table));
                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    boxCategories.setSelectedIndex(0);
                    imageShow.setIcon(null);
                    frame.setVisible(true);
                    dialog.setVisible(false);
                }
            }
        });

        dialog.add(namePanel, BorderLayout.NORTH);
        dialog.add(savePanel, BorderLayout.SOUTH);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true);
                super.windowClosing(e);
            }
        });
        //dialog.setVisible(true);

        //frame
        Object[] column = {"ID", "Name", "Categories", "Price", "Qty", "Image"};
        modelTable.setColumnIdentifiers(column);
        table.setModel(modelTable);
        table.setRowHeight(144);
        //disable editor on table
        table.setDefaultEditor(Object.class, null);
        table.setModel(Operation.customModel(table));
        DefaultTableCellRenderer model = new DefaultTableCellRenderer();
        model.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount()-1; i++){
            table.getColumnModel().getColumn(i).setCellRenderer(model);
            if(i == 0 || i == 4){
                table.getColumnModel().getColumn(i).setMaxWidth(80);
            }
            else{
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
            }

        }
        //TableCellRenderer button = new Jtable
//        table.getColumn("Button").setCellRenderer(model);
        table.getColumnModel().getColumn(5).setMinWidth(256);
        table.setFont(new Font("Dialog", Font.BOLD, 22));

//        frame.setSize(1080,720);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        panelSearch.setLayout(new BorderLayout());
        //panelSearch.setPreferredSize(new Dimension(frame.getWidth(), 60));
        panelSearch.setBackground(Color.lightGray);
        panelSearch.setBorder(new EmptyBorder(10,5,10,5));
//        panelSearch.add(addButton, BorderLayout.WEST);
        panelSearch.add(panelCenterSearch, BorderLayout.CENTER);
        panelSearch.add(searchButton, BorderLayout.EAST);

        panelCenterSearch.add(search, BorderLayout.CENTER);
        panelCenterSearch.add(searchCategories, BorderLayout.WEST);

//        addButton.setPreferredSize(new Dimension(120, 40));
//        searchCategories.setMinimumSize(new Dimension(addButton.getPreferredSize()));
        for (String a: categoriesString) {
            searchCategories.addItem(a);
        }
        searchCategories.setBorder(BorderFactory.createMatteBorder(0,5,0,0, Color.LIGHT_GRAY));
//        search.setPreferredSize(new Dimension(frame.getWidth() - (addButton.getPreferredSize().width*2), addButton.getPreferredSize().height));
        search.setBorder(BorderFactory.createMatteBorder(0,0,0,5, Color.LIGHT_GRAY));
//        searchButton.setPreferredSize(new Dimension(addButton.getPreferredSize().width, addButton.getPreferredSize().height));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchCategories.getSelectedItem().toString() == "All Departments") {
                    table.setModel(Operation.search(search.getText(), "", table));
                }
                else {
                    table.setModel(Operation.search(search.getText(), searchCategories.getSelectedItem().toString(), table));
                }
            }
        });

//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dialog.setVisible(true);
//                frame.setVisible(false);
//            }
//        });

        //when using normal panel
        //panelTable.setLayout(new BorderLayout());
        //when using scollpannel
        panelTable.setBackground(Color.lightGray);
        //panelTable.add(table, BorderLayout.CENTER);
        //panelTable.add(table.getTableHeader(), BorderLayout.NORTH);

        frame.add(panelSearch, BorderLayout.NORTH);
        frame.add(panelTable, BorderLayout.CENTER);

        frame.setVisible(true);
        return frame;
    }






    }

