package habiba.app.control;

import habiba.app.model.InvoiceHeader;
import habiba.app.model.InvoiceLine;
import habiba.app.model.TableOfItems;
import habiba.app.view.Launcher;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static habiba.app.control.ActionAndFileHandling.dateFormat;


public class InvoiceListener implements ListSelectionListener{

    private Launcher launcher;

    public InvoiceListener(Launcher launcher) {
        this.launcher = launcher;
    }

    public InvoiceListener() {
    }
    
    @Override
    public void valueChanged(ListSelectionEvent ev) {
        int currInv = launcher.getInvoicesData().getSelectedRow();
        if (currInv != -1){
            InvoiceHeader currSelectedInv;
            currSelectedInv = launcher.getHeaders().get(currInv);
            ArrayList<InvoiceLine> items = currSelectedInv.getInvLines();
            TableOfItems tableOfItems = new TableOfItems(items);
            launcher.getInvoiceItems().setModel(tableOfItems);
            launcher.setItems(items);
            launcher.getNameLabel().setText(currSelectedInv.getInvCutomer());
            launcher.getInvoiceLabel().setText("" + currSelectedInv.getInvNum());
            launcher.getDateLabel().setText(dateFormat.format(currSelectedInv.getInvDate()));
            launcher.getTotalLabel().setText("" + currSelectedInv.getTotal());
        }      
    }
    
}
