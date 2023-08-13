import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import synthesizer.GuitarString;

public class GuitarHero {

    private static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] strings = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            strings[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString string : strings) {
                string.tic();
            }
        }
    }
}
