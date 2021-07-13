package io.github.ecsoya.fabric.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author kuan
 * Created on 2020/5/23.
 * @description
 */
public class FileUtils {
    private static final int BUFFER = 2048;

    public static byte[] readFile(String filePath) throws IOException {
        FileInputStream inputStream  = new FileInputStream(filePath);
        byte[]  fileBytes = new byte[inputStream.available()];
        inputStream.read(fileBytes);
        return fileBytes;
    }

    public static void writeFile(String content,String fileName,boolean append) throws IOException {
        File file = new File(fileName);

        if (!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }

        FileWriter fileWriter = new FileWriter(fileName,append);
        fileWriter.write(content);
        fileWriter.close();
    }

    /**
     * 解压Zip文件
     * @param path 文件目录
     */
    public static String unZip(String path)
    {
        int count = -1;
        String savepath = "";
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        savepath = path.substring(0, path.lastIndexOf(".")) + File.separator;
        new File(savepath).mkdir();
        ZipFile zipFile = null;
        try
        {
            zipFile = new ZipFile(path, Charset.forName("gbk"));
            Enumeration<?> entries = zipFile.entries();
            while(entries.hasMoreElements())
            {
                byte buf[] = new byte[BUFFER];
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String filename = entry.getName();
                boolean ismkdir = false;
                if(filename.lastIndexOf("/") != -1){
                    ismkdir = true;
                }
                filename = savepath + filename;
                if(entry.isDirectory()){
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if(!file.exists()){
                    if(ismkdir){
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs();
                    }
                }
                file.createNewFile(); //创建文件
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, BUFFER);
                while((count = is.read(buf)) > -1)
                {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                is.close();
            }
            zipFile.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                if(bos != null){
                    bos.close();
                }
                if(fos != null) {
                    fos.close();
                }
                if(is != null){
                    is.close();
                }
                if(zipFile != null){
                    zipFile.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return savepath;
    }

}
