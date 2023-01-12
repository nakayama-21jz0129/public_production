package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import conversion.Format;
import database.TimePeriodDiscountDAO;
import database.TimePeriodDiscountDTO;

public class TimePeriodDiscount {
    private double timePeriod;
    private double rate;

    /**
     * 引数無しコンストラクタ
     */
    public TimePeriodDiscount() {
    }

    // getter・setter
    public double getTimePeriod() {
        return timePeriod;
    }

    private void setTimePeriod(double timePeriod) {
        this.timePeriod = timePeriod;
    }

    public double getRate() {
        return rate;
    }

    private void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * 日時から時間帯割引を探す。
     *  見つかればフィールドにデータをセット
     * @param dateTime
     * @return
     */
    public boolean search(LocalDateTime dateTime) {
        // DAO・DTO
        TimePeriodDiscountDAO timePeriodDiscountDAO = new TimePeriodDiscountDAO();
        ArrayList<TimePeriodDiscountDTO> timePeriodDiscountDTOArray = null;
        
        // 引数のnullを確認
        if (dateTime != null) {
            
            // 日付から時間帯割引を探す
            timePeriodDiscountDTOArray = timePeriodDiscountDAO.search(
                        Format.localDateToDate(
                                Format.localDateTimeToLocalDate(dateTime)));
            
            // 時刻から時間帯割引を探す
            int hour = dateTime.getHour();
            for (TimePeriodDiscountDTO dto : timePeriodDiscountDTOArray) {
                if (hour == (int)dto.getTimePeriod()) {
                    // フィールドに値をセット
                    setTimePeriod(dto.getTimePeriod());
                    setRate(dto.getRate());
                    
                    return true;
                }
            }
        }
        
        
        return false;
    }
}
