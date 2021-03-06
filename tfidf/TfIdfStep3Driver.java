package tfidf;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class TfIdfStep3Driver extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {
    if(args.length != 2) {
      throw new IllegalArgumentException("Bad number of arguments: "
          + args.length);
    }
    String input = args[0];
    String output = args[1];
    
    JobConf conf = new JobConf(getConf(), TfIdfStep3Driver.class);
    conf.setJobName(this.getClass().getName());
    
    FileInputFormat.setInputPaths(conf, new Path(input));
    FileOutputFormat.setOutputPath(conf, new Path(output));

    conf.setInputFormat(SequenceFileInputFormat.class);
    //conf.setOutputFormat(SequenceFileOutputFormat.class);
    
    conf.setMapperClass(TfIdfStep3Mapper.class);
    
    conf.setMapOutputKeyClass(TermDocIdWritable.class);
    conf.setMapOutputValueClass(DoubleWritable.class);
    
    //conf.setOutputKeyClass(TermDocIdWritable.class);
    //conf.setOutputValueClass(TfNWritable.class);
    
    JobClient.runJob(conf);
    return 0;
  }

  public static void main(String[] args) throws Exception {
    TfIdfStep3Driver driver = new TfIdfStep3Driver();
    int exitCode = ToolRunner.run(driver, args);
    System.exit(exitCode);
  }
}