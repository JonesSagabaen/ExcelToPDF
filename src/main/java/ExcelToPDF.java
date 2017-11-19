import javax.swing.*;

public class ExcelToPDF {

    public static void main(String[] args) {
        // Note: Order of execution is important to avoid NullPointerException
        JFrame frame = new JFrame("Excel to PDF");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new gui.Main(frame).getMainView());
        frame.setSize(750, 500);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}