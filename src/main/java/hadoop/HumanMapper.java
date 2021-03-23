package hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HumanMapper extends Mapper<LongWritable, Text, Human, IntWritable> {
    Human h = new Human();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        h.setGender(Integer.parseInt(str.split("_")[0]));
        h.setAge(Integer.parseInt(str.split("_")[1]));

        context.write(h,new IntWritable(1));
    }
}
