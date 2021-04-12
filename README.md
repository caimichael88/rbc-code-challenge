# rbc coding challenge

## TODO
```
1. Set up local and production profile
2. Add more validation for the stock fields.
3. Move project from Maven to Gradle
4. Create an Angular Front end 
```

### Prerequisites

You need:

```
* Maven
* Java 1.8 > (jdk)
* Docker
```

### Installing

```
Please make sure you have docker installed 
rununing the local start resources will install the mysql database as well as the table.
```
 
## Running the application

1. Start application

    first start the local docker containers;
    ```
    cd local
    ./start_resources.sh
    
    remove the container mysql database
    ./stop_resources.sh
    ```
    Keep the containers running, run the spring boot application
  

2. Check if the end-point is responding
    
    Use Postman or any tools to test the endpoint
    
    **Upload CSV file**
    ```
    The sample csv file is located inside the local/init/stocks.csv
    Ensure you run the local start resource script first and check the table is created.
   
    http://localhost:8081/api/rbc/upload
    ```
    **insert single stock**:
    ```
    Test JSON is under test/resources/newStock.json
   
    http://localhost:8081/api/rbc/insert
    ```
    
    **Get Stocks based on stock name**:
    ```
    http://localhost:8081/api/rbc/get/AA
    ```
    *Get Stocks based on stock name**:
    ```
    http://localhost:8081/api/rbc/stocks
    ```


### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With


## Contributing


## Versioning


## Authors

* **Michael Cai** - _Initial work_


## License


## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
