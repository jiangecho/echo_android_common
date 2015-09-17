package net.coding.program.maopao.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chaochen on 14-9-22.
 */
public class PhotoOperate {

    private Context context;

    public PhotoOperate(Context context) {
        this.context = context;
    }

    private File getTempFile(Context context) {
        File file = null;
        try {
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            file = File.createTempFile(fileName, ".jpg", context.getCacheDir());
        } catch (IOException e) {
        }
        return file;
    }

    public File scal(Uri fileUri) throws Exception {
        String path = Global.getPath(context, fileUri);
        File outputFile = new File(path);
        if (Global.isGif(path)) {
            return outputFile;
        }

        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {

            Matrix matrix = new Matrix();

            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap source = BitmapFactory.decodeFile(path, options);
            Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, options.outWidth, options.outHeight, matrix, true);

            outputFile = getTempFile(context);
            FileOutputStream fos = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (source.isRecycled()){
                source.recycle();
            }

        } else {
            File tempFile = outputFile;
            outputFile = getTempFile(context);
            copyFileUsingFileChannels(tempFile, outputFile);
        }

        return outputFile;
    }

    private static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

}
