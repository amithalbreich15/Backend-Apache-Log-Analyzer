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
│   |── test/                         # Test classes (if any)
│   |   └── java/
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
The program final output is:
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

## Dependencies
* MaxMind GeoIP2 - For IP to country resolution
* UserAgentUtils - For browser and OS detection
* Apache HttpComponents - For downloading the log file


## Final Results
The program will print results like:
```
Countries:
United States - 85.84%
Brazil - 2.85%
Kazakhstan - 1.44%
Indonesia - 0.92%
France - 0.79%
Japan - 0.72%
China - 0.72%
The Netherlands - 0.69%
Israel - 0.64%
United Kingdom - 0.59%
Colombia - 0.51%
Singapore - 0.48%
Peru - 0.45%
Germany - 0.34%
Ireland - 0.29%
Ecuador - 0.24%
Chile - 0.20%
Argentina - 0.17%
Poland - 0.16%
India - 0.15%
Bulgaria - 0.15%
Spain - 0.15%
Thailand - 0.14%
Cambodia - 0.14%
Italy - 0.13%
Canada - 0.13%
Mexico - 0.12%
Australia - 0.11%
Serbia - 0.10%
Norway - 0.06%
Finland - 0.05%
Palestine - 0.04%
Iraq - 0.04%
Malaysia - 0.04%
United Arab Emirates - 0.03%
Myanmar - 0.03%
Greece - 0.03%
Vietnam - 0.03%
Switzerland - 0.03%
Paraguay - 0.03%
Bolivia - 0.03%
Nigeria - 0.03%
South Africa - 0.02%
Taiwan - 0.02%
Egypt - 0.02%
Philippines - 0.02%
Unknown - 0.01%
Panama - 0.01%
Hong Kong - 0.01%
Ukraine - 0.01%
Honduras - 0.01%
Iran - 0.00%
T?rkiye - 0.00%
Iceland - 0.00%
Latvia - 0.00%
New Zealand - 0.00%
Russia - 0.00%
Kenya - 0.00%
Denmark - 0.00%

Operating systems:
Windows - 89.40%
Unknown - 3.52%
Mac OS X - 2.75%
iOS - 1.69%
Android - 1.08%
Linux - 1.00%
Sony Playstation - 0.13%
Unknown mobile - 0.12%
Chrome OS - 0.09%
macOS - 0.07%
SunOS - 0.05%
Android (Google TV) - 0.04%
Symbian OS - 0.02%
BlackBerryOS - 0.02%
Nintendo Wii - 0.02%
BlackBerry Tablet OS - 0.01%
Unknown tablet - 0.00%

Browsers:
IE - 79.39%
Firefox - 6.76%
Chrome - 4.63%
Safari - 4.26%
Bot - 1.49%
Unknown - 1.02%
Mozilla - 0.88%
Opera - 0.38%
Apple WebKit - 0.38%
Opera 10 - 0.27%
Opera 11 - 0.19%
Opera Mini - 0.15%
Opera 9 - 0.06%
Opera 12 - 0.06%
CFNetwork - 0.06%
NetFront - 0.02%
Camino 2 - 0.00%
Lynx - 0.00%
Flock - 0.00%
```
