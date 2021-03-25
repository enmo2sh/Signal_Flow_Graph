package Control;

public class relation {
    String gain;
    String to_vertex;
    relation(String gain,String to_vertex){
        this.gain=gain;
        this.to_vertex=to_vertex;
    }
    String getGain(){
        return  gain;
    }
    String getTO_vertex(){
        return  to_vertex;
    }
}
