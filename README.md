# smartsupply-v2

[![License](https://img.shields.io/badge/License-Apache2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Slack](https://img.shields.io/badge/Join-Slack-blue)](https://callforcode.org/slack) [![Website](https://img.shields.io/badge/View-Website-blue)](https://github.com/benspylee/smartsupply-v2.git)

This repository contains two modules one is android application client and other is rest api server back end.
Our Theme for callforcode is local community communication/co-operation.we build an app that mediates between local vendors and customers within  that area and deliver the essentials 
safely with local delivery agents which encourages social distancing , tracking and isolation of people within this supply chain.Contactless delivery and delay in order can be avoided by preferring local vendors are few advantages of this system.


## Contents

1. [Short description](#short-description)
1. [Demo video](#demo-video)
1. [The architecture](#the-architecture)
1. [Long description](#long-description)
1. [Project roadmap](#project-roadmap)
1. [Getting started](#getting-started)
1. [Running the tests](#running-the-tests)
1. [Live demo](#live-demo)
1. [Built with](#built-with)
1. [Contributing](#contributing)
1. [Versioning](#versioning)
1. [Authors](#authors)
1. [License](#license)
1. [Acknowledgments](#acknowledgments)

## Short description

### What's the problem?
When pandemic outbreaks it shows how we are as community not prepared to tackle this situation.In country with large population centre point of spread is happened
when buying essentials(groceries and vegtables) where people not following social distancing which makes the situation worse.

### How can technology help?

we are trying to build the bridge that fill the gap between local vendors ,customer and delivery agents by building an app. 

### The idea

we build an app that connects local vendors who need to sell their items and customers who are in need of essential items with safety and social distancing as main goal. we are engaging to build this solution quickly with IBM cloud based service like IBM cos,functions.so..on.

## Demo video

[![Watch the video](https://github.com/benspylee/smartsupply-v2/blob/master/docs/hqdefault.jpg)](https://youtu.be/4YYp69qVs3U)

## The architecture

![Smartsupply app](https://github.com/benspylee/smartsupply-v2/blob/master/docs/architecture.png)

1. This app supports three different user roles Retail vendor,Customer and Delivery agent.Users register with app, After verification user can login and use.
2. We build an android client that communicates with rest-api which deployed in IBM cloud foundry and we save user generated transaction data in IBM Db2.
3. Customers orders items by browsing shop registered with app, we send notification to Customer and Delivery Agents with respect to order flow
4. Order receive to local retail vendor, he goes through it and he may accept or reject order.
5. Once retail vendor good with order he will prepare the order and assign it to local delivery agent.
6. delivery agent receives the job and he may accept the delivery or reject it.If he accept it he began to pick up the order and deliver it to customer and get the delivery charge.
4. This app stores user generated invoices,reports and images on IBM Object Storage.

## Long description

[More detail is available here](DESCRIPTION.md)

## Project roadmap

![Roadmap](https://github.com/benspylee/smartsupply-v2/blob/master/docs/roadmap.png)

## Getting started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Things you need to install the software and how to install them

```bash
sudo apt-get install wget
wget https://redirector.gvt1.com/edgedl/android/studio/ide-zips/4.0.1.0/android-studio-ide-193.6626763-linux.tar.gz
tar -zxvf android-studio-ide-193.6626763-linux.tar.gz
cd android-studio-ide-193.6626763-linux/bin
./studio.sh
```

[Download and install Db2 Communitiy Edition](https://www.ibm.com/in-en/products/db2-database/developers)

[Download and install IntelliJ Communitiy Edition](https://www.jetbrains.com/idea/download/)

### Installing

Steps to setup android client in local system

clone the repository and import as extisting android project

```bash
git clone https://github.com/benspylee/smartsupply-v2.git
cd smartsupply-v2/frontend
```
[import android project](https://developer.android.com/studio/intro/migrate) 

Steps to setup backend(rest-api) in local system

switch to scripts folder 
```bash
cd smartsupply-v2/docs/sql-scripts
```
execute the ddl scripts 

```bash
cd smartsupply-v2/backend
```

[import backend springboot project](https://spring.io/guides/gs/intellij-idea/)

Configure db2 server details and basic authorization details in application.properties and run 'SmartsupplyApplication.java' as Java Application 

![validate server status](https://github.com/benspylee/smartsupply-v2/blob/master/docs/server-logs.png)

## Running the tests

Basic health check 
```bash
curl -X GET http://localhost:8080/items -H 'authorization: Basic YWRtaWU6ghh' -H 'cache-control: no-cache' -H 'content-type: application/json'
```
If this gives respone code 200 then application is up running with good health 

## Live demo

You can find a running system to test at [callforcode.mybluemix.net](http://callforcode.mybluemix.net/)

## Built with

* [IBM Cloud Foundry](https://www.cloudfoundry.org/the-foundry/ibm-cloud-foundry/)  - SpringBoot based rest-api deployed in cloud foundry
* [IBM Cloud Functions](https://cloud.ibm.com/catalog?search=cloud%20functions#search_results) - Nodejs function exposed as servless rest-api to send push notification message
* [IBM Cloud Object Storage](https://www.ibm.com/cloud/object-storage) - Used to store reports,invoice and user docs securely.
* [IBM Push Notifications](https://www.ibm.com/cloud/push-notifications) - Deliver push notification to user end
* [Gradle](https://gradle.org/install/) - Dependency management
* [Android Studio](https://developer.android.com/studio/releases/sdk-tools) - Used to develop,build the android client

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [Github](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/benspylee/smartsupply-v2.git).

## Authors

* **Barath Ramalingam** - *Initial work* - [SmartSupply](https://github.com/Benspylee)
* **Maheshwari Pandy** -*Document support*

See also the list of [contributors](https://github.com/benspylee/smartsupply-v2.git) who participated in this project.

## License

This project is licensed under the Apache 2 License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Based on [Billie Thompson's README template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2).
