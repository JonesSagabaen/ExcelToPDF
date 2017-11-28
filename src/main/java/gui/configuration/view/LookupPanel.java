package gui.configuration.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LookupPanel implements Observer {
    private JPanel MainView;
    private JPanel excelPanel;
    private JPanel pdfPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton removeButton;

    public LookupPanel() {
        setFurtherUICustomizations();
    }

    /**
     * Front-end container for entire panel.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }

    /**
     * All further UI customizations are contained here where commenting out this method will still run the app
     * but without these customizations.
     */
    private void setFurtherUICustomizations() {
        // Titled borders
        TitledBorder titledExcelArea = new TitledBorder("Excel");
        excelPanel.setBorder(titledExcelArea);

        TitledBorder titledPdfArea = new TitledBorder("PDF");
        pdfPanel.setBorder(titledPdfArea);

    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("[View LookupPanel] Observable: " + o.getClass());
        System.out.println("[View LookupPanel] Object passed: " + arg.getClass());
    }

    public void addController(ActionListener controller){
        System.out.println("[View LookupPanel] Adding controller");
        removeButton.addActionListener(controller);
    }
}
