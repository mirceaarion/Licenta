
/**
 * Created by mircea on 7/27/2017.
 */
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import javax.imageio.ImageIO;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class MyImage {
   // public static void main(String []args) throws IOException{
        int width = 800;
        int height = 460;
        Graphics2D g ;

        BufferedImage image = null;
        File f = null;
        String s="Mircea Arion";
        String ss="O sa devina inginer";
        MyImage(){

            f=new File("E:\\mircea.jpg");

        }

        void creeareImagine(String s,String ss, String d,String a){
        image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        // g.drawImage(image,0,0,width,height);
        g = image.createGraphics();


        System.out.print("Citire completa");
        try{
            f = new File("E:\\output.jpg");
            g.setColor(Color.WHITE);
            g.setFont(Font.getFont("as",new Font("Arial",Font.BOLD,25)));
            g.drawString(s,10,20);
            g.drawLine(0,25,200,25);
            g.drawString(ss,10,100);
            g.drawString(d,10,150);
            g.drawString(a,10,200);

            ImageIO.write(image,"jpg",f);
            System.out.println("S`a scris");
        }catch (IOException e){
            e.printStackTrace();
        }
        }

    //}
}
