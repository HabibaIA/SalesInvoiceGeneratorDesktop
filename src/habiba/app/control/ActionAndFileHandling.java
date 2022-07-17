package habiba.app.control;

import habiba.app.model.InvoiceHeader;
import habiba.app.model.TableOfHeaders;
import habiba.app.model.InvoiceLine;
import habiba.app.model.TableOfItems;
import habiba.app.view.Launcher;
import habiba.app.view.NewInvoiceWizard;
import habiba.app.view.NewLineWizard;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ActionAndFileHandling implements ActionListener {

    private NewLineWizard newLineWizard;
    
    private final Launcher launcher;
    
    private NewInvoiceWizard newInvoiceWizard;
    
   
    public ActionAndFileHandling(Launcher launcher){
        this.launcher = launcher;
    }
    
    public Launcher getLauncher(){
        return launcher;
    }
   
    
    
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void actionPerformed(ActionEvent err) {
        
        switch (err.getActionCommand()) {
            case "Create New Line":
                initLine();
                break;
            case "Load File":
                loadFile();
                break;
                
            case "Save File":
                saveFile();
                break;
                
            case "Create Invoice":
                newInvoice();
                break;
                
            case "Create New Invoice":
                startInvoiceWizard();
                break;
                
            case "Delete Invoice":
                deleteInv();
                break;    
            
            case "Cancel Invoice":
                cancelInvCreation();
                break;
                               
            case "Create Line":
                makeLine();
                break;
                
            case "Cancel Line":
                cancelLineCreation();
                
            case "Delete Line":
                deleteLineData();
                break;
        }
        
    }
    public void loadFile() {
        JFileChooser chooser1;
        chooser1 = new JFileChooser();
        try {
            int res;
            JOptionPane.showMessageDialog(launcher, "Select Headers File...", "", JOptionPane.WARNING_MESSAGE);
            res = chooser1.showOpenDialog(launcher);
            if (res == JFileChooser.APPROVE_OPTION){
                File header;
                header = chooser1.getSelectedFile();
                Path pathOfHeader;
                pathOfHeader = Paths.get(header.getAbsolutePath());

                ArrayList<InvoiceHeader> headersList;
                headersList = new ArrayList<>();
                List<String> headersText;
                headersText = Files.readAllLines(pathOfHeader);
                
                for (String headerText : headersText){
                   String[] words = headerText.split(",");
                   String firstHeader = words[0];
                   String secHeader = words[1];
                   String thirdHeader = words[2];
                   
                   int number = Integer.parseInt(firstHeader);
                   Date dateInHeader = dateFormat.parse(secHeader);
                   String customerName = thirdHeader;
                   InvoiceHeader invHeader = new InvoiceHeader(number, dateInHeader, customerName);
                   headersList.add(invHeader);
                }   
            launcher.setHeaders(headersList);
            JOptionPane.showMessageDialog(launcher, "Now select lines file...", "", JOptionPane.WARNING_MESSAGE);
            res = chooser1.showOpenDialog(launcher);
            if (res == JFileChooser.CANCEL_OPTION){
                JOptionPane.showMessageDialog(launcher, "Please Select a proper CSV File.", "", JOptionPane.ERROR_MESSAGE);
            }
            else if (res == JFileChooser.APPROVE_OPTION){
                File fileOfLine = chooser1.getSelectedFile();
                Path pathForLine = Paths.get(fileOfLine.getAbsolutePath());
 
                ArrayList<InvoiceLine> content = new ArrayList<>();
                List<String> linesList = Files.readAllLines(pathForLine);

                for (String line : linesList){
                    String[] lineWords = line.split(",");
                    String frst = lineWords[0];
                    String sec = lineWords[1];
                    String thrd = lineWords[2];
                    String frth = lineWords[3];
                    
                    int numVal;
                    numVal = Integer.parseInt(frst);
                    String itemName = sec;
                    double itemPrice = Double.parseDouble(thrd);
                    int itemCount = Integer.parseInt(frth);
                    InvoiceHeader invHeader = launcher.getNum(numVal);
                    
                    InvoiceLine invLine = new InvoiceLine(invHeader, itemName, itemPrice,itemCount);
                    invHeader.getInvLines().add(invLine); 
                }            
                launcher.setItems(content);

            }
                TableOfHeaders table1;
                table1 = new TableOfHeaders(headersList);
                launcher.setHeaderTable(table1);
                launcher.getInvoicesData().setModel(table1);                   
        }
            } catch (HeadlessException | IOException | NumberFormatException | ParseException exp) {
                JOptionPane.showMessageDialog(launcher, "Failed Loading File.\n Make sure selected file is a valid invoice CSV file.", "", JOptionPane.ERROR_MESSAGE);
            } 

        }
    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        try {
        int res = chooser.showSaveDialog(launcher);
        if (res == JFileChooser.APPROVE_OPTION){
            File headerF = chooser.getSelectedFile();
            FileWriter fileHW = new FileWriter(headerF);
            ArrayList<InvoiceHeader> headersList = launcher.getHeaders();
            
            
            String headerWords = "", records = "";
            
            for (InvoiceHeader header: headersList){
                headerWords += header.toString();
                headerWords += "\n";
                for (InvoiceLine item: header.getInvLines()){
                    records += item.toString();
                    records += "\n";
                }
            }
            
            
            File linef = chooser.getSelectedFile();
            
            FileWriter LineFW = new FileWriter(linef);

            headerWords = headerWords.substring(0, headerWords.length()-1);
            fileHW.write(headerWords);
            fileHW.close();
            
            records = records.substring(0, records.length()-1);
            LineFW.write(records);
            LineFW.close();
            
            
            JOptionPane.showMessageDialog(launcher, "Save Done","", JOptionPane.INFORMATION_MESSAGE);
            if (headersList == null) {
                throw new Exception("Headers Missing");
            }
         }      
        }

        catch (Exception exp){
            JOptionPane.showMessageDialog(launcher, "Saving Failed\n Make sure program is filled with proper data,\n and selected saving destination is accessible.", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startInvoiceWizard() {
        newInvoiceWizard = new NewInvoiceWizard(launcher);
        newInvoiceWizard.setVisible(true);
       try { 
            int invNumber = 0;
            for (InvoiceHeader header : launcher.getHeaders()){
                if (header.getInvNum()> invNumber)
                    invNumber = header.getInvNum();
            }
            invNumber++;
            newInvoiceWizard.getInvNumLabel().setText("" + invNumber);
       } 
       catch (Exception exp){
           JOptionPane.showMessageDialog(launcher,"Make sure all needed files are selected first.", "", JOptionPane.ERROR_MESSAGE);
           newInvoiceWizard.setVisible(false);
     }
    }  
    
    private void newInvoice() {
       int invoiceNum = 0;
       for (InvoiceHeader header : launcher.getHeaders()){
        if (header.getInvNum() > invoiceNum)
            invoiceNum = header.getInvNum();
        }
       invoiceNum++;
       String cutstomerName = newInvoiceWizard.getCustomerName().getText();
       String date = newInvoiceWizard.getInvDateField().getText();
       Date invDate = new Date();
       try {
           invDate = dateFormat.parse(date);
         }
       catch (ParseException exception) {
           JOptionPane.showMessageDialog(launcher, "Date entry should adhere to format: dd-MM-yyyy", "", JOptionPane.ERROR_MESSAGE);
        } 
       if (cutstomerName.length()==0){
       JOptionPane.showMessageDialog(launcher, "Name cannot be empty", "", JOptionPane.ERROR_MESSAGE);
       }
       else{
        InvoiceHeader invHeader = new InvoiceHeader(invoiceNum, invDate, cutstomerName);
        launcher.getHeaders().add(invHeader);
        launcher.getTableOfHeader().fireTableDataChanged();
        newInvoiceWizard.dispose();
        newInvoiceWizard = null;
       }   
    }

    private void cancelInvCreation(){
        newInvoiceWizard.dispose();
        newInvoiceWizard = null;
    }
    
        
    private void deleteInv() {
        int invID = launcher.getInvoicesData().getSelectedRow();
           if (invID != -1){ 
            launcher.getHeaders().remove(invID);
            launcher.getTableOfHeader().fireTableDataChanged();
            launcher.getInvoiceItems().setModel(new TableOfItems(new ArrayList<>()));
            launcher.getInvoiceLabel().setText("");
            launcher.getNameLabel().setText("");
            launcher.getDateLabel().setText("");
            launcher.getTotalLabel().setText("");
        }
    }
    
    
    private void initLine() {
        newLineWizard = new NewLineWizard(launcher);
        newLineWizard.setVisible(true);    
    }
    
    private void makeLine(){
        newLineWizard.setVisible(false);
        int currInvoice;
        currInvoice = launcher.getInvoicesData().getSelectedRow();
        String itemName = newLineWizard.getItemField().getText();
        String itemPrAsString = newLineWizard.getPrField().getText();
        String countString = newLineWizard.getCntField().getText();
        double itemPr = 0;
        int itemCnt = 0;
        try {
            itemPr = Double.parseDouble(itemPrAsString); 
            } 
        catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(launcher, "Error parsing price.", "", JOptionPane.ERROR_MESSAGE);
       } 
        try {
             itemCnt = Integer.parseInt(countString);
       } 
        catch (NumberFormatException exception){
           JOptionPane.showMessageDialog(launcher, "Error parsing count", "", JOptionPane.ERROR_MESSAGE);
       } 

       if (currInvoice != -1 && newLineWizard != null){
            InvoiceHeader invoiceNum = launcher.getHeaders().get(currInvoice);
            InvoiceLine invLine = new InvoiceLine(invoiceNum, itemName, itemPr, itemCnt);
            launcher.getItems().add(invLine);
            launcher.getTableOfHeader().fireTableDataChanged();
            launcher.getInvoicesData().setRowSelectionInterval(currInvoice, currInvoice);
            launcher.getItemsTable().fireTableDataChanged();
            newLineWizard.dispose();
            newLineWizard = null;
        }
    }

    private void cancelLineCreation() {
        newLineWizard.dispose();
        newLineWizard = null;
    }
    
    public void deleteLineData() {
        int selectedInvoice = launcher.getInvoicesData().getSelectedRow(); 
        int selectedItemIndex = launcher.getInvoiceItems().getSelectedRow();
           if (selectedItemIndex != -1){ 
            launcher.getItems().remove(selectedItemIndex);
            launcher.getTableOfHeader().fireTableDataChanged();
            launcher.getInvoicesData().setRowSelectionInterval(selectedInvoice, selectedInvoice);
            launcher.getItemsTable().fireTableDataChanged();
        }
    }
}
