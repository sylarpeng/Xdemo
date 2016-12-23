package com.pzj.vplibrary.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by pzj on 2016/12/15.
 */

public class IOUtil {
        private static final String TAG = "DownLoadManager";

        public static String writeResponseBodyToDisk(ResponseBody body,String dir, String downloadName) {
            File futureFile = new File(dir,downloadName);
            String path = futureFile.getPath();
            InputStream inputStream = null;
            OutputStream outputStream = null;
            long fileSize = body.contentLength();
            Log.d(TAG,"WriteFileManager.writeResponseBodyToDisk.fileSize:"+fileSize);

            try {
                try {
                    byte[] fileReader = new byte[1024 * 1024];
                    long fileSizeDownloaded = 0;
                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(futureFile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }
                        outputStream.write(fileReader, 0, read);
                        fileSizeDownloaded += read;
//                    LogUtil.i(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    outputStream.flush();
                    return path;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return null;
            }

        }

}
