# Overview
The ingredient-finder application returns a list of products that contain a given ingredient.

# How To Run
1. cd .../ingredient-finder/target
1. java -jar ingredient-finder-1.0-SNAPSHOT-jar-with-dependencies.jar [fully qualified folder where input .json files are stored] [ingredient you're interested in]
1. Example: java -jar ingredient-finder-1.0-SNAPSHOT-jar-with-dependencies.jar /Users/williamlee815/Documents/repos/ingredient-finder/src/main/resources/ "Organic Banana"

# How To Build
1. Download and install Java 8
1. Download and install maven
1. cd .../ingredient-finder
1. mvn clean install assembly:single
