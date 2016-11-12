package info.mabin.java.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Random Byte Input Stream<br/>
 * Make Random Byte Stream
 */
@SuppressWarnings("unused")
public class RandomByteInputStream extends InputStream{
    private static final int DEFAULT_SIZE = Integer.MAX_VALUE;
    private static final boolean DEFAULT_KEEP_DATA = false;
    private static final boolean DEFAULT_USE_VISIBLE_BYTE = false;

    private int size;
    private boolean keepData;
    private boolean useVisibleByte;

    private int readAmount = 0;

    private ReadFunction readFunction;
    private SaveFunction saveFunction;

    /**
     * Constructs a RandomByteInputStream
     */
    public RandomByteInputStream(){
        this(DEFAULT_SIZE, DEFAULT_KEEP_DATA, DEFAULT_USE_VISIBLE_BYTE);
    }

    /**
     * Constructs a RandomByteInputStream<br/>
     * with Size
     * @param size the size of stream data
     */
    public RandomByteInputStream(int size){
        this(size, DEFAULT_KEEP_DATA, DEFAULT_USE_VISIBLE_BYTE);
    }

    /**
     * Constructs a RandomByteInputStream<br/>
     * with Size and KeepData Option
     * @param size the size of stream data
     * @param keepData keep stream data. kept data can be get with {@link #toByteArray()}
     */
    public RandomByteInputStream(int size, boolean keepData){
        this(size, keepData, DEFAULT_USE_VISIBLE_BYTE);
    }

    /**
     * Constructs a RandomByteInputStream<br/>
     * with KeepData Option
     * @param keepData keep stream data. kept data can be get with {@link #toByteArray()}
     */
    public RandomByteInputStream(boolean keepData){
        this(DEFAULT_SIZE, keepData, DEFAULT_USE_VISIBLE_BYTE);
    }

    /**
     * Constructs a RandomByteInputStream<br/>
     * with KeepData and UseVisibleByte Option
     * @param keepData keep stream data. kept data can be get with {@link #toByteArray()}
     * @param useVisibleByte make stream data with only visible characters.
     */
    public RandomByteInputStream(boolean keepData, boolean useVisibleByte){
        this(DEFAULT_SIZE, keepData, useVisibleByte);
    }

    /**
     * Constructs a RandomByteInputStream<br/>
     * with All Options
     * @param size the size of stream data
     * @param keepData keep stream data. kept data can be get with {@link #toByteArray()}
     * @param useVisibleByte make stream data with only visible characters.
     */
    public RandomByteInputStream(int size, boolean keepData, boolean useVisibleByte){
        this.size = size;
        this.keepData = keepData;
        this.useVisibleByte = useVisibleByte;

        init();
    }

    @Override
    public void close() throws RuntimeException {
        try {
            super.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init(){
        if(useVisibleByte){
            readFunction = new AlphabetReadFunction();
        } else {
            readFunction = new NormalReadFunction();
        }

        if(keepData){
            saveFunction = new DoSaveFunction();
        } else {
            saveFunction = new DoNotSaveFunction();
        }
    }



    @Override
    public int read() throws RuntimeException {
        ++readAmount;
        if(readAmount > size){
            return -1;
        } else {
            int readByte = readFunction.read();
            saveFunction.save(readByte);
            return readByte;
        }
    }

    /**
     * Get kept Data.
     * @return kept byte array
     */
    public byte[] toByteArray(){
        return saveFunction.toByteArray();
    }


    /**
     * @param size the size of stream data
     */
    public void setSize(int size) {
        this.size = size;
        init();
    }

    /**
     * @param keepData keep stream data. kept data can be get with {@link #toByteArray()}
     */
    public void setKeepData(boolean keepData) {
        this.keepData = keepData;
        init();
    }

    /**
     * @param useVisibleByte make stream data with only visible characters.
     */
    public void setUseVisibleByte(boolean useVisibleByte) {
        this.useVisibleByte = useVisibleByte;
        init();
    }

    private interface ReadFunction{
        int read();
    }

    private class NormalReadFunction implements ReadFunction{
        public int read(){
            return (int) (Math.random() * 256.0);
        }
    }

    private class AlphabetReadFunction implements ReadFunction{
        public int read(){
            return (int) (Math.random() * 94.0) + 33;
        }
    }



    private interface SaveFunction{
        void save(int byteForSave);
        byte[] toByteArray();
    }

    private class DoNotSaveFunction implements SaveFunction{
        @Override public void save(int byteForSave){}
        @Override public byte[] toByteArray(){throw new UnsupportedOperationException("'Keep Data' Option is Not Enabled!");}
    }

    private class DoSaveFunction implements SaveFunction{
        private ByteArrayOutputStream byteArrayOutputStream;

        DoSaveFunction(){
            if(size != Integer.MAX_VALUE) {
                byteArrayOutputStream = new ByteArrayOutputStream(size);
            } else {
                byteArrayOutputStream = new ByteArrayOutputStream();
            }
        }

        @Override
        public void save(int byteForSave) {
            byteArrayOutputStream.write(byteForSave);
        }

        @Override
        public byte[] toByteArray() {
            return byteArrayOutputStream.toByteArray();
        }
    }
}
