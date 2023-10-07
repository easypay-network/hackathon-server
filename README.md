# hackathon-server

> **Rest service written in java.**

## Docker

Docker build:

`docker build -t easypay-server:v1 .`

Docker run:

`docker run -p 8095:8095 --env NEO4J_URI=<uri> --env NEO4J_USERNAME=<username> --env NEO4J_PASSWORD=<pass> -it -d easypay-server:v1`
