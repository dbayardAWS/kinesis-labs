package com.amazonaws.services.kinesisanalytics;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisProducer;
import org.apache.flink.streaming.connectors.kinesis.config.ConsumerConfigConstants;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;

/**
 * A Kinesis Data Analytics for Java application to calculate word count for
 * records in a Kinesis Data Stream using a tumbling window.
 * 
 * Based on the example at https://docs.aws.amazon.com/kinesisanalytics/latest/java/examples-tumbling.html , but modified for readability.
 *
 * Be sure to append your Initials to the inputStreamName and outputStreamName (lines 33 and 34)
 *
 * And if not using the us-east-1 region, modify line 32
 */
public class TumblingWindowStreamingJob {

    private static final String region = "us-east-1";
    private static final String inputStreamName = "ExampleInputStream";
    private static final String outputStreamName = "ExampleOutputStream";

    private static DataStream<String> createSourceFromStaticConfig(
            StreamExecutionEnvironment env) {
        Properties inputProperties = new Properties();
        inputProperties.setProperty(ConsumerConfigConstants.AWS_REGION, region);
        inputProperties.setProperty(ConsumerConfigConstants.STREAM_INITIAL_POSITION,
                "LATEST");

        return env.addSource(new FlinkKinesisConsumer<>(inputStreamName,
                new SimpleStringSchema(), inputProperties)).name(inputStreamName);
    }

    private static FlinkKinesisProducer<String> createSinkFromStaticConfig() {
        Properties outputProperties = new Properties();
        outputProperties.setProperty(ConsumerConfigConstants.AWS_REGION, region);
        outputProperties.setProperty("AggregationEnabled", "false");    

        FlinkKinesisProducer<String> sink = new FlinkKinesisProducer<>(new
                SimpleStringSchema(), outputProperties);
        sink.setDefaultStream(outputStreamName);
        sink.setDefaultPartition("0");
        return sink;
    }

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> input = createSourceFromStaticConfig(env);

        ObjectMapper jsonParser = new ObjectMapper();
        input.map(value -> { // Parse the JSON
            JsonNode jsonNode = jsonParser.readValue(value, JsonNode.class);
            return new Tuple2<>(jsonNode.get("TICKER").asText(), 1.0);
        }).returns(Types.TUPLE(Types.STRING, Types.DOUBLE)) // Tokenizer for generating words, ie extracting the ticker
                .keyBy(0) // Logically partition the stream for each word
                .timeWindow(Time.seconds(5)) // Tumbling window definition
                .sum(1) // Sum the number of words per partition
                .map(value -> value.f0 + "," + value.f1.toString())
                .addSink(createSinkFromStaticConfig()).name(outputStreamName);

        env.execute("Word Count via Tumbling Window");
    }


}
