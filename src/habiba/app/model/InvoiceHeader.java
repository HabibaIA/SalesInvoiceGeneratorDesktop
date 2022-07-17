package habiba.app.model;


import habiba.app.control.ActionAndFileHandling;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int invNumber;
    private String invCutomer;
    private Date invDate;

    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader() {
    }

    // constructor but with initial values
    public InvoiceHeader(int invNum, Date invDate, String invCustomer) {
        this.invNumber = invNum;
        this.invDate = invDate;
        this.invCutomer = invCustomer;
    }

    public Date getInvDate() {
        return invDate;
    }

    public int getInvNum() {
        return invNumber;
    }

    public void setInvNumber(int invNum) {
        this.invNumber = invNum;
    }
    
        public String getInvCutomer() {
        return invCutomer;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public void setInvCustomer(String invCustomer) {
        this.invCutomer = invCustomer;
    }
    
    @Override
    public String toString() {
        return invNumber + "," + ActionAndFileHandling.dateFormat.format(invDate) + "," + invCutomer;
    }
    
        public ArrayList<InvoiceLine> getInvLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }
    
    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    public double getTotal() {
        double total = 0.0;
        for (int i=0 ; i < getInvLines().size() ; i++ ){
            total += getInvLines().get(i).getItemTotal();
        }
        return total;
    }   



}