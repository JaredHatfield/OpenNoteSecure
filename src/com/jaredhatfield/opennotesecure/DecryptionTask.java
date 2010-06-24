package com.jaredhatfield.opennotesecure;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class DecryptionTask extends AsyncTask<FileTaskHolder, Void, FileTaskHolder>{

	/**
	 * Perform the task of decrypting the file.
	 */
	@Override
	protected FileTaskHolder doInBackground(FileTaskHolder... arg0) {
		FileTaskHolder holder = arg0[0];
		
		// Read in and decrypt the file
		if(holder.getEncryption().equals("None")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: None");
			holder.setResult(FileManager.Instance().readFile(holder.getFile()));
		}
		else if(holder.getEncryption().equals("AES")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: AES");
			Log.e(OpenNoteSecure.TAG, "Algoirthm not implemented");
		}
		else if(holder.getEncryption().equals("DES")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: DES");
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
	 * Load the decrypted content into the EditText box and dismiss the dialog.
	 */
	@Override
    protected void onPostExecute(FileTaskHolder holder)
    {
		EditText content = holder.getEditTextContent();
		content.setText(holder.getResult());
		holder.getDialog().dismiss();
    }
}

