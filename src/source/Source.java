package source;

import java.awt.*;

public class Source
{
    public static Font mainFont;
    public static Font dimensionFont;
    public static Font comboxFont;
    public static Font JPanelTitle;
    public static Font buttonFont;
    public static String[] algorithm;
    public static String savaPath;
    
    static {
        Source.mainFont = new Font("Times New Roman", 0, 22);
        Source.dimensionFont = new Font("Serif", 0, 20);
        Source.comboxFont = new Font("Serif", 0, 18);
        Source.JPanelTitle = new Font("Times New Roman", Font.BOLD, 22);
        Source.buttonFont = new Font("Serif", 0, 15);
        Source.algorithm = null;
    }
}
