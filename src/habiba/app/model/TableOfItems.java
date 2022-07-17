package habiba.app.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableOfItems extends AbstractTableModel {
    private ArrayList<InvoiceLine> items;

    public TableOfItems(ArrayList<InvoiceLine> lines) {
        this.items = lines;
    }
    
    
    public TableOfItems() {
    }
    
    

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int row, int col) {
      InvoiceLine invLine = items.get(row);
        switch (col) {
            case 0 -> {
                return row + 1 ;
            }
            case 1 -> {
                return invLine.getItemName();
            }
            case 2 -> {
                return invLine.getItemPrice();
            }
            case 3 -> {
                return invLine.getItemCount();
            }
            case 4 -> {
                return invLine.getItemTotal();
            }
        }
        return "";
    }
    
    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0 -> {
                return "No.";
            }
            case 1 -> {
                return "Item Name";
            }
            case 2 -> {
                return "Item Price";
            }
            case 3 -> {
                return "Count";
            }
            case 4 -> {
                return "Item Total";
            }
        }
        return "";
    }
    
    
    
    
    
    
}
