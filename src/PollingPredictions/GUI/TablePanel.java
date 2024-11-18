package PollingPredictions.GUI;

/*
    This panel will hold the JTable with all relevant data
    Also has logic to filter JTable
 */

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;

public class TablePanel extends JScrollPane implements GUIConstants {
    public JTable dataTable;
    public JPanel filterPanel;
    private final String[] FILTERS = {"Only Show HarrisX Polls", "Only Show Polls with Percentage >50%",
            "Only Show Polls which Predict Trump as Winner"};
    public JScrollPane scrollPane;
    public ArrayList<JCheckBox> checkBoxes;

    TablePanel(PollTableModel data) {
        // set up table
        dataTable = new JTable();
        dataTable.setModel(data);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(data);
        dataTable.setRowSorter(sorter);
        dataTable.setFont(DEFAULT_FONT);
        dataTable.setAutoCreateRowSorter(true); // enable column sorting

        for (int column_index = 0; column_index < dataTable.getColumnCount(); column_index++) {
            TableColumn column = dataTable.getColumnModel().getColumn(column_index);
            column.sizeWidthToFit();
        }

        // set up checkBoxes
        checkBoxes = new ArrayList<>();
        for (String filter : FILTERS)
            checkBoxes.add(new JCheckBox(filter));

        // set up filters
        filterPanel = new JPanel();

        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setFont(DEFAULT_FONT);
            checkBox.setBackground(BACKGROUND_COLOR);
            checkBox.setVisible(true);
            checkBox.addItemListener(e -> data.filter(checkBoxes, sorter));
        }

        // set up scroll pane
        scrollPane = new JScrollPane(dataTable);
        scrollPane.setPreferredSize(SCROLL_PANE_DIMENSION);
        dataTable.setFillsViewportHeight(true);

        add(filterPanel);
        super.setPreferredSize(TABLE_PANEL_DIMENSION);
        super.setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }
}
