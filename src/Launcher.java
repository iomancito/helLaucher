
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Launcher {

    
    
    public static void main(String[] args) {
        
        String server = "http://74.91.114.46/";
        String xmlFile = "data.xml";
        String cliente = "Client2.exe";
        String parametroCliente = "vodoocn2";
        
        Ventana miVentana = new Ventana();
        miVentana.setVisible(true);
        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea ta = new JTextArea(100,100);
        JScrollPane scrollPane = new JScrollPane(ta);
        ta.setEditable(false);
        miVentana.add(ta);
        
        ParseXML xml;
        Item itemArray[];
        try{
            ta.append("\nConectando con el servidor.");
            xml = new ParseXML(server + xmlFile);
            xml.parseDoc();
            ta.append("\n...OK.");
            itemArray = xml.parseDoc();
            
            for(int x = 0; x < itemArray.length; x++){
                ta.append("\nChecking " + itemArray[x].path);
                if(itemArray[x].checkFile())ta.append(" ...OK");
                else{
                    ta.append("\n...NOK");
                    //descargar fichero
                    ta.append("\nDescargando " + itemArray[x].getPath());
                    if(itemArray[x].downloadFile(server)) ta.append("...OK");
                    else ta.append("\n...NOK");                    
                }
            }
            ta.append("\nEjecutando " + cliente);

            Process p = Runtime.getRuntime().exec(cliente);
            System.exit(1);
        }catch(MalformedURLException e){
            ta.append("\nError, al recuperar archivo.");
        }catch(IOException e){
            e.printStackTrace();
            ta.append("\n Error al abrir el archivo.");
        }catch(Exception e){
            e.printStackTrace();
            ta.append("\nError.");
            ta.append(e.toString());
        }
        
       


    }
}