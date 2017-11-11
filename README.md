# Excel to PDF

This is a Java application that generates a filled out PDF document by associating an Excel document to read 
information from.  It is most beneficial when a large numnber of PDF forms need to be generated from a 
long list of names in an Excel document.

## Getting Started

This set of instructions will build a JAR which then can be executed for running the application.  The application
then will use a given Excel document to read from, a given PDF document to write onto, and then use user input for 
combining all of this into a filled-out PDF form.

### Deployment

From the command-line navigate to the project's root directory.  Create a clean build.

```
$ ./gradlew clean shadowJar
```

A fat JAR will be created in the directory: _/print-personal-info/build/libs/print-personal-info-1.0-SNAPSHOT-all.jar_

The JAR can then be executed by double-clicking or running in the command-line
```
$ java -jar print-personal-info-1.0-SNAPSHOT-all.jar
```

### Application Configuration

The application relies on a configuration file which maps columns from an Excel document to  
properties for placing the text onto the PDF form. 

Currently, this file needs to be manually created until further progress will have the user enter 
the information and have the application generate this file.  

### Format
The configuration file can be created using any text editor but needs to be saved with the title **user.conf**. 
The file must be placed in the same directory as the executable .JAR file.  Each row inside of the configuration file 
is a text field to be entered into the PDF document.

```
|||excel_header_name|||x-coordinate|||y-coordinate|||font_size|||
``` 

Here is the format from the sample document (provided in _/print-personal-info/user.conf_)
```
|||name|||210|||543|||20|||
|||id number|||210|||510|||20|||
|||birthday|||145|||468|||20|||
|||anniversary|||395|||468|||20|||

```  

## Sample Documents

Documents in this project are provided as a sample.

* **Configuration file** _/print-personal-info/user.conf_ - Must be placed in the same directory as the application .JAR
* **Excel document** _/print-personal-info/src/test/resources/Personnel.xlsx_
* **PDF document** _/print-personal-info/src/test/resources/SignUpForm.xlsx_

Provide all of these documents to the application.  If successful, a generated PDF file will be made.
Give it a try!

## Built With

* [JDK 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Gradle](https://gradle.org/) - Build tool
* [Apache POI](https://poi.apache.org/) - Java API for Microsoft documents
* [iText](https://itextpdf.com/) - Toolkit for integration PDF functionalities

## Authors

* **Jones Sagabaen** - [GitHub](https://github.com/JonesSagabaen)

Version 1.0 completed on November 10, 2017.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
