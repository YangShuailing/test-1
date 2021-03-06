package algorithm.encrypt;

import utils.FileUtil;

import javax.crypto.Cipher;
import java.io.File;
import java.security.Key;
import java.security.Security;

public class MySecurity {

    private MySecurity des = new MySecurity();
    private static String strDefaultKey = "johnny";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    //TODO
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    private MySecurity() throws Exception {
        this(strDefaultKey);
    }

    @SuppressWarnings("restriction")
	private MySecurity(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    private void encryptFile(String srcFile, String destFile) throws Exception {
        File inFile = new File(srcFile);
        byte[] fileA = FileUtil.getByteFromFile(inFile);
        FileUtil.writeBytesToFile(des.encrypt(fileA), destFile);
    }

    private void decryptFile(String srcFile, String destFile) throws Exception {
        File inFile = new File(srcFile);
        byte[] fileA = FileUtil.getByteFromFile(inFile);
        FileUtil.writeBytesToFile(des.decrypt(fileA), destFile);
    }

    public static void main(String args[]) {
        try {
            MySecurity des = new MySecurity();

            des.encryptFile("E:\\GitHub\\test\\demo\\src\\test\\resources\\1.txt", "E:\\GitHub\\test\\demo\\src\\test\\resources\\en1.txt");

            des.decryptFile("E:\\GitHub\\test\\demo\\src\\test\\resources\\en1.txt", "E:\\GitHub\\test\\demo\\src\\test\\resources\\2.txt");

            System.out.println("string before decrypted:String==" + "123456");
            String enStr = des.encrypt("123456");
            System.out.println("string after encrypted:enStr==" + enStr);
            String deStr = des.decrypt(enStr);
            System.out.println("string after decrypted:deStr==" + deStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
