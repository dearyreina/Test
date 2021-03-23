package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class HumanDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem.get(conf).delete(new Path("D:\\humanresult"), true);
        Job job = Job.getInstance(conf);
        job.setJarByClass(HumanDriver.class);
        job.setMapperClass(HumanMapper.class);
        job.setReducerClass(HumanReducer.class);
        job.setMapOutputKeyClass(Human.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setGroupingComparatorClass(HumanComparator.class);
        job.setNumReduceTasks(2);
        FileInputFormat.setInputPaths(job,new Path("D:\\human.txt"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\humanresult"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }

}
