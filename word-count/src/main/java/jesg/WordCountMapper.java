package jesg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Map<String, Integer> partialResults;

    @Override
    protected void setup(Context context) throws IOException,
            InterruptedException {
        partialResults = new HashMap<String, Integer>();
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);

        while (tokenizer.hasMoreTokens()) {
            increment(tokenizer.nextToken());
        }
    }

    private void increment(String word) {
        Integer count = partialResults.get(word);

        if (count == null) {
            count = 0;
        }

        count++;

        partialResults.put(word, count);
    }

    @Override
    protected void cleanup(Context context) throws IOException,
            InterruptedException {

        for (Entry<String, Integer> entry : partialResults.entrySet()) {
            context.write(new Text(entry.getKey()),
                    new IntWritable(entry.getValue()));
        }

    }
}

