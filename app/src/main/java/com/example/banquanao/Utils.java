package com.example.banquanao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class Utils {
    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }
    public static Bitmap TranslateByteToBitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;

    }
    public static Bitmap loadBitmapFromAssets(Context context, String path, String folderName)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(folderName+"/"+path);
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {} finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}
