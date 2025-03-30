package de.birgitkratz.morsecode;

import de.birgitkratz.MorseCodeBaseListener;
import de.birgitkratz.MorseCodeParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MorseCodeSoundListener extends MorseCodeBaseListener {
    private static final int SAMPLE_RATE = 8000; // Abtastrate
    private static final int DIT_DURATION = 100; // Dauer eines Punktes in Millisekunden
    private static final int DAH_DURATION = DIT_DURATION * 3; // Dauer eines Strichs
    private static final int SPACE_DURATION = DIT_DURATION; // Dauer einer Pause zwischen Zeichen
    private static final int LETTER_SPACE_DURATION = DIT_DURATION * 3; // Dauer einer Pause zwischen Buchstaben
    private static final int WORD_SPACE_DURATION = DIT_DURATION * 7; // Dauer einer Pause zwischen Worten

    @Override
    public void exitWord(MorseCodeParser.WordContext ctx) {
        try {
            for(TerminalNode letter : ctx.LETTER()) {
                var charArray = letter.getText().toCharArray();
                for(char c : charArray) {
                    makeSound(c);
                    Thread.sleep(SPACE_DURATION);
                }
                Thread.sleep(LETTER_SPACE_DURATION);
            }
            Thread.sleep(WORD_SPACE_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeSound(char morseSign) {
        byte[] buf = new byte[1];
        int duration = DIT_DURATION;
        if (morseSign == '-') {
            duration = DAH_DURATION;
        }
        float frequency = 440;
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        try(SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();

            for (int i = 0; i < duration * SAMPLE_RATE / 1000; i++) {
                double angle = 2.0 * Math.PI * i / (SAMPLE_RATE / frequency);
                buf[0] = (byte) (Math.sin(angle) * 127f);
                sdl.write(buf, 0, 1);
            }

            sdl.drain();
            sdl.stop();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
