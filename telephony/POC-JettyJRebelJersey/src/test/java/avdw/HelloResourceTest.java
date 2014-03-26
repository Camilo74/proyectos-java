package avdw;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * sox entrada.wav -r 8000 -c1 output.gsm
 */

public class HelloResourceTest {

	public static void main(String[] args) throws FileNotFoundException {
		HelloResourceTest test = new HelloResourceTest();
		do {
			File record = test.record();
			double bytes = record.length();
			double kilobytes = (bytes / 1024);
			System.out.println("kilobytes : " + kilobytes);
			test.send(record);
		} while (true);
	}

	private File record() {
		long inicio = System.currentTimeMillis();
		File recorder = new JavaSoundRecorder().main();
		System.out.println("### record: " + (System.currentTimeMillis() - inicio));
		return recorder;
	}

	public void send(File fileToUpload) {
		try {
			long inicio = System.currentTimeMillis();
			final ClientConfig config = new DefaultClientConfig();
			final Client client = Client.create(config);
			final WebResource resource = client.resource("http://localhost:8081/jersey").path("hello").path("push");
			final FormDataMultiPart multiPart = new FormDataMultiPart();
			if (fileToUpload != null) {
				multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload,MediaType.APPLICATION_OCTET_STREAM_TYPE));
			}
			final ClientResponse clientResp = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, multiPart);
			System.out.println("Response: " + clientResp.getClientResponseStatus());
			client.destroy();
			System.out.println("### send: " + (System.currentTimeMillis() - inicio));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void play() {
		try {
			// AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audiofile"));
			AudioInputStream stream = AudioSystem.getAudioInputStream(new URL("http://localhost:8081/jersey/hello/get2"));

			AudioFormat format = stream.getFormat();
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,format.getSampleRate(),format.getSampleSizeInBits() * 2, format.getChannels(),format.getFrameSize() * 2, format.getFrameRate(), true); // big endian
				stream = AudioSystem.getAudioInputStream(format, stream);
			}

			SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class,stream.getFormat(),((int) stream.getFrameLength() * format.getFrameSize()));
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(stream.getFormat());
			line.start();

			int numRead = 0;
			byte[] buf = new byte[line.getBufferSize()];
			while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
				int offset = 0;
				while (offset < numRead) {
					offset += line.write(buf, offset, numRead - offset);
				}
			}
			line.drain();
			line.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}