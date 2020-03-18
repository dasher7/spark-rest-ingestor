# Spark Ingestor

This application is an ingestor that allow you to ingest tons of data coming from Kafka/Socket/Hadoop.
It is tirggerable via REST APIs, fully controllable.
The application average 2000-ish documents saved per second.

## Tech Stack and Dependecy

  - Java 8
  - Spring Boot 2.2.5
  - Maven
  - Swagger
  - Lombok 1.18.4
  - MongoDB 4.x
  - Spark 2.3.0
  - Hadoop 2.9.1
  
Note: this project use [Guava](https://github.com/google/guava) for parsing strings, you may encountr some problem with the version
of this dependecy. Currently, I am using 27.0.1, go back to 15.0 if you see some strange errors while compiling and building the project.
  
## Installation

In your console, simply:

``` git clone https://github.com/dasher7/spark-rest-ingestor.git ```

Import your code into your favourite IDE, I invite you to use Sping Tools Suite (STS 4).

Install [Lombok] (https://projectlombok.org/)

Be sure to have MongoDB installed on your computer, otherwise you can use the Atlas cloud version.
Be sure to have Hadoop (suggested version: 2.9.1) on your computer, up and running, if you'd like to test the Hadoop streaming.

## Usage

The project it's all triggerable via REST APIs.

### Socket / Kafka Streaming

You can use the following API:
  - *@GET /spark/activateProducer* : activate the generator source for your ingestion process
  - *@GET /spark/disableStreaming* : disable the genetator source
  - *@GET /spark/activateStream* : activate your Spark actor allowing you to save the data that are being generated into MongoDB
  - *@GET /spark/disableStreaming* : ends the ingestion process
  
### Hadoop

  You can use the following API:
    - *@GET /hadoop/activateFileStream* : activate the ingestion process via Hadoop
    
Note: in order for the Strems to work, there is the necessity to have the Producers on. So, first thing first, call *@GET /spark/activateProducer*.

## Useful links:

  - *Swagger*: http://localhost:8080/swagger-ui.html#/
  - *Hadoop Resource Manager : http://localhost:8088
  - *Hadoop Node Manager*  http://localhost:8042
  - *Hadoop Name Node : http://localhost:50070
  - *Hadoop Data Node* : http://localhost:50075
 
## Contribuiton and Usage

Feel free to use the project for your own like.
NOTE: The project is always a WIP, because I will always try new things and test new topic.

## License

[MIT](https://choosealicense.com/licenses/mit/)
