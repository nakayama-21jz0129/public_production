package conversion;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Format {
    // jsonToMap用
    private static ObjectMapper mapper = new ObjectMapper();
    
    /**
     * String型のJson形式文字列をMap<String, Object>型の連想配列に変換する。
     *  変換失敗時にはnullを返す
     * @param str
     * @return 
     */
    public static Map<String, Object> jsonToMap(String str) {
        try {
            return mapper.readValue(str, new TypeReference<Map<String, Object>>(){});
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }
    
    /**
     * Map型の連想配列をString型のJson形式文字列に変換する。
     *  変換失敗時には空文字を返す
     * @param map
     * @return
     */
    public static String mapToJson(Object map) {
        try {
            return mapper.writeValueAsString(map);
        }
        catch (JsonProcessingException e) {
            return "";
        }
    }
    
    
    /**
     * LocalDateTime型の日付データをLocalDate型の日付データに変換する。
     *  引数がnullの場合はnullを返す
     * @param dateTime
     * @return
     */
    public static LocalDate localDateTimeToLocalDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            return LocalDate.from((TemporalAccessor)dateTime);
        }
        return null;
    }
    
    /**
     * java.sql.Date型の日付データをLocalDate型の日付データに変換する。
     *  引数がnullの場合はnullを返す
     * @param sqlDate
     * @return
     */
    public static LocalDate dateToLocalDate(Date sqlDate) {
        if (sqlDate != null) {
            return sqlDate.toLocalDate();
        }
        return null;
    }
    
    /**
     * LocalDate型の日付データをjava.sql.Date型の日付データに変換する。
     *  引数がnullの場合はnullを返す
     * @param date
     * @return
     */
    public static Date localDateToDate(LocalDate date) {
        if (date != null) {
            return Date.valueOf(date);
        }
        return null;
    }
    
    /**
     * LocalDateTime型の日付データをTimestamp型の日付データに変換する。
     *  引数がnullの場合はnullを返す
     * @param dateTime
     * @return
     */
    public static Timestamp localDateTimeToTimestamp(LocalDateTime dateTime) {
        if (dateTime != null) {
            return Timestamp.valueOf(dateTime);
        }
        return null;
    }
    
    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
    
    /**
     * int型の真偽値(0:false,1:true)をboolean型の真偽値に変換する。
     *  0,1以外の数字の場合もfalse
     * @param i
     * @return
     */
    public static boolean intToBoolean(int i) {
        return i == 1;
    }
    
    /**
     * boolean型の真偽値をint型の真偽値(0:false,1:true)に変換する。
     * @param bool
     * @return
     */
    public static int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }
}
