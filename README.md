# Brew Day!
Brew Day! is an application that allows home brewers to maintain an organized database of their
beer recipes. The application allows users to create, store and modify recipes, and later on delete
them, if the user wishes to do so. The application is intended for "all-grain" brewers only, and thus
all recipes are for this kind of brews (the "extract" brews are not supported).

Besides the actual recipes, the application maintains recipe instances, particular brews
based on a recipe; these instances can be accompanied by notes to refer to issues that may affect the
resulting beer and the brewers would like to keep logged. A particular kind of note is the tasting
notes, that allows brewers to keep track of opinions on a beer from a particular brew.

Besides these more traditional features of Brew Day!, the application maintains the list of instruments of which
the brewer equipment is composed and the list of available ingredients, this allows brewers to be notified about 
missing ingredients for the next brew. 

Brew Day! also support a useful feature for brewers: "what should I brew today?" suggests to the brewer the recipe 
that maximizes the use of the available ingredients, taking into account the equipment capacity.

## Getting Started

To get started with Brew Day! download the `.zip` file containing everything you need by clicking on
`Clone or download` and `Download ZIP` in this page. The next step is to extract the folder contained in the `.zip` file, 
then you're ready to import the project in your IDE.

Alternatively you can directly import the repository in your IDE by clicking on `Clone or download` and copying the given URL.

### Prerequisites

It's required to have Java (preferably the latest version) installed on your PC to install Brew Day!.

### Installing

To install Brew Day! download the `.zip` file containing everything you need by clicking on `Clone or download`
and `Download ZIP` in this page. The next step is to extract the folder contained in the `.zip` file. The final step is 
to run the `BrewDay!.exe` file contained in the `Brew Day!` folder if you're using Windows or the `BrewDay!.rar` file contained 
in the same folder if you're using macOS.

## Running the tests

To run all tests run the project as a maven build with the goals `test`. 

## Analyzing with SonarCloud

To analyze the project with SonarCloud run the project as a maven build with the goals `test sonar:sonar`.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=UnimibSoftEngCourse1920_progetto-birra-3-gruppo-birra-3&metric=alert_status)](https://sonarcloud.io/dashboard?id=UnimibSoftEngCourse1920_progetto-birra-3-gruppo-birra-3)

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Final version

The final version is the tag `release`.

## Authors

* **Cazzetta Davide** - [Davydhh](https://github.com/Davydhh)
* **Cusini Matteo** - [user-met](https://github.com/user-met)
* **Locatelli Federico** - [FedeLoca](https://github.com/FedeLoca)
