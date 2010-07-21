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

/**
 * The exception that is thrown when encryption or decryption fails.
 * @author Jared Hatfield
 */
public class EncryptionException extends Exception {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Implements the default encryption exception.
	 */
	public EncryptionException(){
		super("Encryption Exception");
	}
	
	/**
	 * Implements the encryption exception.
	 * @param message The message for the error.
	 */
	public EncryptionException(String message) {
		super(message);
	}
	
	/**
	 * Implements the encryption exception.
	 * @param message The message for the error.
	 * @param cause The cause of the exception.
	 */
	public EncryptionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Implements the encryption exception.
	 * @param cause The cause of the exception.
	 */
	public EncryptionException(Throwable cause) {
		super(cause);
	}
}
