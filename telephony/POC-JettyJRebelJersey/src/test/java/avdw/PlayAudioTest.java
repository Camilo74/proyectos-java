package avdw;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlayAudioTest {

	public static void main(String[] args) throws MalformedURLException, IOException {
		final String urlString = "http://localhost:8081/jersey/hello/get";
		do {
			try {
				BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());			
				// Create an AudioStream object from the input stream.
				AudioStream as = new AudioStream(in);         
				// Use the static class member "player" from class AudioPlayer to play clip.
				AudioPlayer.player.start(as);
				Thread.sleep(870);
			} catch (Exception e) {
				System.out.println("Error");
			}
			
		} while (true);
	}
}