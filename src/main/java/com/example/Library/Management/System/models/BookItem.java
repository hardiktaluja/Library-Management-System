package com.example.Library.Management.System.models;

import com.example.Library.Management.System.enums.BookFormat;
import com.example.Library.Management.System.enums.BookStatus;

import java.time.LocalDate;
import java.util.Date;

import static com.example.Library.Management.System.utils.AppUtils.ShowError;

public class BookItem extends Book {
    private String barcode;
    private boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    private double price;
    private BookFormat format;
    private BookStatus status;
    private Date dateOfPurchase;
    private Date publicationDate;
    private Rack placedAt;

    public String getBarcode() {
        return barcode;
    }

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    public void updateBookItemStatus(BookStatus status) {
        this.status = status;
    }

    public void updateDueDate(LocalDate dueDate) {
        this.dueDate = new Date(dueDate.toEpochDay());
    }

    public boolean checkout(String memberId) {
        if (this.isReferenceOnly()) {
            ShowError("This book is Reference only and can't be issues");
            return false;
        }
        if (!BookLending.lendBook(this.getBarcode(), memberId)) {
            return false;
        }
        this.updateBookItemStatus(BookStatus.LOANED);
        return true;
    }
}
