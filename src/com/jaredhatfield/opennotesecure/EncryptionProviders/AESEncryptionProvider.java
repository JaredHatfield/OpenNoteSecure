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
package com.jaredhatfield.opennotesecure.EncryptionProviders;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Log;
import com.jaredhatfield.opennotesecure.Base64;
import com.jaredhatfield.opennotesecure.OpenNoteSecure;

public class AESEncryptionProvider extends IStringEncryptor {

	/**
     * Will be replaced with 16 byte key (128 bit)
     */
    protected static byte[] rawSecretKey = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    
    /**
     * Constructor for AESEncryptionProvider for a specific passphrase
     * @param passphrase The phassphrase to protect the data with.
     * @throws EncryptionException 
     */
    public AESEncryptionProvider(String passphrase) throws EncryptionException {
    	// Set up the cipher
    	this.CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
        this.CIPHER_ALGORITHM = "AES";
        this.MESSAGEDIGEST_ALGORITHM = "MD5";
        
        // Create the password byte array
        byte[] passwordKey = encodeDigest(passphrase);

        // Set up the algorithm
        try {
            cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        } 
        catch (NoSuchAlgorithmException e) {
            Log.e(OpenNoteSecure.TAG, "No such algorithm " + CIPHER_ALGORITHM, e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        } 
        catch (NoSuchPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "No such padding PKCS5", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        }
        
        // Finish setting up the encryption by making the secret key and iv parameters
        secretKey = new SecretKeySpec(passwordKey, CIPHER_ALGORITHM);
        ivParameterSpec = new IvParameterSpec(rawSecretKey);
    }
    
    /**
     * Performs the encryption on a string of data.
     * @param data The plain text to encrypt.
     * @return The encrypted text encoded as a Base64 string.
     */
    public String encryptAsBase64(String data) throws EncryptionException{
    	byte[] encryptedData = encrypt(data.getBytes());
        return Base64.encodeBytes(encryptedData);
    }
    
    /**
     * Performs the decryption on a Base64 encoded encrypted string.
     * @param data The cipher text to decrypt.
     * @return The plain text.
     * @throws IOException
     */
    public String decryptAsBase64(String data) throws EncryptionException{
    	try{
    		byte[] decryptedData = decrypt(Base64.decode(data.getBytes()));
    		return new String(decryptedData);
    	}
    	catch(Exception e){
    		throw new EncryptionException("Decryption Failed", e);
    	}
    }

    /**
     * Performs the AES decryption on a byte array.
     * @param cipherData The cipher byte array.
     * @return The plain text byte array.
     * @throws EncryptionException 
     */
    private byte[] decrypt(byte[] cipherData) throws EncryptionException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        } 
        catch (InvalidKeyException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid key", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        } 
        catch (InvalidAlgorithmParameterException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        }

        byte[] decryptedData;
        try {
        	decryptedData = cipher.doFinal(cipherData);
        } 
        catch (IllegalBlockSizeException e) {
            Log.e(OpenNoteSecure.TAG, "Illegal block size", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        } 
        catch (BadPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "Bad padding", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        }
        
        return decryptedData;
    }
    
    /**
     * Performs the AES encryption on a byte array.
     * @param clearData The unencrypted byte array.
     * @return The encrypted byte array.
     * @throws EncryptionException 
     */
    private byte[] encrypt(byte[] clearData) throws EncryptionException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } 
        catch (InvalidKeyException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid key", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        } 
        catch (InvalidAlgorithmParameterException e) {
            Log.e(OpenNoteSecure.TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        }

        byte[] encryptedData;
        try {
            encryptedData = cipher.doFinal(clearData);
        } 
        catch (IllegalBlockSizeException e) {
            Log.e(OpenNoteSecure.TAG, "Illegal block size", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        } 
        catch (BadPaddingException e) {
            Log.e(OpenNoteSecure.TAG, "Bad padding", e);
            throw new EncryptionException("AESEncrpyionProvider could not be created", e);
        }
        
        return encryptedData;
    }
}
