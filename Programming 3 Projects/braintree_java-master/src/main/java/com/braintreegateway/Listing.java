package com.braintreegateway;

public class Listing {
    private String nonce;
    private Long currentReservation;
    private Long listingID;

    public void setNonce(String nonce){
        this.nonce = nonce;
    }

    public String getNonce(){
        return nonce;
    }


    public long getCurrentReservation(){
        return currentReservation;
    }

    public void setCurrentReservation(Long currentReservation){
        this.currentReservation = currentReservation;
    }

    public long getId(){
        return listingID;
    }

    public void setId(long listingID){
        this.listingID = listingID;
    }








}
