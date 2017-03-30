import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by khang on 3/30/2017.
 */
public class TestEncode {
  @Test
  public void testEncode() throws IOException {
    FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");
    FFprobe ffprobe = new FFprobe("C:\\ffmpeg\\bin\\ffprobe.exe");

    FFmpegBuilder builder = new FFmpegBuilder()
        .setInput("C:\\video\\videotest.mp4")     // Filename, or a FFmpegProbeResult
        .overrideOutputFiles(true) // Override the output if it exists
        .addOutput("C:\\video\\videotest-output.mp4")   // Filename for the destination
        .setFormat("mp4")        // Format is inferred from filename, or can be set
        .disableSubtitle()       // No subtiles
        .setAudioChannels(1)         // Mono audio
        .setAudioCodec("aac")        // using the aac codec
        .setAudioSampleRate(48_000)  // at 48KHz
        .setAudioBitRate(32768)      // at 32 kbit/s
        .setVideoCodec("libx264")     // Video using x264
        .setVideoFrameRate(24, 1)     // at 24 frames per second
        .setVideoResolution(640, 480) // at 640x480 resolution
        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
        .done();
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);

// Run a one-pass encode
    executor.createJob(builder).run();
  }
}
