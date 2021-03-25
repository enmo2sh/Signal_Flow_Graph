package Control;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import javax.swing.JFrame;
import java.awt.*;
import java.util.List;
import javax.swing.JLabel;

public class GUI extends JFrame {
    JLabel l1;

    int n;
    private Map<String, java.util.List<relation>> map = new HashMap<String, List<relation>>();
    Map<String, List<relation>> mymap;
    String num;
    String den;
    public void setProps(int n,Map<String,List<relation>>map,String num,String den) {
        this.n=n;
        this.map=map;
        this.num=num;
        this.den=den;
        mymap = new TreeMap<String, List<relation>>(map);

    }
    public  GUI() {
        setSize(1500,800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 0));
        setForeground(new Color(51, 51, 153));
        setBackground(new Color(51, 51, 153));
        setLayout(new BorderLayout());
        setLayout(new FlowLayout());
        setVisible(true);
    }


    public void paint (Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.drawLine(1000/n,100,(1000/n)+50,100);
        g.drawString("Forward",(1000/n)+70,100);
        g.setColor(Color.RED);
        g.drawLine(1000/n,120,(1000/n)+50,120);
        g.drawString("Backward",(1000/n)+70,120);
        g.setColor(Color.orange);
        g.drawLine(1000/n,140,(1000/n)+50,140);
        g.drawString("Self loop",(1000/n)+70,140);
        g.setColor(Color.WHITE);
        int xtemp=1000/n;
        Map<String,Integer> coord=new HashMap<>();
        g.setFont(new Font("Tahoma", Font.PLAIN, 160/n));
            for(String name:mymap.keySet()) {
                g.drawRect(xtemp, 200, 300 / n, 300 / n);
                String vertex = name.toString();
                g.drawString(vertex, xtemp + 100 / n, 200 + 200 / n);
                coord.put(vertex, xtemp+5);
                xtemp+=(300/n)+(1000/n);
            }
        int newY=200+300/n+20;
        g.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for(String name:mymap.keySet()) {
                String v=name.toString();
                List<relation> temp=mymap.get(v);
                int y=200+300/n;
                for(int p=0;p<temp.size();p++){
                    int newX=coord.get(temp.get(p).getTO_vertex());
                    if(newX==coord.get(v)){  //self loop
                        g.setColor(Color.ORANGE);
                        g.drawLine(newX,200,newX,170);//up
                        g.drawLine(newX,170,newX+300/n,170);//right
                        g.drawLine(newX+300/n,170,newX+300/n,200+150/n);//down
                        g.drawLine(newX+300/n,200+150/n,newX+200/n,200+150/n);//left
                        g.drawString(temp.get(p).getGain(),newX+150/n,185);
                    }
                    else {
                        if(coord.get(v)>coord.get(temp.get(p).getTO_vertex())) //backward
                            g.setColor(Color.RED);
                        else  //forward
                            g.setColor(Color.BLUE);
                        g.drawLine(coord.get(v),y,coord.get(v),newY);
                        g.drawLine(coord.get(v), newY, newX, newY);
                        g.drawLine(newX, newY, newX, y);
                        g.drawString(temp.get(p).getGain(),coord.get(v)-((coord.get(v)-newX)/2),newY+10);
                        newY += 20;
                        coord.put(v,coord.get(v)+(300/(n*n)));
                        coord.put(temp.get(p).getTO_vertex(),newX+300/(n*n));
                    }
                }
            }
            g.setColor(Color.magenta);
        g.setFont(new Font("Tahoma", Font.PLAIN, 120/n));
            g.drawString("result = "+num,100,newY+100);
            g.drawLine(200,newY+150,1200,newY+150);
            if(den.length()<100)
                g.drawString(den,200,newY+200);
            else {
                g.drawString(den.substring(0, 100), 50, newY + 200);
                g.drawString(den.substring(101,den.length()-1),100,newY+250);
            }

    }

}




