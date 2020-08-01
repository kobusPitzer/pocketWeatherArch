package com.kobus.pitzer.updatedweather.media;

import android.content.Context;
import android.os.Environment;

import com.kobus.pitzer.updatedweather.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MediaFolders {
    private static String getRootDirectoryName(Context context) {
        return context.getString(R.string.app_name);
    }

    private static String getRootDefaultDirectoryName() {
        return "default";
    }

    private static String getRootDebugDirectoryName(Context context) {
        return context.getString(R.string.default_directory_name_debug);
    }


    private static String getLogsDirectoryName(Context context) {
        return context.getString(R.string.default_directory_name_logs);
    }


    private static File createOrFindFolder(File path) {
        if (path.exists() && path.isDirectory()) {
            // Timber.d("Path exist, return %s", path.toString());
            return path;
        } else {
            if (path.mkdirs()) {
                //   Timber.d("Path created, return %s", path.toString());
                return path;
            } else {
                //  Timber.d("Path not created");
                throw new RuntimeException("Unable to create folder " + path.toString());
            }
        }
    }

    public static File getRootDirectory(Context context) {
        return createOrFindFolder(
                new File(
                        Environment.getExternalStorageDirectory() +
                                File.separator +
                                getRootDirectoryName(context)
                )
        );
    }

    public static File getRootDefaultDirectory(Context context) {
        return createOrFindFolder(
                new File(
                        getRootDirectory(context) +
                                File.separator +
                                getRootDefaultDirectoryName()
                )
        );
    }

    public static File getRootDebugDirectory(Context context) {
        return createOrFindFolder(
                new File(
                        getRootDirectory(context) +
                                File.separator +
                                getRootDebugDirectoryName(context)
                )
        );
    }


    public static File getLogsDirectory(Context context) {
        return createOrFindFolder(new File(getLogsDirectoryFilePath(context)));
    }

    public static String getLogsDirectoryFilePath(Context context) {
        return getRootDebugDirectory(context) +
                File.separator +
                getLogsDirectoryName(context);
    }


    private static String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    private static String getCounterString(int counter) {
        return String.format("%03d", counter);
    }


}
