package com.spata14.trelloproject.card.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtils {
    public static byte[] compressfile(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] streamByte = new byte[4*1024];
        while(!deflater.finished()) {
            int size = deflater.deflate(streamByte);
            outputStream.write(streamByte, 0, size);
        }
        try {
            outputStream.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        return outputStream.toByteArray();
    }

    public static byte[] decompressFile(byte[] fileData) {
        Inflater inflater = new Inflater();
        inflater.setInput(fileData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(fileData.length);
        byte[] tempByte = new byte[4 * 1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tempByte);
                outputStream.write(tempByte, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return outputStream.toByteArray();
    }
}
