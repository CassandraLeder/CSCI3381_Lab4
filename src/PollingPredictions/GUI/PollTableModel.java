package PollingPredictions.GUI;

/*
    Creates the logic for the JTable that displays our polling data
 */

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Arrays;

import static PollingPredictions.PollStructure.*;

public class PollTableModel implements TableModel {
    final int column_count;
    final int row_count;
    final String[] COLUMNS = final_data_headers.keySet().toArray(new String[0]);
    Object[][] data;
    ArrayList<TableModelListener> listeners;


    public PollTableModel(ArrayList<Object[]> data) {
        column_count = COLUMNS.length;
        row_count = data.size();

        // get ready to convert ArrayList<Object[]> data into Object[][] this.data
        this.data = new Object[data.size()][COLUMNS.length];

        // copy from data into this.data
        for (int i = 0; i < data.size(); i++) {
            // if object is a string array
            if (data.get(i) instanceof String[] buffer) {
                // parse based on comma, [] still present in first and last string
                String[] fields = Arrays.toString(buffer).split(STANDARD_DELIMITER);

                // remove [] from first and last string
                fields[0] = fields[0].replace("[", "");
                fields[fields.length - 1] = fields[fields.length - 1].replace("]", "");

                System.arraycopy(fields, 0, this.data[i], 0, fields.length);
            }
        }

        listeners = new ArrayList<>();
    }

    @Override
    public int getRowCount() {return row_count;}
    @Override
    public int getColumnCount() {return column_count;}
    @Override
    public String getColumnName(int column) { return new_data_headers.get(column); }
    @Override
    public Class<?> getColumnClass(int columnIndex) { return getValueAt(0, columnIndex).getClass(); }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { return data[rowIndex][columnIndex]; }

    // in this instance, one should not be able to change the data
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener l) { listeners.add(l); }

    @Override
    public void removeTableModelListener(TableModelListener l) { listeners.remove(l); }

    // this function could be more efficient/general
    public void filter(ArrayList<JCheckBox> checkBoxes, TableRowSorter<TableModel> sorter) {
        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();
        RowFilter<TableModel, Object> filter = null;

        // only show HarrisX polls
        if (checkBoxes.getFirst().isSelected()) {
            filters.add(RowFilter.regexFilter("HarrisX"));
        } // only show percentages greater than 50%
        else if (checkBoxes.get(1).isSelected()) {
            filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, 50));
        }
        // only show polls that predict trump will win
        else if (checkBoxes.getLast().isSelected()) {
            filters.add(RowFilter.regexFilter("Trump"));
        }

        RowFilter<TableModel, Object> groupFilter = RowFilter.orFilter(filters);
        sorter.setRowFilter(groupFilter);
        sorter.sort();
    }
}