package de.therazzerapp.abs.gui;

import de.therazzerapp.abs.ABS;
import de.therazzerapp.abs.Utils;
import de.therazzerapp.abs.filefilter.PDFFilter;
import de.therazzerapp.abs.manager.MitarbeiterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
    private JCheckBox keepTXT;
    private JLabel errorLabel;
    private JPanel openPanel;
    private JPanel infoPanel;
    private JLabel mitarbeiterCount;
    private JButton backButton;
    private JLabel tableName;
    private static JFrame frame = new JFrame("StartUI");

    public static void run() {
        frame.setContentPane(new StartUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(472,290);
        frame.setTitle("ISW MB-Skript v." + ABS.getVersion() + " by Paul Koenig");

        URL resource1 = ABS.class.getResource( "images/icon_128.png" );
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(resource1));
    }

    public StartUI() {
        if (!Utils.pdftotxtExists()){
            errorLabel.setText("Error: pdftotext.exe wurde nicht gefunden");
            choosePDF.setEnabled(false);
            keepTXT.setVisible(false);
        }

        copyright.setText("Industrieschutz Walter \u00a9 2016");

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
                ABS.run(chooser.getSelectedFile(), keepTXT.isSelected());
                openPanel.setVisible(false);
                infoPanel.setVisible(true);
                mitarbeiterCount.setText(MitarbeiterManager.mitarbeiterSize() + "");
                tableName.setText(chooser.getSelectedFile().getPath().replaceAll("\\.pdf",".csv"));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPanel.setVisible(true);
                infoPanel.setVisible(false);
                mitarbeiterCount.setText("");
                tableName.setText("");
            }
        });
    }
}
