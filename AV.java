import java.io.*;

public class AV {
    public void clearWhiteSpace(File f )throws FileNotFoundException,IOException{
        if (f.exists()){
            BufferedReader br = new BufferedReader(new FileReader(f));
            File tempfile = new File("temp");
            if(tempfile.exists());
                tempfile.delete();
            tempfile.createNewFile();
            PrintWriter pw = new PrintWriter(tempfile);
            String line=br.readLine();
            StringBuilder str = new StringBuilder();
            while (line!=null){
                for (int i=0;i<line.length();i++) {
                    if (line.charAt(i) != ' ') {
                        str.append(line.substring(i, line.length()));
                        pw.println(str.toString());
                        break;
                    }
                }
                str=new StringBuilder();
                line=br.readLine();
            }
            br.close();
            pw.close();
            f=tempfile;
            tempfile.delete();
        }
        else {
            System.out.println("File Not Found!");
            System.exit(0X4444);
        }
    }
    public void printGenCode (File f,int delay)throws FileNotFoundException,IOException{ // delay in milisec
        FirstBlock demo = new FirstBlock();
        Update_second code = new Update_second();
        BufferedReader ReaderFile = new BufferedReader(new FileReader(f));
        StringBuilder temp_line = new StringBuilder();
        byte tempcount=0;
        File cf = new File ("./module02/CodeWriter.java");
        if (!cf.exists()){
            cf.createNewFile();
        }
        PrintWriter WriteGenFile = new PrintWriter(cf);
        String line =ReaderFile.readLine();
        WriteGenFile.println("public class CodeWriter extends WorkSpace {\n" + "CodeWriter(){");
        while (line!=null){
            if (line.contains("algo:"))
                break;
            demo.loadVariable(line);
          line=ReaderFile.readLine();
        }
        temp_line.append("super(20,new String[] {");
        for (int i=0;i<GlobalVariable.Variable_Name.length;i++){
            if (GlobalVariable.Variable_Name[i]!=null){
                temp_line.append("\""+GlobalVariable.Variable_Name[i]+"\"");
                tempcount++;
            }
            else
                break;
            if (GlobalVariable.Variable_Name[i+1]!=null){
                temp_line.append(",");
            }
        }
        temp_line.append("},new int[] {");
        for (int i =0;i<tempcount;i++){
            temp_line.append(GlobalVariable.Variable_Value[i]);
            if (i+1!=tempcount){
                temp_line.append(",");
            }
        }
        temp_line.append("});\ndelay="+delay+";\n}\n\tpublic void WriteOperation() {");
        WriteGenFile.println(temp_line);


        line =ReaderFile.readLine();
        while (line !=null){
            WriteGenFile.println(code.convertAVCode(line));
            line=ReaderFile.readLine();
        }
        WriteGenFile.println("}}");           //class and main close
        WriteGenFile.close();
        ReaderFile.close();
    }

    public static void main(String[] args) throws IOException{
        AV av = new AV();
        System.out.println("System file location is :"+args[0]);
       File f = new File (args[0]);
        av.clearWhiteSpace(f);
        if (args.length==1)
            av.printGenCode(f,100);
        else 
            av.printGenCode(f,Integer.parseInt(args[1]));

        File runfile = new File ("./module02/CodeWriter.java");
        Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe /C \"cd module02 && javac CodeWriter.java && java Animation\"");

        // new Animation();

    }


}
