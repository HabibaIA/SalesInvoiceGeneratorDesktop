package habiba.app.model;


public class InvoiceLine {
    private InvoiceHeader header;
    private String itemName;
    private double itemPrice;
    private int itemCount;

    public InvoiceLine() {
    }

    public InvoiceLine(InvoiceHeader header, String itemName, double itemPrice, int itemCount) {
        this.header = header;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public double getItemTotal(){
        return itemPrice*itemCount;
    }

    @Override
    public String toString() {
        return header.getInvNum() + "," + itemName + "," + itemPrice + "," + itemCount;
    }

    
    
    
    
    
}