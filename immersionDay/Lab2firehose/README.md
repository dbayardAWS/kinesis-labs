# Immersion Day Lab 2 - Process Data using a Lambda function and send to Kinesis Data Firehose

In this section, we create an Amazon Kinesis Data Stream and populate the stream using a historic data set of taxi trips made in NYC.

In this section, we create an Amazon Kinesis Data Firehose delivery stream and send data to it from Kinesis Delivery Stream created in lab section 1 using a Lambda trigger.  The Lambda function also filters out the spurious data in the incoming events and then sends the clean events to a Firehose Delivery Stream in batch mode (using the PutRecordBatch API). The Lambda trigger is configured both in standard (polling) and Enhanced FanOut mode to illustrate the differences between the two.
 
This lab covers 3 different ways to creating the resources for the above described functionality and you can choose your approach.

|Approach |Description |
|---- | ----|
|[Cloud Formation](Part1CF.md) |Create resources using CloudFormation template (this is the easiest) |
|[AWS Console UI](Part1UI.md) |Create resources using AWS Console (next easiest.  good for understanding what's happening.) |
|[AWS CLI](Part1CLI.md) |Create resources using AWS CLI (Use this option only if you have a linux machine with AWS SDK installed and if you know how to use AWS profiles) |

When done with creating resources, you will proceed to Part 2 of this Lab to stream data through the Lambda function and Kinesis Firehose and validate output.

*Hint: If in doubt, we suggest creating resources via the [Cloud Formation](Part1CF.md) approach.  While the Cloud Formation is running, you can review the steps in the [AWS Console UI](Part1UI.md) instructions to have a better idea of what the Cloud Formation template is doing behind the scenes.*



