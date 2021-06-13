package com.example.Library.Management.System.models;

import com.example.Library.Management.System.constants.Constants;
import com.example.Library.Management.System.enums.BookStatus;
import com.example.Library.Management.System.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.Date;

import static com.example.Library.Management.System.utils.AppUtils.ShowError;

public class Member extends Account {
    private Date dateOfMembership;
    private int totalBooksCheckedOut;

    public int getTotalBooksCheckedOut() {
        return totalBooksCheckedOut;
    }

    public boolean reserveBookItem(BookItem bookItem) {
        return false;
    }

    private void incrementTotalBooksCheckedOut() {

    }

    private void decrementTotalBooksCheckedOut() {

    }

    public boolean checkOutBookItem(BookItem bookItem) {
        if (this.getTotalBooksCheckedOut() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            ShowError("The user has already checked out maximum number of books");
            return false;
        }
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
            ShowError("This book is reserved by another member");
            return false;
        } else if (bookReservation != null) {
            bookReservation.updateStatus(ReservationStatus.COMPLETED);
        }
        if (!bookItem.checkout(this.getId())) {
            return false;
        }
        this.incrementTotalBooksCheckedOut();
        return true;
    }

    private void checkForFine(String bookItemBarcode) {
        BookLending bookLending = BookLending.fetchLendingDetails(bookItemBarcode);
        Date dueDate = bookLending.getDueDate();
        Date today = new Date();
        if (today.compareTo(dueDate) > 0) {
            long diff = today.getTime() - dueDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            Fine.collectFine(this.getId(), diffDays);
        }
    }

    public void returnBookItem(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null) {
            bookItem.updateBookItemStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
        }
        bookItem.updateBookItemStatus(BookStatus.AVAILABLE);
    }

    public boolean renewBookItem(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
            ShowError("This book is reserved by another member");
            this.decrementTotalBooksCheckedOut();
            bookItem.updateBookItemStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
            return false;
        } else if (bookReservation != null) {
            bookReservation.updateStatus(ReservationStatus.COMPLETED);
        }
        BookLending.lendBook(bookItem.getBarcode(), this.getId());
        bookItem.updateDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
        return true;
    }
}
