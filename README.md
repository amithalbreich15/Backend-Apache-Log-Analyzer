# Apache Log Analyzer
This Java application analyzes Apache web server logs to generate statistical reports about user behavior, including:
* Countries of origin (based on GeoIP)
* Operating systems used
* Browsers used

## Features
* Object-oriented design that's easy to extend with new metrics
* Uses MaxMind's GeoIP2 database for accurate country identification
* Uses UserAgentUtils for reliable browser and OS detection
* Outputs percentages with 2 decimal places precision
* Sorts results in descending order
* Proper exception handling and resource management
* Includes a LogFileDownloader utility to download log files automatically

## Prerequisites
* Java 11 or higher
* Maven
* MaxMind GeoLite2 Country database

## Project Structure
```
apache-log-analyzer/
├── pom.xml                           # Maven configuration
├── README.md                         # This file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── loganalyzer/      # Java source files
│   │   │           ├── LogAnalyzer.java
│   │   │           ├── LogProcessor.java
│   │   │           ├── MetricExtractor.java
│   │   │           ├── CountryExtractor.java
│   │   │           ├── LogEntryParser.java
│   │   │           ├── OperatingSystemExtractor.java
│   │   │           ├── BrowserExtractor.java
│   │   │           └── LogFileDownloader.java
│   │   └── resources/
│   │       ├── GeoLite2-Country.mmdb # GeoIP database
│   │       └── all.log               # Sample log file
│   └── test/                         # Test classes (if any)
│       └── java/
└── all_stats.txt                     # File that includes results
```

## Setup
1. Clone the repository or create from scratch
```bash
git clone <repository-url>
# OR create directories manually
```

2. Download GeoLite2 Country database
   1. Sign up for a free account at MaxMind
   2. Download the GeoLite2 Country database in MMDB format
   3. Place it in `src/main/resources/GeoLite2-Country.mmdb`

3. Log file
   - Option 1: Download manually
   ```bash
   curl -o src/main/resources/all.log "https://grnhse-use1-prod-s1-ghr.s3.amazonaws.com/generic_attachments/attachments/002/693/931/original/all.log?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVQGOLGY373LJL5PF%2F20250306%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250306T130529Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=7cd1b62143623b64595973d608855416f8711ca59d39c578c1bd8f96b620419e"
   ```
   - Option 2: Let the application download it
     - The LogFileDownloader class will automatically download the log file if it's not found in the resources directory

## Building and Running
Build with Maven
```bash
mvn clean package
```

Run the application
```bash
java -jar target/apache-log-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Sample Output
The program will print results like:
```
Countries:
US - 64.12%
Germany - 19.01%
Brazil - 12.50%
Israel - 5.00%

Operating systems:
Windows - 75.01%
Android - 13.99%
iOS - 11.01%

Browsers:
Firefox - 43.12%
Chrome - 32.34%
IE - 24.11%
```

## Extending the Application
To add a new metric, create a new class that implements the `MetricExtractor` interface and register it with the `LogProcessor`:

```java
public class NewMetricExtractor implements MetricExtractor {
    @Override
    public String getMetricName() {
        return "new-metric-name";
    }
    
    @Override
    public String extract(String logLine) {
        // Implementation to extract your metric
        return extractedValue;
    }
}

// Then register it:
logProcessor.registerExtractor(new NewMetricExtractor());
```

## Dependencies
* MaxMind GeoIP2 - For IP to country resolution
* UserAgentUtils - For browser and OS detection
* Apache HttpComponents - For downloading the log file
