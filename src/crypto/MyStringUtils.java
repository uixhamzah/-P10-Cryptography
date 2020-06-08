package crypto;

/**
 *
 * @author Hmzh
 */
public class MyStringUtils {
    
    static final  byte [] HEX_CHAR_TABLE = {
        (byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',
        (byte)'7',(byte)'8',(byte)'9',(byte)'a',(byte)'b',(byte)'c',(byte)'d',
        (byte)'e',(byte)'f'
    };
    
    public static String getHexString(byte [] raw){
        String hex = "";
        try {
            byte[] hexa = new byte[2 * raw.length];
            int index = 0;
            for (byte b : raw) {
                int v = b & 0xff;
                hexa[index++] = HEX_CHAR_TABLE[v >>> 4];
                hexa[index++] = HEX_CHAR_TABLE[v & 0xf];
            }
            hex = new String(hexa, "ASCII");
        } catch (Exception e) {
        }
        return hex;
    }
}
