package hadoop.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class UserProductDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		int exitCode = ToolRunner.run(conf, new UserProductDriver(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {
		/*
		 * Validate that two arguments were passed from the command line.
		 */
		if (args.length != 2) {
			System.out.printf("Usage: UserProductDriver <input dir> <output dir>\n");
			System.exit(-1);
		}

		/*
		 * Instantiate a Job object for your job's configuration.
		 */
		Job job = new Job(getConf());

		/*
		 * Specify the jar file that contains your driver, mapper, and reducer.
		 * Hadoop will transfer this jar file to nodes in your cluster running
		 * mapper and reducer tasks.
		 */
		job.setJarByClass(UserProductDriver.class);

		/*
		 * Specify an easily-decipherable name for the job. This job name will
		 * appear in reports and logs.
		 */
		job.setJobName("Session Product");

		/*
		 * Specify the paths to the input and output data based on the
		 * command-line arguments.
		 */
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		/*
		 * Specify the mapper and reducer classes.
		 */
		job.setMapperClass(UserProductMapper.class);
		job.setReducerClass(UserProductReducer.class);

		/*
		 * The input file and output files are text files, so there is no need
		 * to call the setInputFormatClass and setOutputFormatClass methods.
		 */

		/*
		 * The mapper's output keys and values have different data types than
		 * the reducer's output keys and values. Therefore, you must call the
		 * setMapOutputKeyClass and setMapOutputValueClass methods.
		 */
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		/*
		 * Specify the job's output key and value classes.
		 */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		/*
		 * Start the MapReduce job and wait for it to finish. If it finishes
		 * successfully, return 0. If not, return 1.
		 */
		boolean success = job.waitForCompletion(true);
		return(success ? 0 : 1);
	}

}
