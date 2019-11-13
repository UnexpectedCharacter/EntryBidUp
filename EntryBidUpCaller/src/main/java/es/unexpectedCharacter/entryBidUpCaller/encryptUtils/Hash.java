package es.unexpectedCharacter.entryBidUpCaller.encryptUtils;

import java.util.Base64;
import java.util.logging.Logger;


public class Hash {
    final private static Logger LOGGER = Logger.getLogger("");


    public String stringHash(String inputStr) {
        String encodedString = Base64.getEncoder().encodeToString(inputStr.getBytes());
        //ENCRYPT
        String encryptedString = AES.encrypt(encodedString);
        Hash.LOGGER.info("Encrypted value: " + encryptedString);

        return encryptedString;
    }

    public String stringDehash(String encryptedString) {
        String decryptedString = AES.decrypt(encryptedString);
        Hash.LOGGER.info("Decrypted value: " + decryptedString);
        byte[] decodedBytes = Base64.getDecoder().decode(decryptedString);
        String decodedString = new String(decodedBytes);
        Hash.LOGGER.info("Decoded string: " + decodedString);

        return decodedString;
    }
}
