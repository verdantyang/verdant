package com.ycy.demo.sequencefile;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.ReflectionUtils;

public class SequenceFileTest {  
      
    private static SequenceFile.Reader reader = null;  
    private static JobConf conf = new JobConf();  
  
    public static class ReadFileMapper extends  
            Mapper<LongWritable, Text, LongWritable, Text> {  
  
        /* (non-Javadoc) 
         * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context) 
         */  
        @Override  
        public void map(LongWritable key, Text value, Context context) {  
            key = (LongWritable) ReflectionUtils.newInstance(  
                    reader.getKeyClass(), conf);  
            value = (Text) ReflectionUtils.newInstance(  
                    reader.getValueClass(), conf);  
            try {  
                while (reader.next(key, value)) {  
                    System.out.printf("%s\t%s\n", key, value);  
                    context.write(key, value);  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
  
    }  
    /** 
     * @param args 
     * @throws IOException 
     * @throws InterruptedException 
     * @throws ClassNotFoundException 
     */  
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {  
          
        Job job = new Job(conf,"Read seq file");  
        job.setJarByClass(SequenceFileTest.class);  
        job.setMapperClass(ReadFileMapper.class);  
        job.setMapOutputValueClass(Text.class);  
        Path path = new Path("logfile2");  
        FileSystem fs = FileSystem.get(conf);  
        Writer w=SequenceFile.createWriter(null, null, null);

        reader = new SequenceFile.Reader(fs, path, conf);  
        FileInputFormat.addInputPath(job, path); 
        SequenceFileOutputFormat.setOutputPath(conf, path);
        FileOutputFormat.setOutputPath(conf, new Path("result"));  
        System.exit(job.waitForCompletion(true)?0:1);  
    }  
}  