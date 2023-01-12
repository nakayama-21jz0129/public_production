package database;

import java.sql.Date;


public class TimePeriodDiscountDTO {
    private double timePeriod;
    private Date startDate;
    private Date endDate;
    private double rate;

    public TimePeriodDiscountDTO(double timePeriod, Date startDate, Date endDate, double rate) {
        this.timePeriod = timePeriod;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
    }

    public double getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(double timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
