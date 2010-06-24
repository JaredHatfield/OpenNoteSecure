package com.jaredhatfield.opennotesecure;

import android.os.AsyncTask;
import android.util.Log;

public class EncryptionTask  extends AsyncTask<FileTaskHolder, Void, FileTaskHolder>{

	/**
	 * Perform the task of encrypting and writing out the file.
	 */
	@Override
	protected FileTaskHolder doInBackground(FileTaskHolder... params) {
		FileTaskHolder holder = params[0];
		
		// Encrypt and write out the file.
		if(holder.getEncryption().equals("None")){
			Log.i(OpenNoteSecure.TAG, "Encrypting with Algorithm: None");
			FileManager.Instance().writeFile(holder.getFile(), holder.getEditTextContent().getText().toString());
		}
		else if(holder.getEncryption().equals("AES")){
			Log.i(OpenNoteSecure.TAG, "Encrypting with Algorithm: AES");
			Log.e(OpenNoteSecure.TAG, "Algoirthm not implemented");
		}
		else if(holder.getEncryption().equals("DES")){
			Log.i(OpenNoteSecure.TAG, "Encrypting with Algorithm: DES");
			Log.e(OpenNoteSecure.TAG, "Algoirthm not implemented");
		}
		
		// Wait so it looks like the task takes some time to complete
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			Log.e(OpenNoteSecure.TAG, e.getMessage());
		}
		
		return holder;
	}
	
	/**
	 * Dismiss the dialog after the task is complete.
	 */
	@Override
    protected void onPostExecute(FileTaskHolder holder)
    {
		holder.getDialog().dismiss();
    }
}
