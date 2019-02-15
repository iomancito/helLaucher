/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ioman
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.CRC32;

public class Item {
    String path;
    String crc;
    
    public Item (String n, String c){
        this.path = n;
        this.crc = c;
    }
    
    public String getPath(){
        return this.path;
    }
    
    public String getCrc(){
        return this.crc;
    }
    
    public boolean checkFile() throws IOException{
        CRC32 crc = new CRC32();
        File file = new File(this.path);
        if(file.exists()){
            InputStream fs = new FileInputStream(this.path);
            int cnt;
            while((cnt = fs.read()) != -1) crc.update(cnt);    
            return Long.toHexString(crc.getValue()).compareTo(this.crc) == 0;
        }else return false;
    }
    /**
     * 
     * @param path ruta en String
     * @return el crc32 del fichero en la ruta path
     * @throws IOException 
     */
    public boolean checkFile(String path) throws IOException{
        CRC32 crc = new CRC32();
        
        InputStream fs = new FileInputStream(path);
        int cnt;
        while((cnt = fs.read()) != -1) crc.update(cnt);
        
        return crc.getValue() == Long.parseLong(this.crc);
    }
    
    /**
     * 
     * @param f fichero en File
     * @return
     * @throws IOException 
     */
    public boolean checkFile (File f) throws IOException{
        CRC32 crc = new CRC32();
        
        InputStream fs = new FileInputStream(f);
        int cnt;
        while((cnt = fs.read()) != -1) crc.update(cnt);
        
        return crc.getValue() == Long.parseLong(this.crc);
    }
    
    public boolean downloadFile(String uri) throws IOException{
        
        String fuente = uri + this.path;
        String destino = this.path;
        try{
            URL website = new URL(fuente);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destino);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);  
        return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
