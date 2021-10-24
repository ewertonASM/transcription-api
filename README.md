# Video Transcription API

Video Transcription API is a video transcription and translation service..


## Table of Contents

- **[Getting Started](#getting-started)**
  - [System Requirements](#system-requirements)
  - [Prerequisites](#prerequisites)
  - [Installing](#installing)
- **[Documentation](#documentation)**
- **[Contributors](#contributors)**

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### System Requirements

* OS: Ubuntu 18.04.3 LTS (Bionic Beaver)
* OS: Windows WSL

### Prerequisites

Before starting the installation, you need to install some prerequisites.

##### [Java](https://www.java.com/pt-BR/)

Install Java.

```sh
sudo apt install -y defalt-jre
```

##### [MySQL](https://www.mysql.com/)

Update local package database.

```sh
sudo apt update
```
Install the MongoDB packages.

```sh
sudo apt install mysql-server
```

##### [RabbitMQ](https://www.rabbitmq.com/)

Update package indices.

```sh
sudo apt update
```

Install prerequisites.

```sh
sudo apt install -y curl gnupg apt-transport-https
```

Install RabbitMQ signing key.

```sh
curl -fsSL https://github.com/rabbitmq/signing-keys/releases/download/2.0/rabbitmq-release-signing-key.asc | sudo apt-key add -
```

Add Bintray repositories that provision latest RabbitMQ and Erlang 21.x releases.

```sh
echo "deb https://dl.bintray.com/rabbitmq-erlang/debian bionic erlang-21.x" | tee /etc/apt/sources.list.d/bintray.rabbitmq.list
```

```sh
echo "deb https://dl.bintray.com/rabbitmq/debian bionic main" | tee -a /etc/apt/sources.list.d/bintray.rabbitmq.list
```

Update package indices.

```sh
sudo apt update
```

Install rabbitmq-server and its dependencies.

```sh
sudo apt install rabbitmq-server -y --fix-missing
```

### Installing

To test the installation, build and start the transcription API with the following command:

```sh
./mvnw spring-boot:run
```


## Documentation

To access the documentation and usage examples of the Video Transcription API, start the translation server in your localhost and open a browser with the following link:

[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## Contributors

* Ewerton Moura - <ewerton.asmoura@gmail.com>
