package com.notloki.csvprocessor;
// Object to hold fields from CSV file

public class CutList {

    CSVProcessor processor = new CSVProcessor();

    private String qty;
    private String length;
    private String inches;

    public String getInches() {
        return inches;
    }

    public void setInches(String inches) {
        this.inches = inches;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return (qty + "," + length + "," + "False" + ",,,,," + inches);
    }
}
