/*
 * OpenNoteSecure is an Android application for reading and writing
 * encrypted text files to an SD card.
 * Copyright (C) 2010 Jared Hatfield
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaredhatfield.opennotesecure.EncryptionProviders;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import android.util.Log;

import com.jaredhatfield.opennotesecure.Base64;
import com.jaredhatfield.opennotesecure.OpenNoteSecure;

/**
 * Provides the ability to encrypt and decrypt strings use DES.
 * @author Jared Hatfield
 */
public class DESEncryptionProvider extends IStringEncryptor {
	
	/**
	 * The encryption cipher.
	 */
    Cipher ecipher;
    
    /**
     * The decryption cipher.
     */
    Cipher dcipher;

    /**
     * 8 bytes of Salt.
     */
    byte[] salt = {72, 51, 82, 112, 28, 27, 87, 115};

    /**
     * The iteration count.
     */
    int iterationCount = 10;

    /**
     * Initializes a new instance of the DESEncryptionProvider.
     * @param passPhrase The passphrase to protect the data with.
     * @throws EncryptionException
     */
    public DESEncryptionProvider(String passPhrase) throws EncryptionException {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
            
            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } 
        catch (Exception e){
        	Log.e(OpenNoteSecure.TAG, "Failed to created DES encryptor.", e);
            throw new EncryptionException("DESEncrpyionProvider could not be created", e);
        }
    }

    /**
	 * Performs DES encryption on a string.
	 * @param data The string to encrypt.
	 * @return The encrypted string.
	 * @throws EncryptionException
     */
    public String encryptAsBase64(String str) throws EncryptionException {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return Base64.encodeBytes(enc);
        } 
        catch (Exception e){
        	Log.e(OpenNoteSecure.TAG, "Failed to created DES encryptor.", e);
            throw new EncryptionException("DESEncrpyionProvider could not be created", e);
        }
    }

    /**
	 * Performs DES decryption on a string.
	 * @param data The string to decrypt.
	 * @return The decrypted string.
	 * @throws EncryptionException
     */
    public String decryptAsBase64(String str) throws EncryptionException {
        try {
            // Decode base64 to get bytes
        	byte[] dec = Base64.decode(str);
            
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } 
        catch (Exception e){
        	Log.e(OpenNoteSecure.TAG, "Failed to created DES encryptor.", e);
            throw new EncryptionException("DESEncrpyionProvider could not be created", e);
        }
    }
}
