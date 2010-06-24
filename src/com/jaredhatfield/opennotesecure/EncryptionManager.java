/*
 * OpenNoteSecure is an Android application for reading and writing encrypted
 * text files to an SD card.
 * Copyright (C) 2010  Jared Hatfield
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
package com.jaredhatfield.opennotesecure;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class EncryptionManager {
	/**
	 * 
	 */
    private static Cipher aesCipher;
    
    /**
     * 
     */
    private static SecretKey secretKey;
    
    /**
     * 
     */
    private static IvParameterSpec ivParameterSpec;

    /**
     * 
     */
    private static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    
    /**
     * 
     */
    private static String CIPHER_ALGORITHM = "AES";
    
    /**
     * Will be replaced with 16 bit key
     */
    private static byte[] rawSecretKey = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * 
     */
    private static String MESSAGEDIGEST_ALGORITHM = "MD5";

    /**
     * 
     * @param passphrase
     */
    public EncryptionManager(String passphrase) {
        byte[] passwordKey = encodeDigest(passphrase);

        try {
            aesCipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        } 
        catch (NoSuchAlgorithmException e) {
            Log.e(OpenNoteSecure.TAG, "No such algorithm " + CIPHER_ALGORITHM, e);
        } 
        catch (NoSuchPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "No such padding PKCS5", e);
        }

        secretKey = new SecretKeySpec(passwordKey, CIPHER_ALGORITHM);
        ivParameterSpec = new IvParameterSpec(rawSecretKey);
    }
    
    /**
     * 
     * @param data
     * @return
     */
    public String encryptAsBase64(String data){
    	byte[] encryptedData = encrypt(data.getBytes());
        return Base64.encodeBytes(encryptedData);
    }
    
    /**
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public String decryptAsBase64(String data) throws IOException{
    	byte[] decryptedData = decrypt(Base64.decode(data.getBytes()));
    	return new String(decryptedData);
    }

    /**
     * 
     * @param clearData
     * @return
     */
    private byte[] decrypt(byte[] clearData) {
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        } 
        catch (InvalidKeyException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid key", e);
            return null;
        } 
        catch (InvalidAlgorithmParameterException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            return null;
        }

        byte[] decryptedData;
        try {
        	decryptedData = aesCipher.doFinal(clearData);
        } 
        catch (IllegalBlockSizeException e) {
            Log.e(OpenNoteSecure.TAG, "Illegal block size", e);
            return null;
        } 
        catch (BadPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "Bad padding", e);
            return null;
        }
        
        return decryptedData;
    }
    
    /**
     * 
     * @param clearData
     * @return
     */
    private byte[] encrypt(byte[] clearData) {
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } 
        catch (InvalidKeyException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid key", e);
            return null;
        } 
        catch (InvalidAlgorithmParameterException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            return null;
        }

        byte[] encryptedData;
        try {
            encryptedData = aesCipher.doFinal(clearData);
        } 
        catch (IllegalBlockSizeException e) {
            Log.e(OpenNoteSecure.TAG, "Illegal block size", e);
            return null;
        } 
        catch (BadPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "Bad padding", e);
            return null;
        }
        
        return encryptedData;
    }

    /**
     * 
     * @param text
     * @return
     */
    private byte[] encodeDigest(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(MESSAGEDIGEST_ALGORITHM);
            return digest.digest(text.getBytes());
        } 
        catch (NoSuchAlgorithmException e) {
            Log.e(OpenNoteSecure.TAG, "No such algorithm " + MESSAGEDIGEST_ALGORITHM, e);
        }

        return null;
    }
}
