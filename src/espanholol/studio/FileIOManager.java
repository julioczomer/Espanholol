/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espanholol.studio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author rudsonm
 */
public final class FileIOManager {
    public static String getFileContent(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            br.close();
            return everything;
        } catch(Exception e) {
            return e.getMessage();
        }
    }
    
    public static void createFileAndWrite(String path, String content) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(content);
            out.close();
        } catch(Exception e) {
            
        }
    }
}
