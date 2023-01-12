package conversion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    // hash用
    private static final String SALT = "xYZ3n4e&7";
    
    /**
     * String型の文字列をソルトを付けてハッシュ化し、16進数表記のString型の文字列に変換する。
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hash(String password) throws NoSuchAlgorithmException {
        // sha512のdigestを作成
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        
        // ハッシュ化
        byte[] hash = digest.digest((password + SALT).getBytes());
        for (int i = 0; i < 5; i++) {
            hash= digest.digest(hash);
        }
        
        // ハッシュ化されたバイナリデータを文字列に変換
        StringBuilder password16 = new StringBuilder(2 * hash.length);
        for(byte b: hash) {
            password16.append(String.format("%02X", b&0xff) );
        }
        
        // 文字列を返す
        return password16.toString();
    }
}
