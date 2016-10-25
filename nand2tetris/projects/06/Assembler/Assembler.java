import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ethan on 2016/10/23.
 */
public class Assembler {
    private static HashMap<String, String> symbols;
    private static HashMap<String, String> comp;
    private static HashMap<String, String> jump;

    private static void parseFile(String fileName) {
        // Read asm file
        ArrayList<String> fileAsm = new ArrayList<>();
        String fileBin;
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String read;
            while ((read = br.readLine()) != null) {
                // Skip whitespace and comment
                String line = read.replaceAll("\\s+", "");
                if (line.contains("//")) line = line.substring(0, line.indexOf("//"));
                if (line.length() != 0) {
                    fileAsm.add(line);
                }
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Error: File cannot be found.");
        }

        // Parse
        initialize();
        firstPass(fileAsm);
        fileBin = secondPass(fileAsm);

        // Write binary file
        try {
            File file = new File(fileName.substring(0, fileName.length() - 4) + ".hack");
            FileWriter fw = new FileWriter(file);
            fw.write(fileBin);
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error: File cannot be found.");
        }

    }

    private static void initialize() {
        // Add predefined symbols
        symbols = new HashMap<>();
        for (int i = 0; i <= 15; i++) {
            symbols.put("R" + i, i + "");
        }
        symbols.put("SP", "0");
        symbols.put("LCL", "1");
        symbols.put("ARG", "2");
        symbols.put("THIS", "3");
        symbols.put("THAT", "4");
        symbols.put("SCREEN", "16384");
        symbols.put("KBD", "24576");

        // comp
        comp = new HashMap<>();
        comp.put("0", "0101010");
        comp.put("1", "0111111");
        comp.put("-1", "0111010");
        comp.put("D", "0001100");
        comp.put("A", "0110000");
        comp.put("M", "1110000");
        comp.put("!D", "0001101");
        comp.put("!A", "0110001");
        comp.put("!M", "1110001");
        comp.put("-D", "0001111");
        comp.put("-A", "0110011");
        comp.put("-M", "1110011");
        comp.put("D+1", "0011111");
        comp.put("A+1", "0110111");
        comp.put("M+1", "1110111");
        comp.put("D-1", "0001110");
        comp.put("A-1", "0110010");
        comp.put("M-1", "1110010");
        comp.put("D+A", "0000010");
        comp.put("D+M", "1000010");
        comp.put("D-A", "0010011");
        comp.put("D-M", "1010011");
        comp.put("A-D", "0000111");
        comp.put("M-D", "1000111");
        comp.put("D&A", "0000000");
        comp.put("D&M", "1000000");
        comp.put("D|A", "0010101");
        comp.put("D|M", "1010101");

        // jump
        jump = new HashMap<>();
        jump.put("null", "000");
        jump.put("JGT", "001");
        jump.put("JEQ", "010");
        jump.put("JGE", "011");
        jump.put("JLT", "100");
        jump.put("JNE", "101");
        jump.put("JLE", "110");
        jump.put("JMP", "111");
    }

    private static void firstPass(ArrayList<String> fileAsm) {
        // Add labels
        int index = 0;
        for (String s : fileAsm) {
            if (s.matches("\\(.+\\)")) {
                symbols.put(s.substring(1, s.length() - 1), index + "");
            } else {
                index++;
            }
        }
    }

    private static String secondPass(ArrayList<String> fileAsm) {
        StringBuilder sb = new StringBuilder();
        int varNo = 0;
        for (String s : fileAsm) {
            switch (s.charAt(0)) {
                case '(': // L_COMMAND
                    // ignore
                    break;
                case '@': // A_COMMAND
                    String add = s.substring(1);
                    String val;
                    if (!add.matches("\\d+")) {
                        val = symbols.get(add);
                        // Add variables
                        if (val == null){
                            val = String.valueOf(16 + varNo);
                            symbols.put(add, val);
                            varNo++;
                        }
                    } else {
                        val = add;
                    }
                    sb.append(0).append(String.format("%15s", Integer.toBinaryString(Integer.parseInt(val))).replace(" ", "0"))
                            .append("\n");
                    break;
                default:  // C_COMMAND
                    String dd = "";
                    String jj = "";
                    int cBegin = 0;
                    int cEnd = s.length();
                    boolean hasDest = false;
                    boolean hasJump = false;
                    if (s.contains("=")) {
                        hasDest = true;
                        dd = s.substring(0, s.indexOf("="));
                        cBegin = s.indexOf("=") + 1;
                    }
                    if (s.contains(";")) {
                        hasJump = true;
                        jj = s.substring(s.indexOf(";") + 1, s.length());
                        cEnd = s.indexOf(";");
                    }

                    String c = comp.get(s.substring(cBegin, cEnd));
                    String d = hasDest ?
                            (dd.contains("A") ? "1" : "0") + (dd.contains("D") ? "1" : "0") + (dd.contains("M") ? "1" : "0") : "000";
                    String j = hasJump ? jump.get(jj) : "000";
                    sb.append(111).append(c).append(d).append(j).append("\n");
                    break;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length == 1 || args[0].matches(".+\\.asm")){
            parseFile(args[0]);
        } else {
            System.out.println("Usage: Assembler filename.asm");
        }
    }
}
