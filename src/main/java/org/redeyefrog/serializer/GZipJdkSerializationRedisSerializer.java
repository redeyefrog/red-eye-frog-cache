package org.redeyefrog.serializer;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipJdkSerializationRedisSerializer extends JdkSerializationRedisSerializer {

    @Override
    public Object deserialize(byte[] bytes) {
        return super.deserialize(decompress(bytes));
    }

    @Override
    public byte[] serialize(Object object) {
        return compress(super.serialize(object));
    }

    private byte[] compress(byte[] source) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(source);
            gzipOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(MessageFormat.format("Could not compress: {0}", e.getMessage()), e);
        }
    }

    private byte[] decompress(byte[] source) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(source));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = gzipInputStream.read(buf)) != 1) {
                outputStream.write(buf, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(MessageFormat.format("Could not decompress: {0}", e.getMessage()), e);
        }
    }

}
