package com.bluebee.smartsupply.client;


import com.bluebee.smartsupply.dao.OrderFileDao;
import com.bluebee.smartsupply.model.Order;
import com.bluebee.smartsupply.model.OrderFile;
import com.bluebee.smartsupply.model.OrderItem;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.S3Object;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Component
public class GenerateInvoice {

    @Autowired
    AmazonS3 client;

    @Value("${cos.bucketname}")
    private String bucketname;

    @Autowired
    OrderFileDao orderFileDao;

    OrderFile orderFile=null;

    private void putObject(String filename,File file){
        client.putObject(bucketname,filename,file);
    }

    public OrderFile generate(Map hashmap){

        Document document = new Document();
        try
        {

            File file=File.createTempFile("Invoice", ".pdf");//new  //File("/home/naduvan/Music/ideaprod/smartsupply/src/main/resources/static/AddTableExample.pdf");
            //file.mkdirs();
            file.createNewFile() ;
            FileOutputStream fileOutputStream=     new FileOutputStream(file);

            PdfWriter writer = PdfWriter.getInstance(document,fileOutputStream );
            document.open();
            Paragraph paragraph= new Paragraph("Invoice");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            Order order=(Order) hashmap.get("order");
            String s1=(String)hashmap.get("deliveryaddress");
            String s2=(String)hashmap.get("shopaddress");
            Paragraph address = new Paragraph("Delivery address:\n "+s1.replaceAll("@@","\n,"));
            Paragraph shopaddress = new Paragraph("Shop address:\n "+s2.replaceAll("@@","\n,"));

            document.add(address);
            document.add(new Paragraph("            "));
            document.add(shopaddress);

            PdfPTable table = new PdfPTable(4); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {0.5f,1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = getPdfPCell("Sno");
            PdfPCell cell2 = getPdfPCell("Item");
            PdfPCell cell3 = getPdfPCell("Quantity");
            PdfPCell cell4 = getPdfPCell("Price");

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            List<OrderItem> listOrderItmes=(List<OrderItem>)hashmap.get("orderitems");
            int i=1;
            double tot=0.0;
            for (OrderItem orderItem: listOrderItmes) {

                table.addCell( getPdfPCell(i+""));
                table.addCell( getPdfPCell(orderItem.getItemname()));
                table.addCell( getPdfPCell(orderItem.getQnty()+""));
                double price= orderItem.getPrice() * orderItem.getQnty();
                tot=tot+(price);
                table.addCell( getPdfPCell("Rs."+price));
                i++;
            }

            table.addCell( getPdfPCell(""));
            table.addCell( getPdfPCell(" "));
            table.addCell( getPdfPCell("Total"));
            table.addCell( getPdfPCell(tot+""));


            document.add(table);

            document.close();
            String filename="INVOICE000"+order.getOrderid();
            orderFile= orderFileDao.getOrderFileByOrderId(order.getOrderid());
             if(orderFile==null){
                 putObject(filename,file);
                 orderFile=new OrderFile();
                 orderFile.setOrderid(order.getOrderid());
                 orderFile.setInvoicefile(filename);
                 orderFile.setStatus(1);
                 orderFile.setInfectreport(0);
                 orderFileDao.addOrderFile(orderFile);
             }

           writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    return orderFile;
    }

    public static void main(String[] args) {


    }

    private static PdfPCell getPdfPCell(String s) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(s));
        cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell1;
    }

    public S3Object getDownloadObject(int orderid){
     /*   OrderFile orderFile= orderFileDao.getOrderFileByOrderId(orderid);
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE,10);
        return client.generatePresignedUrl(bucketname,orderFile.getInvoicefile(),calendar.getTime() );*/
        return getDownloadObUrl(orderid);
    }

    public S3Object getDownloadObUrl(int orderid){
        OrderFile orderFile= orderFileDao.getOrderFileByOrderId(orderid);
     return client.getObject(bucketname,orderFile.getInvoicefile().trim());

    }
}
