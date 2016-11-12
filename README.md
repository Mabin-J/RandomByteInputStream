Random Byte Input Stream
========================
Generate Random Byte through InputStream

-------
Example
-------
```java
package info.mabin.java.io.example;

import info.mabin.java.io.RandomByteInputStream;

public class RandomByteInputStreamExample {
    public static void main(String... args){
        try(RandomByteInputStream randomByteInputStream = new RandomByteInputStream(
                100,    // size
                true,   // keepData
                true    // useVisibleByte
        )){
            System.out.print("Data: ");

            int readByte;
            while((readByte = randomByteInputStream.read()) != -1){
                System.out.print((char) readByte);
            }
            System.out.println("");
        }
    }
}
```