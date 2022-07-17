package habiba.app.model;

import habiba.app.control.ActionAndFileHandling;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class TableOfHeaders extends AbstractTableModel{
    private ArrayList<InvoiceHeader> headers;

    public TableOfHeaders(ArrayList<InvoiceHeader> headers) {
        this.headers = headers;
    }

    public TableOfHeaders() {
       
    }
    

    
    @Override
    public int getRowCount() {
        return headers.size();
    }

                  @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0 -> {
                return "No.";
            }
            case 1 -> {
                return "Date";
            }
            case 2 -> {
                return "Customer";
            }
            case 3 -> {
                return "Total";
            }
        }
        return "";
    }

    @Override
    public Object getValueAt(int row, int col) {
        InvoiceHeader header;
        header = headers.get(row);
        switch (col) {
            case 0 -> {
                return header.getInvNum();
            }
            case 1 -> {
                return ActionAndFileHandling.dateFormat.format(header.getInvDate());
            }
            case 2 -> {
                return header.getInvCutomer();
            }
            case 3 -> {
                return header.getTotal();
            }

        }
        return "";
    }
    
}
