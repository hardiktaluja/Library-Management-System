package com.example.Library.Management.System.models;

import com.example.Library.Management.System.enums.ReservationStatus;

import java.util.Date;

public class BookReservation {
    private Date creationDate;
    private ReservationStatus status;
    private String bookItemBarcode;
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public static BookReservation fetchReservationDetails(String barcode) {
        return null;
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }

    public void sendBookAvailableNotification() {

    }
}
