package org.entitypedia.games.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * From http://thomaswabner.wordpress.com/2007/10/09/fast-stream-copy-using-javanio-channels/
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public final class ChannelTools {

    public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();
            // write to the channel, may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        // get an channel from the stream
        final ReadableByteChannel inputChannel = Channels.newChannel(input);
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        // copy the channels
        try {
            ChannelTools.fastChannelCopy(inputChannel, outputChannel);
        } finally {
            // closing the channels
            inputChannel.close();
            outputChannel.close();
        }
    }
}
