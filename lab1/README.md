# LAB 1 - Getting Started with Kinesis Data Analytics for Java
In this lab you will setup a basic data lake environment, load some data, and begin to use it.



## Contents
* [Before You Begin](#before-you-begin)
* [Setup your initial Data Lake on S3](#setup-your-initial-data-lake-on-s3)
* [Catalog our new dataset](#catalog-our-new-dataset)
* [Query our new data](#query-our-new-data)
* [OPTIONAL Make our data faster](#optional-make-our-data-faster)
* [Before You Leave](#before-you-leave)

## Before You Begin
* Determine and capture the following information.
  * [Your_AWS_Account_Id]
  * [Your_AWS_User_Name]
  * [Your_AWS_Password]
  * [AWS_Region_to_be_used_for_Lab]
* Login to the [AWS Console](https://console.aws.amazon.com/). 
* Switch your console to the assigned [AWS Region](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/Concepts.RegionsAndAvailabilityZones.html).  

## Create a Cloud9 Development Environment
Cloud9 is ...

We will use Cloud9 for ...

Learn more [here](https://aws.amazon.com/products/storage/data-lake-storage/).

### Navigate to the Cloud9 Console

* In the AWS Console, use the Services menu and navigate to the Cloud9 console.  One way to do so, is to expand the Services top menu and type "Cloud9" in the service search field.

![screenshot](images/C90.png)

### Create a Cloud9 development environment

* In the Cloud9 console, click on "Create environment".  

* Enter "kdaj-[your_initials]" for the name.

Note: As multiple users may be using the same account bucket in the labs, please use your initials when creating/naming the environment.

* Fill-in a description.

* Click "Next Step"

![screenshot](images/C91.png)

* On the "Configure settings" page, leave the defaults as-is.

* Click "Next Step"

![screenshot](images/C92.png)

* Click "Create Environment"

![screenshot](images/C93.png)

A new browser tab will open for your new environment.  It will take a few minutes for the environment to be ready.   While waiting, you can review the Cloud9 IDE tutorial in the step below.

### Get familiar with Cloud9
If new to Cloud9, review the IDE tutorial at [https://docs.aws.amazon.com/cloud9/latest/user-guide/tutorial.html#tutorial-tour-ide](https://docs.aws.amazon.com/cloud9/latest/user-guide/tutorial.html#tutorial-tour-ide)


### Configure your Cloud9 environment for the prerequisites

* Open up your Cloud9 environment if not already open

![screenshot](images/prereq1.png)

* Click on the + sign to use the pop-up menu to open a new Terminal tab

![screenshot](images/prereq2.png)

* In the terminal, paste and run the following code to setup the java 1.8 environment.

```
sudo yum -y update
sudo yum -y install java-1.8.0-openjdk-devel
sudo update-alternatives --set javac /usr/lib/jvm/java-1.8.0-openjdk.x86_64/bin/javac
sudo update-alternatives --set java /usr/lib/jvm/jre-1.8.0-openjdk.x86_64/bin/java
java -version

```

![screenshot](images/prereq3.png)

* In the terminal, paste and run the following code to setup the Maven environment.

```
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
mvn -version

```

![screenshot](images/prereq4.png)

* In the terminal, paste and run the following code to setup the Flink 1.6.2 environment and to compile the Kinesis connector for Flink.

```
wget https://github.com/apache/flink/archive/release-1.6.2.zip
unzip release-1.6.2.zip
cd flink-release-1.6.2
mvn clean package -B -DskipTests -Dfast -Pinclude-kinesis -pl flink-connectors/flink-connector-kinesis

```

![screenshot](images/prereq5.png)


### Download our sample code
Find out more about the sample code [here](https://docs.aws.amazon.com/kinesisanalytics/latest/java/get-started-exercise.html).

Note: We have tweaked the examples slightly.

* In the terminal, paste and run the following code to setup the Flink 1.6.2 environment and to compile the Kinesis connector for Flink.

```
cd ~/environment
git clone https://github.com/dbayardAWS/kinesis-labs.git
```

![screenshot](images/clone1.png)

* In the navigator on the left-hand side, expand the kinesis-labs folder, then the src folder, all the way as shown in the below screensot until you get to the BasicStreamingJob.java file.  Double-click on the file to open it in a tab in the editor.

![screenshot](images/clone2.png)

## Create Kinesis Streams for input and output

```
# change DB to your initials before running this
export INITIALS=DB

```

![screenshot](images/stream1.png)

```
echo Initials=$INITIALS
aws kinesis create-stream \
--stream-name ExampleInputStream$INITIALS \
--shard-count 1 \
--region us-east-1

aws kinesis create-stream \
--stream-name ExampleOutputStream$INITIALS \
--shard-count 1 \
--region us-east-1

```

![screenshot](images/stream2.png)

### view the kinesis streams in the UI

### Create an s3 bucket to upload code

### Edit the getting started and compile it

### copy the jar file to the s3 bucket

### use the UI to define the KDAJ application

### run the python producer and consumer






## Before You Leave
If you are done with all of the labs, please follow the cleanup instructions to avoid having to pay for unused resources.

Note: you will use the datasets created in this lab in the next lab, so don't delete them until you are finished with all of the labs.
