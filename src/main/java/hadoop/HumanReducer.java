package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class HumanReducer extends Reducer <Human, IntWritable, IntWritable,IntWritable>{
    @Override
    protected void reduce(Human key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        context.write(new IntWritable(key.getGender()),new IntWritable(key.getAge()));
    }
}
