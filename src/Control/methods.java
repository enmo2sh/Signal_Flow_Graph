package Control;
import javax.sound.sampled.Line;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
public class methods {
    private int n;
    private static Map<String,List<relation>> mymap = new HashMap<String,List<relation>>();
    private LinkedList<String> Forward=new LinkedList<>();
    private LinkedList<String> Loops=new LinkedList<>();
    private Map<String,LinkedList<String>> vertexOfForward=new HashMap<>();
    private Map<String,LinkedList<String>> vertexOfLoops=new HashMap<>();
    private LinkedList<String> nonTouchingLoops=new LinkedList<>();
    private LinkedList<String>nonTouchingForward=new LinkedList<>();
    methods(int n){
        this.n=n;
        for(int i=1;i<n+1;i++){
            List <relation> temp = new LinkedList<>();
            mymap.put(((new StringBuilder()).append("V").append(String.valueOf(i)).toString()),temp);
        }
    }

    Map<String,List<relation>> getinputs(String equ){
        String[] temp=equ.split("=");
        String[] vertices=temp[1].split("\\+");
        for(int i=0;i<vertices.length;i++) {
            String[] term = vertices[i].split("\\*");
            relation r = new relation(term[0], temp[0]);
            mymap.get(term[1]).add(r);
        }
        return mymap;
    }
    void print(){
        for (String name: mymap.keySet()){
            String key = name.toString();
            System.out.print(key+"   ");
            for(int i=0;i<mymap.get(key).size();i++){
                System.out.print(mymap.get(key).get(i).getGain()+"  "+mymap.get(key).get(i).getTO_vertex()+"    ");
            }
            System.out.println();
        }
    }
    void print2(){
        for (String name: vertexOfForward.keySet()){
            String key = name.toString();
            System.out.print(key+"   ");
            for(int i=0;i<vertexOfForward.get(key).size();i++){
                System.out.print(vertexOfForward.get(key).get(i)+"   ");
            }
            System.out.println();
        }
    }
    boolean isValid(String line){
     String whole="^V\\d\\D*[=]((([-])?(\\d)?(\\D*(\\d)?\\D*([+-/*])?)*[*]V\\d[+])*)?([-])?(\\d)?(\\D*(\\d)?\\D*([+-/*])?)*[*]V\\d";
        Pattern help = Pattern.compile(whole);
        Matcher A=help.matcher(line);
        if(A.matches())
            return true;
        else
            return false;
    }
    LinkedList<String> getForwadPaths(String vertex,String path,String finalVertex,LinkedList<String>visited){
        if (!visited.contains(vertex)){
                for(int i=0;i<mymap.get(vertex).size();i++) {
                if (mymap.get(vertex).get(i).getTO_vertex().equals(finalVertex)) {
                    path = path + mymap.get(vertex).get(i).getGain();
                    LinkedList<String> temp=new LinkedList<>();
                    for(int y=0;y<visited.size();y++)
                        temp.add(visited.get(y));
                    if(!temp.contains(vertex))
                         temp.add(vertex);
                    if(!temp.contains(mymap.get(vertex).get(i).getTO_vertex()))
                        temp.add(mymap.get(vertex).get(i).getTO_vertex());
                    vertexOfForward.put(path,temp);
                    Forward.add(path);
                    return Forward;
                } else if((mymap.get(mymap.get(vertex).get(i).getTO_vertex()).size()!=0)&&(!visited.contains(mymap.get(vertex).get(i).getTO_vertex()))){
                        String path2 = path + mymap.get(vertex).get(i).getGain() + "*";
                        if(!visited.contains(vertex))
                            visited.add(vertex);
                        getForwadPaths(mymap.get(vertex).get(i).getTO_vertex(),path2,finalVertex,visited);

                }
             }
                if(visited.contains(vertex))
                    visited.remove(vertex);
        }
        return Forward;
    }
    LinkedList<String> getLoops(String vertex,String path,LinkedList<String>visited){
            for(int i=0;i<mymap.get(vertex).size();i++){
                if(visited.contains(mymap.get(vertex).get(i).getTO_vertex())){
                    String loop=getLoopname(path,mymap.get(vertex).get(i).getTO_vertex(),mymap.get(vertex).get(i).getGain(),visited);
                    if(!Loops.contains(loop))
                         Loops.add(loop);
                }
                else{
                    if(mymap.get(mymap.get(vertex).get(i).getTO_vertex()).size()!=0){
                        String path2 = path + mymap.get(vertex).get(i).getGain() + "*";
                        if(!visited.contains(vertex))
                            visited.add(vertex);
                        visited.add(mymap.get(vertex).get(i).getTO_vertex());
                        getLoops(mymap.get(vertex).get(i).getTO_vertex(),path2,visited);
                    }
                    visited.remove(mymap.get(vertex).get(i).getTO_vertex());
                }
            }
        return Loops;
    }

String getLoopname(String path,String visitedBefore,String gain,LinkedList<String>visited){
        String path2=path+gain;
        String temp[]=path2.split("\\*");
        int count=0; String result="";
        LinkedList<String> vertex=new LinkedList<>();
        while(!visited.get(visited.size()-1-count).equals(visitedBefore)){
            result=result+temp[temp.length-1-count]+"*";
            vertex.add(visited.get(visited.size()-1-count));
            count++;
        }
        vertex.add(visitedBefore);
        result=result+temp[temp.length-1-count];
        result=sortString(result);
        result=result.substring(0,result.length()-1);
        if(!vertexOfLoops.containsKey(result))
            vertexOfLoops.put(result, vertex);

        return result;
}
String sortString(String result){
        String res="";
        String temp[]=result.split("\\*");
        Arrays.sort(temp);
        for(int i=0;i<temp.length;i++)
            res=res+temp[i]+"*";
        return res;
}
LinkedList<String> getNonTouchingLoops(){
    for (String name: vertexOfLoops.keySet()){
        String key = name.toString();
        LinkedList<String>loop1=vertexOfLoops.get(key);
        for(String name2:vertexOfLoops.keySet()){
            String key2=name2.toString();
            if(!key.equals(key2)){
                LinkedList<String>loop2=vertexOfLoops.get(key2);
                boolean res=isTouched(loop1,loop2);
                if(res==false){
                    String newLoop=key+"*"+key2;
                    newLoop=sortString(newLoop);
                    newLoop=newLoop.substring(0,newLoop.length()-1);
                    if(!nonTouchingLoops.contains(newLoop))
                        nonTouchingLoops.add(newLoop);
                }
            }
    }
    }
    return nonTouchingLoops;
}
LinkedList<String> getNonTouchingForward(){
    for (String name: vertexOfForward.keySet()){
        String PK="";
        String key = name.toString();
        LinkedList<String>loop1=vertexOfForward.get(key);
        for(String name2:vertexOfLoops.keySet()){
            String key2=name2.toString();
                LinkedList<String>loop2=vertexOfLoops.get(key2);
                boolean res=isTouched(loop1,loop2);
                if(res==false)
                    PK=PK+"+"+key2;
            }
        nonTouchingForward.add(PK);
    }
        return nonTouchingForward;
}
boolean isTouched(LinkedList<String>loop1,LinkedList<String>loop2){
        for(int i=0;i<loop1.size();i++){
            if(loop2.contains(loop1.get(i)))
                return true;
        }
        return false;
}
String result1(){
        String num="{ ";
        for (int h=0;h<Forward.size();h++){
            if(nonTouchingForward.get(h).equals(""))
                num=num+Forward.get(h)+"+ ";
            else
                num=num+Forward.get(h)+"*"+nonTouchingForward.get(h)+"+ ";
        }
    num=num.substring(0,num.length()-2)+" }";
    return num;
}
String result2(){
        String deno="1- { [";
        for(int f=0;f<Loops.size();f++)
            deno=deno+Loops.get(f)+"+ ";
        deno=deno.substring(0,deno.length()-2)+"]";
        if(nonTouchingLoops.size()==0)
            deno= deno+" }";
        else {
            deno = deno + "+ [";
            for (int h = 0; h < nonTouchingLoops.size(); h++)
                deno = deno + nonTouchingLoops.get(h) + "+ ";
            deno = deno.substring(0, deno.length() - 2) + "] }";
        }
        return deno;
    }
}
