package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class HiveTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem.get(conf).delete(new Path("output/hivetest_out"), true);
        Job job = Job.getInstance(conf);
        job.setJarByClass(HiveTest.class);
        job.setMapperClass(HiveTestMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //job.setOutputFormatClass(HiveIgnoreKeyTextOutputFormat.class);
        job.setNumReduceTasks(0);
        FileInputFormat.setInputPaths(job,new Path("input/hivetest"));
        FileOutputFormat.setOutputPath(job,new Path("output/hivetest_out/"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
