import json
import boto3
import random
import datetime

kinesis = boto3.client('kinesis')

while True:
        data = random.choice(['red', 'orange', 'blue', 'green', 'yellow'])
        print(data)
        kinesis.put_record(
                StreamName="ExampleInputStream",
                Data=data,
                PartitionKey="partitionkey")
 

