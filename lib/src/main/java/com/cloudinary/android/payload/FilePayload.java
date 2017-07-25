package com.cloudinary.android.payload;

import android.content.Context;
import android.net.Uri;

import com.cloudinary.utils.Base64Coder;

import java.io.File;

/**
 * This class is used to handle uploading of images/videos represented as a {@link java.io.File}
 */
public class FilePayload extends Payload<String> {
    static final String URI_KEY = "file";

    public FilePayload(String filePath) {
        super(filePath);
    }

    public FilePayload(){
    }

    @Override
    public long getLength(Context context) throws NotFoundException {
        File file = new File(data);

        if (!file.exists()){
            throw new NotFoundException(String.format("File '%s' does not exist", data));
        }

        return file.length();
    }

    @Override
    public Object prepare(Context context) throws NotFoundException {
        File file = new File(data);

        if (!file.exists()){
            throw new NotFoundException(String.format("File '%s' does not exist", data));
        }

        return file;
    }

    @Override
    public String toUri() {
        return URI_KEY +  "://" + Base64Coder.encodeString(data);
    }

    @Override
    void fromUri(String uri) {
        data = Base64Coder.decodeString(Uri.parse(uri).getHost());
    }
}
