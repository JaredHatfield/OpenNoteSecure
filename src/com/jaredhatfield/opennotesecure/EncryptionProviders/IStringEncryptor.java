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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import android.util.Log;

import com.jaredhatfield.opennotesecure.OpenNoteSecure;

public abstract class IStringEncryptor {
	/**
	 * 
	 */
	protected Cipher cipher;
    
    /**
     * 
     */
    protected SecretKey secretKey;
    
    /**
     * 
     */
    protected IvParameterSpec ivParameterSpec;

    /**
     * 
     */
    protected String CIPHER_TRANSFORMATION;
    
    /**
     * 
     */
    protected String CIPHER_ALGORITHM;
    
    /**
     * 
     */
    protected String MESSAGEDIGEST_ALGORITHM;
    
	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String encryptAsBase64(String data) throws EncryptionException;
	
	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String decryptAsBase64(String data) throws EncryptionException;
	
	/**
     * 
     * @param text
     * @return
     */
    protected byte[] encodeDigest(String text) {
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
