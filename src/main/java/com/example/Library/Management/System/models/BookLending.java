package com.example.Library.Management.System.models;

import java.util.Date;

public class BookLending {
    private Date creationDate;
    private Date dueDate;
    private Date returnDate;
    private String bookItemBarcode;
    private String memberId;

    public Date getDueDate() {
        return dueDate;
    }

    public static boolean lendBook(String barcode, String memberId) {
        return false;
    }

    public static BookLending fetchLendingDetails(String barcode) {
        return null;
    }
}
