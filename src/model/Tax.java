package model;

import database.TaxDAO;

public class Tax {
    private double rate;

    public Tax() {
        this.rate = new TaxDAO().getTaxRate();
    }

    public double getRate() {
        return rate;
    }
}
