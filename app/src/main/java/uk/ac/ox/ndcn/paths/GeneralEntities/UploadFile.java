/*
 * Copyright (c) 2011 Dropbox, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */


package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.UploadRequest;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxFileSizeException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import uk.ac.ox.ndcn.paths.MainActivity;

/**
 * Here we show uploading a file in a background thread, trying to show
 * typical exception handling and flow of control for an app that uploads a
 * file from Dropbox.
 */
public class UploadFile extends AsyncTask<Void, Long, Boolean> {

    private File mFile;
    private DbxClientV2 dbxClient;
    private long mFileLen;
    private UploadRequest mRequest;
    private Context mContext;

    private String mErrorMsg;

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void save(String name, String s, Context context)
        throws IOException, DropboxException {
        if (isExternalStorageWritable()){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
            FileOutputStream f;
            FileInputStream i;
            f = new FileOutputStream(file);
            f.write(s.getBytes());
            f.close();
            Toast.makeText(context,"File saved Locally", Toast.LENGTH_LONG).show();



            if (MainActivity.accessToken == null){
                Toast.makeText(context,"File NOT backed up to dropbox, you aren't signed in", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                UploadFile upload = new UploadFile(context, file);
                upload.execute();
            }
        }
        else
        {
            //throw new IOException("External Storage Not Writeable");
            Toast.makeText(context,"File NOT saved at all, please make sure access to external storage is granted", Toast.LENGTH_LONG).show();
        }
    }

    public static void saveImg(String name, Bitmap img, Context context)
            throws IOException, DropboxException {
        if (isExternalStorageWritable()){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
            FileOutputStream f;
            FileInputStream i;
            f = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.PNG, 100, f);
            f.close();
            Toast.makeText(context,"File saved Locally", Toast.LENGTH_LONG).show();


            if (MainActivity.accessToken == null){
                Toast.makeText(context,"File NOT backed up to dropbox, you aren't signed in", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                UploadFile upload = new UploadFile(context, file);
                upload.execute();
            }
        }
        else
        {
            //throw new IOException("External Storage Not Writeable");
            Toast.makeText(context,"File NOT saved at all, please make sure access to external storage is granted", Toast.LENGTH_LONG).show();

        }
    }


    public UploadFile(Context context,
                      File file) {

        // We set the context this way so we don't accidentally leak activities

        this.dbxClient = DropboxClient.getClient(MainActivity.accessToken);
        mContext = context.getApplicationContext();

        mFileLen = file.length();
        mFile = file;

    }
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Upload to Dropbox
            InputStream inputStream = new FileInputStream(mFile);
            dbxClient.files().uploadBuilder("/" + mFile.getName()) //Path in the user's Dropbox to save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream);
            Log.d("Upload Status", "Success");
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        showToast("File successfully uploaded");
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        error.show();
    }
}
