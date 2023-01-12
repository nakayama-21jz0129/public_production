package model;

import java.time.LocalDate;

import conversion.Format;
import database.TaxDAO;
import database.TaxDTO;

public class Tax {
    private double rate;

    /**
     * 引数無しコンストラクタ
     */
    public Tax() {
    }

    // getter・setter
    public double getRate() {
        return rate;
    }

    private void setRate(double rate) {
        this.rate = rate;
    }
    
    /**
     * 日付から消費税を探す。
     *  見つかればフィールドにデータをセット
     * @param date
     * @return
     */
    public boolean search(LocalDate date) {
        // DAO・DTO
        TaxDAO taxDAO = new TaxDAO();
        TaxDTO taxDTO = null;
        
        // 引数のnullを確認
        if (date != null) {
            
            // 消費税を探す
            taxDTO = taxDAO.search(Format.localDateToDate(date));
            if (taxDTO != null) {
                // フィールドに値をセット
                setRate(taxDTO.getRate());
                
                return true;
            }
        }
        
        return false;
    }

    
}
