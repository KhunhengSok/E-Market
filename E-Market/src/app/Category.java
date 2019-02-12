package app;

import javax.swing.table.TableModel;

public class Category {
    static String ACCESSORY = "Accessory";
    static String ARTS_AND_CRAFTS = "Arts & Crafts";
    static String AUTOMOTIVE = "Automotive";
    static String BABY_STORE = "BaBy Store";
    static String BEAUTY = "Beauty";
    static String Computer = "Computers";
    static String ELECTRONIC = "Electronics";
    static String WOMEN = "Women";
    static String MEN = "Men";
    static String HOUSEHOLD = "Household";
    static String SPORT = "Sport";
    static String TOOL = "Tool";


    public static String[] getAll(){
        return new String[]{ACCESSORY,ARTS_AND_CRAFTS,AUTOMOTIVE,BABY_STORE,BEAUTY,Computer,ELECTRONIC,WOMEN,MEN,HOUSEHOLD,SPORT, TOOL};
    }
}
