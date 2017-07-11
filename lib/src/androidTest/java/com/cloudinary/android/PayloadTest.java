package com.cloudinary.android;

import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cloudinary.android.payload.ByteArrayPayload;
import com.cloudinary.android.payload.FilePayload;
import com.cloudinary.android.payload.LocalUriPayload;
import com.cloudinary.android.payload.NotFoundException;
import com.cloudinary.android.payload.Payload;
import com.cloudinary.android.payload.PayloadFactory;
import com.cloudinary.android.payload.ResourcePayload;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PayloadTest extends AbstractTest {
    static File assetFile;

    @BeforeClass
    public static void setup() throws IOException {
        assetFile = assetToFile(TEST_IMAGE);
    }

    @Test
    public void testFilePayload() throws NotFoundException {
        FilePayload filePayload = new FilePayload(assetFile.getAbsolutePath());
        verifyLengthAndRecreation(filePayload, 3381);
    }

    @Test
    public void testUriPayload() throws NotFoundException {
        LocalUriPayload filePayload = new LocalUriPayload(Uri.fromFile(assetFile));
        // note: can't test uri size unless it's an actual media on the device
        verifyLengthAndRecreation(filePayload, -1);
    }

    @Test
    public void testBytesPayload() throws NotFoundException {
        ByteArrayPayload byteArrayPayload = new ByteArrayPayload(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        verifyLengthAndRecreation(byteArrayPayload, 10);
    }

    @Test
    public void testResourcePayload() throws NotFoundException {
        ResourcePayload payload = new ResourcePayload(com.cloudinary.android.test.R.raw.old_logo);
        verifyLengthAndRecreation(payload, 3381);
    }

    private void verifyLengthAndRecreation(Payload payload, int expectedLength) throws NotFoundException {
        assertEquals(expectedLength, payload.getLength(InstrumentationRegistry.getContext()));

        String asUri = payload.toUri();
        Payload newPayload = PayloadFactory.fromUri(asUri);
        assertEquals(newPayload, payload);
    }
}
