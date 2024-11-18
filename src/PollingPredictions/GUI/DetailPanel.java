package PollingPredictions.GUI;
/*
    Adds information related to the table of data
 */


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailPanel extends JPanel implements GUIConstants {
    JTextArea details;
    public final String DEFAULT_STRING = "The data from this table is gathered from a list of polls from ABC's Project 538. " +
            "Only major party nominees are considered to narrow the data. " +
            "Polls are considered regardless of partisan association. These results may not be accurate.";

    DetailPanel(ArrayList<Object[]> data, JTable data_table) {
        // set up textarea
        details = new JTextArea(DEFAULT_STRING);

        details.setFont(DEFAULT_FONT);
        details.setSize(DETAIL_PANEL_DIMENSION);
        details.setLineWrap(true);
        details.setBackground(BACKGROUND_COLOR);


        ListSelectionModel model = data_table.getSelectionModel();

        model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // display details about the currently selected item
        model.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                details.setText(Arrays.toString(data.get(e.getFirstIndex())));
                if (e.getLastIndex() == data.size() - 1) {
                    details.setText("01000110011100100110111101101101001000000111010001101000011001010010000001010010011010010111011001100101011100100010000001110100011011110010000001110100011010000110010100100000010100110110010101100001001000010010000001001000011000010111001001110010011010010111001100100000011011010111010101110011011101000010000001110011011001010111010000100000010100000110000101101100011001010111001101110100011010010110111001100101001000000110011001110010011001010110010100100001");
                }

                add(details);
            }
        });

        add(details);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }
}
