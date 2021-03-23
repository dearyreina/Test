package hadoop;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class HumanComparator extends WritableComparator {
    public HumanComparator(){
        super(Human.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Human h1 = (Human)a;
        Human h2 = (Human)b;
        int result = Integer.valueOf(h2.getGender()).compareTo(Integer.valueOf(h1.getGender()));
//        if(result == 0){
//            return Integer.valueOf(h2.getAge()).compareTo(Integer.valueOf(h1.getAge()));
//        }
        return result;
    }
}
