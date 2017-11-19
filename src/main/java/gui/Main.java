package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import configuration.*;
import excel.ExcelReader;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import pdf.WriteToPDF;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private String classFilepath;
    private ConfigurationFile configurationFile;

    private JPanel mainView;
    private JTextField inputExcelFilepathField;
    private JTextField inputNameLookupField;
    private JTextField inputPdfFilepathField;
    private JButton chooseExcelFileButton;
    private JButton choosePdfFileButton;
    private JButton submitButton;
    private JSpinner headerRowsSpinner;
    private JPanel excelPanel;
    private JPanel pdfPanel;

    /**
     * Constructor for putting everything together for running the app.
     * All UI logic is here under their associated action listeners.
     */
    public Main(JFrame frame) {
        // Custom UI modifications
        setMenubar(frame );
        setFurtherUICustomizations();

        // Get directory from where this app is launched
        classFilepath = new File("").getAbsolutePath();
        System.out.println("Current app directory:");
        System.out.println(classFilepath);

        // Set default values for UI fields
        inputExcelFilepathField.setText(classFilepath);
        headerRowsSpinner.setValue(1);
        inputPdfFilepathField.setText(classFilepath);

        // Handle configuration file
        initializeConfigurationFile();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // Action Listeners
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Action for Excel file chooser button
        chooseExcelFileButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            File file = new File(classFilepath);
            fileChooser.setCurrentDirectory(file);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                inputExcelFilepathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Action for PDF file chooser button
        choosePdfFileButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            File file = new File(classFilepath);
            fileChooser.setCurrentDirectory(file);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                inputPdfFilepathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Action for Submit button
        submitButton.addActionListener((ActionEvent e) -> processInformation());

        // Action for selecting the inputNameLookupField
        inputNameLookupField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                inputNameLookupField.setForeground(Color.BLACK);
                inputNameLookupField.selectAll();
            }
        });

        // Action for hitting the Enter key against the inputNameLookupField
        inputNameLookupField.addActionListener((ActionEvent e) -> processInformation());
    }

    /**
     * Read existing or prompt to create a configuration file.
     */
    private void initializeConfigurationFile() {
        // Read configuration file and make it a Java object to use
        String configurationFileFilepath = classFilepath + ConfigurationFile.getFilename();
        File configurationFile = new File(configurationFileFilepath);
        if (configurationFile.exists()) {
            System.out.println("Existing configuration file found:");
            System.out.println(configurationFileFilepath);
            this.configurationFile = new ConfigurationFile(configurationFileFilepath);
        }
        // Prompt to create one if no configuration file has been found
        else {
            // TODO: Create UI for composing a configuration file
            JOptionPane.showMessageDialog(null,
                    "A valid configuration file (user.conf) is needed and must be placed in the same " +
                            "location of this .JAR application.",
                    "No Configuration File Found",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Process the information submitted by the user.
     */
    private void processInformation() {
        // Check if Excel document is found
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(inputExcelFilepathField.getText(), Integer.parseInt(headerRowsSpinner.getValue().toString()));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Excel document could not be found at given location.",
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        } catch (NotOfficeXmlFileException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid Excel document submitted.",
                    "Excel Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check potential error that occurs as a result of incorrectly entering number of header rows
        String[] namesInExcelDoc;
        try {
            namesInExcelDoc = excelReader.listMatchingNames(inputNameLookupField.getText());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    "Check whether the correct number of header rows for this " +
                            "Excel document has been indicated.",
                    "Excel Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if PDF is valid
        final String outputPdfFilename = "/output.pdf";
        WriteToPDF writeToPDF = null;
        try {
            writeToPDF = new WriteToPDF(inputPdfFilepathField.getText(), classFilepath + outputPdfFilename);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "PDF document could not be found at given location.",
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        } catch (com.itextpdf.io.IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid PDF document submitted.",
                    "PDF Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        // Show match if one result is found for name lookup
        if (namesInExcelDoc.length == 1) {
            // Excel and PDF documents are valid and proceed
            write(excelReader, writeToPDF);
        }
        // Report that no matches found if no matches found for name lookup
        else if (namesInExcelDoc.length == 0) {
            writeToPDF.closePdf();
            JOptionPane.showMessageDialog(null,
                    "No matches found.");
        }
        // Return a list of all matching names if multiple names match name lookup
        else {
            writeToPDF.closePdf();
            JOptionPane.showMessageDialog(null,
                            namesInExcelDoc,
                    "Multiple Matches",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Read Excel file and write onto the PDF file.
     *
     * @param excelReader The excelReader information source.
     */
    private void write(ExcelReader excelReader, WriteToPDF writeToPDF) {
        for (ExcelPDFConfiguration conf : configurationFile.getConfigurationFields()) {
            if (conf instanceof ConfigurationPrint) {
                ConfigurationPrint confPrint = (ConfigurationPrint) conf;
                String printString = confPrint.getPrintString();
                writeToPDF.writeTextField(printString, confPrint.getFontSize(), confPrint.getX(), confPrint.getY(), 500);
            }
            else if (conf instanceof ConfigurationLookup) {
                int rowNumberForLookup = excelReader.getRowIndex(inputNameLookupField.getText());
                ConfigurationLookup confLookup = (ConfigurationLookup) conf;
                String excelLookupResult = excelReader.lookupDetail(rowNumberForLookup, confLookup.getExcelTargetColumn());
                writeToPDF.writeTextField(excelLookupResult, confLookup.getFontSize(), confLookup.getX(), confLookup.getY(), 500);
            }
            else if (conf instanceof ConfigurationCheckbox) {
                int rowNumberForLookup = excelReader.getRowIndex(inputNameLookupField.getText());
                ConfigurationCheckbox confCheckbox = (ConfigurationCheckbox) conf;
                String excelLookupResult = excelReader.lookupDetail(rowNumberForLookup, confCheckbox.getExcelTargetColumn());
                if (confCheckbox.setCheckboxSelection(excelLookupResult)) {
                    // Note: Attempted an ASCII checkmark but wasn't printing so an X is used instead
                    writeToPDF.writeTextField("X", confCheckbox.getFontSize(), confCheckbox.getX(), confCheckbox.getY(), 500);
                }
            }
        }
        writeToPDF.generatePdf();
        System.out.println("Successfully generated PDF:");
        System.out.println(classFilepath + writeToPDF.getOutputPDF());

        JOptionPane.showMessageDialog(null,
                "PDF file generated: \n" +
                        classFilepath + writeToPDF.getOutputPDF(),
                "Successfully Composed PDF",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Method for setting up the menu bar
     * @param frame The main view of the application.
     */
    private void setMenubar(JFrame frame) {
        // MacOS specific setting to have menu in the Mac menu bar
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Creates new Menu Bar for the JFrame.
        JMenuBar menuBar = new JMenuBar();

        // Create File menu in the menubar
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.getAccessibleContext().setAccessibleDescription(
                "File menu");
        menuBar.add(menuFile);

        // About menu item
        JMenuItem menuItemAbout = new JMenuItem("About", KeyEvent.VK_A);
        menuItemAbout.getAccessibleContext().setAccessibleDescription(
                "About developer");
        menuItemAbout.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(null,
                        "Excel to PDF \n" +
                                "https://github.com/JonesSagabaen/ExcelToPDF \n" +
                                "\n" +
                                "Developer: Jones Sagabaen \n" +
                                "Build in November 10, 2017",
                        "About",
                        JOptionPane.PLAIN_MESSAGE));
        menuFile.add(menuItemAbout);

        // Exit menu item
        JMenuItem menuItemExit = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItemExit.getAccessibleContext().setAccessibleDescription(
                "Exit application");
        menuItemExit.addActionListener((ActionEvent e) -> System.exit(0));
        menuFile.add(menuItemExit);

        // Add menubar to application
        frame.setJMenuBar(menuBar);
    }

    /**
     * All further UI customizations are contained here where commenting out this method will still run the app
     * but without these customizations.
     */
    private void setFurtherUICustomizations() {
        // Excel file select button
        chooseExcelFileButton.setBackground(Color.decode("#24A3FF"));
        chooseExcelFileButton.setForeground(Color.WHITE);
        chooseExcelFileButton.setOpaque(true);
        chooseExcelFileButton.setFocusPainted(false);
        chooseExcelFileButton.setBorderPainted(false);
        chooseExcelFileButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        // PDF file select button
        choosePdfFileButton.setBackground(Color.decode("#24A3FF"));
        choosePdfFileButton.setForeground(Color.WHITE);
        choosePdfFileButton.setOpaque(true);
        choosePdfFileButton.setFocusPainted(false);
        choosePdfFileButton.setBorderPainted(false);
        choosePdfFileButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        // Submit button
        submitButton.setBackground(Color.decode("#24A3FF"));
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(true);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        // Titled borders
        TitledBorder titledExcelArea = new TitledBorder("Excel");
        excelPanel.setBorder(titledExcelArea);

        TitledBorder titledPdfArea = new TitledBorder("PDF");
        pdfPanel.setBorder(titledPdfArea);
    }

    /**
     * Getter for all contents of this UI.
     * @return              The JPanel containing all of the UI contents.
     */
    public JPanel getMainView() {
        return mainView;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainView = new JPanel();
        mainView.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainView.setMinimumSize(new Dimension(-1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(30, 30, 30, 30), -1, -1));
        mainView.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        excelPanel = new JPanel();
        excelPanel.setLayout(new GridLayoutManager(2, 1, new Insets(20, 20, 20, 20), -1, -1));
        panel1.add(excelPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        excelPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        headerRowsSpinner = new JSpinner();
        panel2.add(headerRowsSpinner, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Number of header rows in the document");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(276, 19), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        excelPanel.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Filepath to Excel document");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputExcelFilepathField = new JTextField();
        panel3.add(inputExcelFilepathField, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        chooseExcelFileButton = new JButton();
        chooseExcelFileButton.setBackground(new Color(-12828863));
        chooseExcelFileButton.setEnabled(true);
        chooseExcelFileButton.setForeground(new Color(-14869219));
        chooseExcelFileButton.setOpaque(false);
        chooseExcelFileButton.setText("Choose File...");
        chooseExcelFileButton.setMnemonic('F');
        chooseExcelFileButton.setDisplayedMnemonicIndex(7);
        panel3.add(chooseExcelFileButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pdfPanel = new JPanel();
        pdfPanel.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), -1, -1));
        panel1.add(pdfPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pdfPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Filepath to PDF document");
        panel4.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputPdfFilepathField = new JTextField();
        panel4.add(inputPdfFilepathField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        choosePdfFileButton = new JButton();
        choosePdfFileButton.setText("Choose File...");
        choosePdfFileButton.setMnemonic('F');
        choosePdfFileButton.setDisplayedMnemonicIndex(7);
        panel4.add(choosePdfFileButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(20, 0, 20, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Name search");
        panel6.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputNameLookupField = new JTextField();
        inputNameLookupField.setForeground(new Color(-4473925));
        inputNameLookupField.setText("Please enter name to lookup...");
        panel6.add(inputNameLookupField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 500, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        submitButton = new JButton();
        submitButton.setBackground(new Color(-12828863));
        submitButton.setEnabled(true);
        submitButton.setForeground(new Color(-14869219));
        submitButton.setHideActionText(false);
        submitButton.setOpaque(false);
        submitButton.setText("Submit");
        submitButton.setMnemonic('S');
        submitButton.setDisplayedMnemonicIndex(0);
        panel7.add(submitButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainView;
    }
}
