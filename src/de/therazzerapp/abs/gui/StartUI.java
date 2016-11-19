package de.therazzerapp.abs.gui;

import de.therazzerapp.abs.ABS;
import de.therazzerapp.abs.filefilter.PDFFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class StartUI {
    private JPanel mainPanel;
    private JLabel logojmage;
    private JButton choosePDF;
    private JLabel copyright;
    private static JFrame frame = new JFrame("StartUI");

    public static void run() {
        frame.setContentPane(new StartUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(472,270);
        frame.setTitle("ISW MB-Skript v." + ABS.getVersion());
    }

    public StartUI() {

        copyright.setText("ISW \u00a9 2016");

        JFileChooser chooser = new JFileChooser();

        PDFFilter pdfFilter = new PDFFilter();
        chooser.setDialogTitle("Wähle eine PDF");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(pdfFilter);
        chooser.setFileFilter(pdfFilter);
        chooser.setAcceptAllFileFilterUsed(false);

        choosePDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.showDialog(frame,null);
                ABS.run(chooser.getSelectedFile());
            }
        });
    }
}
