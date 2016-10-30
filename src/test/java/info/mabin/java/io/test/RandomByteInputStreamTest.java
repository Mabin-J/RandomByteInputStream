package info.mabin.java.io.test;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import info.mabin.java.io.RandomByteInputStream;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RandomByteInputStreamTest {
    private final Logger LOG = LoggerFactory.getLogger(RandomByteInputStreamTest.class);
    private final int TEST_SIZE = 1024 * 1024;

    @Test
    public void defaultTest() throws IOException {
        LOG.debug("defaultTest");

        RandomByteInputStream randomByteInputStream = new RandomByteInputStream(true);
        ByteOutputStream byteOutputStream = new ByteOutputStream(TEST_SIZE);

        int read;
        for(int i = 0; i < TEST_SIZE; ++i){
            read = randomByteInputStream.read();
            byteOutputStream.write(read);
        }

        Assert.assertArrayEquals(
                "Byte Array is Not Same!!",
                randomByteInputStream.toByteArray(),
                byteOutputStream.getBytes()
        );

        byteOutputStream.close();
        randomByteInputStream.close();

        LOG.debug("PASSED!!");
    }

    @Test
    public void sizedDataTest() throws IOException {
        LOG.debug("sizedDataTest");

        RandomByteInputStream randomByteInputStream = new RandomByteInputStream(TEST_SIZE, true);
        ByteOutputStream byteOutputStream = new ByteOutputStream(TEST_SIZE);

        int read;
        while((read = randomByteInputStream.read()) != -1){
            byteOutputStream.write(read);
        }

        Assert.assertArrayEquals(
                "Byte Array is Not Same!!",
                randomByteInputStream.toByteArray(),
                byteOutputStream.getBytes()
        );

        byteOutputStream.close();
        randomByteInputStream.close();

        LOG.debug("PASSED!!");
    }

    @Test
    public void visibilityAndSizedDataTest() throws IOException {
        LOG.debug("visibilityAndSizedDataTest");

        RandomByteInputStream randomByteInputStream = new RandomByteInputStream(TEST_SIZE, true, true);
        ByteOutputStream byteOutputStream = new ByteOutputStream(TEST_SIZE);

        int read;
        while((read = randomByteInputStream.read()) != -1){
            if(LOG.isTraceEnabled()) {
                LOG.trace("Read Character: " + Character.toString((char) read));
            }

            Assert.assertTrue(
                    "Read Byte is Not Visibility",
                    read >= 33 && read <= 126
            );

            byteOutputStream.write(read);
        }

        Assert.assertArrayEquals(
                "Byte Array is Not Same!!",
                randomByteInputStream.toByteArray(),
                byteOutputStream.getBytes()
        );

        byteOutputStream.close();
        randomByteInputStream.close();

        LOG.debug("PASSED!!");
    }
}
