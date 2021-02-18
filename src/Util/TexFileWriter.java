package Util;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TexFileWriter {
    public static void writeTexFile(String filename, String tex) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(tex);
        writer.close();
    }
}
