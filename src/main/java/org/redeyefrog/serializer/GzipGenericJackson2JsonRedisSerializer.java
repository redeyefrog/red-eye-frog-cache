package org.redeyefrog.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

    public GzipGenericJackson2JsonRedisSerializer() {
    }

    public GzipGenericJackson2JsonRedisSerializer(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        return compress(super.serialize(source));
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        return super.deserialize(decompress(source));
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
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(source))) {
            return gzipInputStream.readAllBytes();
        } catch (IOException e) {
            throw new SerializationException(MessageFormat.format("Could not decompress: {0}", e.getMessage()), e);
        }
    }

}
