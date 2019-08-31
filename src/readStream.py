import boto3
import json
from datetime import datetime
import time

#
# Change the name of the my_stream_name parameter to append your Initials (lines 10 and 11)
#

my_stream_name = 'ExampleInputStream'
# my_stream_name = 'ExampleOutputStream'

kinesis_client = boto3.client('kinesis')

response = kinesis_client.describe_stream(StreamName=my_stream_name)

my_shard_id = response['StreamDescription']['Shards'][0]['ShardId']

shard_iterator = kinesis_client.get_shard_iterator(StreamName=my_stream_name,
                                                      ShardId=my_shard_id,
                                                      ShardIteratorType='LATEST')

my_shard_iterator = shard_iterator['ShardIterator']

record_response = kinesis_client.get_records(ShardIterator=my_shard_iterator,
                                              Limit=1)

while 'NextShardIterator' in record_response:
    record_response = kinesis_client.get_records(ShardIterator=record_response['NextShardIterator'],
                                                  Limit=25)

    # print (record_response["MillisBehindLatest"]);
    
    for o in record_response["Records"]:
         # jdat = json.loads(o["Data"])
         jdat = o["Data"]
         print (jdat);

    # wait for 1 seconds
    time.sleep(1)
    
# This code used https://www.arundhaj.com/blog/getting-started-kinesis-python.html as a starting point
# Then we merged in some code from https://aws.amazon.com/blogs/big-data/snakes-in-the-stream-feeding-and-eating-amazon-kinesis-streams-with-python/
