

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;


public class deleteComments {

    enum State {
        CODE,
        SLASH,
        NOTE_MULTILINE,
        NOTE_MULTILINE_STAR,
        NOTE_SINGLELINE,
        BACKSLASH,
        CODE_CHAR,
        CHAR_ESCAPE_SEQUENCE,
        CODE_STRING,
        STRING_ESCAPE_SEQUENCE
    };


    public static String delete_C_Cplusplus_Java_Note(String strToHandle) {
        StringBuilder builder = new StringBuilder();

        State state = State.CODE;// Initiate
        for (int i = 0; i < strToHandle.length(); ++i) {
            char c = strToHandle.charAt(i);
            switch (state) {
                case CODE:
                    if (c == '/') {
                        state = State.SLASH;
                    }else {
                        builder.append(c);
                        if(c=='\'') {
                            state=State.CODE_CHAR;
                        }else if(c=='\"') {
                            state=State.CODE_STRING;
                        }
                    }
                    break;
                case SLASH:
                    if (c == '*') {
                        state = State.NOTE_MULTILINE;
                    } else if (c == '/') {
                        state = State.NOTE_SINGLELINE;
                    } else {
                        builder.append('/');
                        builder.append(c);
                        state = State.CODE;
                    }
                    break;
                case NOTE_MULTILINE:
                    if(c=='*') {
                        state=State.NOTE_MULTILINE_STAR;
                    }else {
                        if(c=='\n') {
//                            builder.append("\r\n");
                        }
                        state=State.NOTE_MULTILINE;
                    }
                    break;
                case NOTE_MULTILINE_STAR:
                    if(c=='/') {
                        state=State.CODE;
                    }else if(c=='*') {
                        state=State.NOTE_MULTILINE_STAR;
                    }
                    else {
                        state=State.NOTE_MULTILINE;
                    }
                    break;
                case NOTE_SINGLELINE:
                    if(c=='\\') {
                        state=State.BACKSLASH;
                    }else if(c=='\n'){
                        builder.append("\r\n");
                        state=State.CODE;
                    }else {
                        state=State.NOTE_SINGLELINE;
                    }
                    break;
                case BACKSLASH:
                    if(c=='\\' || c=='\r'||c=='\n') {
                        if(c=='\n') {
//                            builder.append("\r\n");
                        }
                        state=State.BACKSLASH;
                    }else {
                        state=State.NOTE_SINGLELINE;
                    }
                    break;
                case CODE_CHAR:
                    builder.append(c);
                    if(c=='\\') {
                        state=State.CHAR_ESCAPE_SEQUENCE;
                    }else if(c=='\'') {
                        state=State.CODE;
                    }else {
                        state=State.CODE_CHAR;
                    }
                    break;
                case CHAR_ESCAPE_SEQUENCE:
                    builder.append(c);
                    state=State.CODE_CHAR;
                    break;
                case CODE_STRING:
                    builder.append(c);
                    if(c=='\\') {
                        state=State.STRING_ESCAPE_SEQUENCE;
                    }else if(c=='\"') {
                        state=State.CODE;
                    }else {
                        state=State.CODE_STRING;
                    }
                    break;
                case STRING_ESCAPE_SEQUENCE:
                    builder.append(c);
                    state=State.CODE_STRING;
                    break;
                default:
                    break;
            }
        }
        return builder.toString();
    }


    public static String readFile(String inputFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(inputFileName);
            InputStreamReader dis = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(dis);
            String s;
            while ((s = reader.readLine()) != null) {
                builder.append(s);
                builder.append("\n");
            }
            reader.close();
            dis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return builder.toString();
    }

    public static void writeFile(String outputFileName, String strHandled) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFileName);
            OutputStreamWriter dos = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(dos);
            writer.write(strHandled);
            writer.close();
            dos.close();
            fos.close();
            System.out.println("code that without note has been saved successfully in " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        System.out.println("The fileName that will be delete note:");
//        String inputFileName = in.nextLine();
//        System.out.println("The fileName that will save code without note:");
//        String outputFileName = in.nextLine();
//
//        String strToHandle = readFile(inputFileName);
//        String strHandled = delete_C_Cplusplus_Java_Note(strToHandle);
//        writeFile(outputFileName, strHandled);

        ArrayList<String> files = new ArrayList<String>();
        File file = new File("/Users/moeachto/PycharmProjects/bug_injection/Data/TestFiles/Hadoop-HDFS/Before");
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("files：" + tempList[i]);
                files.add(tempList[i].toString());

                writeFile(tempList[i].toString().replace(".txt", "-d.txt"), delete_C_Cplusplus_Java_Note(readFile(tempList[i].toString())));
            }
        }

    }

}
