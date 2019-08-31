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
--shard-count 1 

aws kinesis create-stream \
--stream-name ExampleOutputStream$INITIALS \
--shard-count 1 

```

![screenshot](images/stream2.png)


### Edit and launch the stocks.py Provider

* In the Cloud9 navigator on the left-hand side, expand the kinesis-labs folder, then the src folder.  Then double-click on the stock.py file to open it in a tab in the editor.

![screenshot](images/stock1.png)

* Find the StreamName parameter in the stock.py code and append your initials to the Stream name (to match the name of the kinesis stream you created with the "aws kinesis create-stream" command above).

![screenshot](images/stock2.png)

* Then use the File..Save menu to save the modified stock.py file

![screenshot](images/stock3.png)

* Use the + icon to open up a new terminal tab

![screenshot](images/stock4.png)

* In the new terminal tab, run these commands:

```
cd kinesis-labs/src
python stock.py

```

![screenshot](images/stock5.png)


### Edit and launch the readOutputStream Consumer

* In the Cloud9 navigator on the left-hand side, expand the kinesis-labs folder, then the src folder.  Then double-click on the readOutputStream.py file to open it in a tab in the editor.

![screenshot](images/read1.png)

* Find the my_stream_name parameter in the readOutputStream.py code and append your initials to the Stream name (to match the name of the kinesis input stream you created with the "aws kinesis create-stream" command above).

Note: at this point, the my_stream_name should be set to the ExampleInputStream[Initials].  In a later exercise, we will point this consumer to the ExampleOutputStream but not yet.

![screenshot](images/read2.png)

* Then use the File..Save menu to save the modified readOutputStream.py file

![screenshot](images/read3.png)

* Use the + icon to open up a new terminal tab

![screenshot](images/read4.png)

* In the new terminal tab, run these commands:

```
cd kinesis-labs/src
python readOutputStream.py

```

![screenshot](images/read5.png)

You should see the messages from the Kinesis stream printed out to the console.

* In the terminal tab running the readOutputStream.py, type ctrl-c to stop it.

![screenshot](images/read5.png)


### Edit the Getting Started KDAJ application and compile it

* In the navigator on the left-hand side, expand the kinesis-labs folder, then the src folder, all the way as shown in the below screensot until you get to the BasicStreamingJob.java file.  Double-click on the file to open it in a tab in the editor.

![screenshot](images/started1.png)

* Find the inputStreamName and outputStreamName parameters in the java code and append your initials to the Stream name (to match the name of the kinesis streams you created with the "aws kinesis create-stream" command above).

Note: Be sure to edit both the inputStreamName and outputStreamName

![screenshot](images/started2.png)

* Then use the File..Save menu to save the modified BasicStreamingJob.java file

![screenshot](images/started3.png)

* In your left-most Terminal tab (or open a new Terminal tab if you wish), run these commands to compile and build your KDAJ application:

```
cd ~/environment/kinesis-labs/src/amazon-kinesis-data-analytics-java-examples/GettingStarted/
mvn install:install-file -Dfile=/home/ec2-user/environment/flink-release-1.6.2/flink-connectors/flink-connector-kinesis/target/flink-connector-kinesis_2.11-1.6.2.jar -DpomFile=/home/ec2-user/environment/flink-release-1.6.2/flink-connectors/flink-connector-kinesis/target/dependency-reduced-pom.xml
mvn package

```

![screenshot](images/started4.png)

### Create an s3 bucket to upload code

* Using the same Terminal tab you just used to compile/build your KDAJ application, run these 2 sets commands to create a new S3 bucket.  Be sure to redefine the lowercaseusername to your username:

```
# change "dbayard" to your username (in lowercase, no spaces) before running this
export lowercaseusername=dbayard

```

```
echo lowercaseusername=$lowercaseusername
aws s3 mb s3://lab-kdaj-app-code-$lowercaseusername

```

![screenshot](images/s31.png)

### copy the jar file to the s3 bucket

* Using the same Terminal tab, run these commands to copy the generated .jar file your KDAJ application to the s3 bucket:

```
echo lowercaseusername=$lowercaseusername
cd ~/environment/kinesis-labs/src/amazon-kinesis-data-analytics-java-examples/GettingStarted/target/
aws s3 rm s3://lab-kdaj-app-code-$lowercaseusername/java-getting-started-1.0.jar
aws s3 cp aws-kinesis-analytics-java-apps-1.0.jar s3://lab-kdaj-app-code-$lowercaseusername/java-getting-started-1.0.jar

```

![screenshot](images/s32.png)

### use the UI to define the KDAJ application

* In the AWS Console, use the Services menu and navigate to the Kinesis console.  One way to do so, is to expand the Services top menu and type "Kinesis" in the service search field.

![screenshot](images/kin1.png)

* In the left-hand column, click on "Data Analytics"

* Then click on "Create application"

![screenshot](images/kin2.png)

* On the Create Application page, enter "kdaj_app_INTITALS" for the application name, where your replace INITIALS with your initials.

* Enter a description of your liking

* Choose Apache Flink 1.6 for the Runtime

* Leave the default of "Create / update IAM..." for the Access permisions

* Click Create application

![screenshot](images/kin3.png)

* Now the application is created but it is not yet configured.  Click Configure

![screenshot](images/kin4.png)

* On the Configure application page, pick the S3 bucket you created earlier.  It will be named something like lab-kdaj-app-code-[lowercaseusername].

* For the S3 object path, enter this value:

```
java-getting-started-1.0.jar
```

* Expand Snapshots and disable them

* Expand Monitoring and enable Cloudwatch Logging

* Click the Update button


![screenshot](images/kin5.png)

* Now run the application by clicking the Run button and when it asks if you are sure, click the Run button again

![screenshot](images/kin6.png)

At this point, your Flink application is starting.  It may take a few minutes to start.

* Wait until the Application Status says running

![screenshot](images/kin7.png)

### run the python producer and consumer






## Before You Leave
If you are done with all of the labs, please follow the cleanup instructions to avoid having to pay for unused resources.

Note: you will use the datasets created in this lab in the next lab, so don't delete them until you are finished with all of the labs.
