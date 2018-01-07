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

A fat JAR will be created in the directory: _/excel-to-pdf/build/libs/excel-to-pdf-1.0-SNAPSHOT-all.jar_

The JAR can then be executed by double-clicking or running in the command-line
```
$ java -jar excel-to-pdf-1.0-SNAPSHOT-all.jar
```

### Application Configuration

The application relies on a configuration file which maps columns from an Excel document to  
properties for writing the text onto the PDF form. 

Generating one can be done through the menu through: **File > Create configuration file...**


The user will be prompted at application launch if a configuration file is not detected.  The expected directory for 
configuration file is in the same directory as the application .JAR file.

### Format of Configuration File
The configuration file can be created using any text editor but needs to be saved with the title **user.conf**. 
The file must be placed in the same directory as the executable .JAR file.  

There are different types of configurations that are saved as a row record.

* **Print** - This is a mapping of a text string that writes to a specific field. 

```
|||print|||20|||111217|||450|||595|||
``` 

* **Lookup** - This is a similar mapping of a text string but instead will do a lookup within the Excel document 
provided.  The lookup is between the column given in this configuration and with the name given in the application.

```
|||excelLookup|||20|||name|||210|||540|||
``` 

* **Checkbox** - This maps locations for checking a checkbox on the PDF form based on the option saved in the 
Excel form. 

```
|||checkbox|||20|||newsletter signup|||yes|||431|||455|||no|||495|||455|||
``` 

Here is an example of the contents of a configuration file (sample provided in _/excel-to-pdf/user.conf_):
```
|||print|||20|||111217|||450|||595|||
|||excelLookup|||20|||name|||210|||540|||
|||excelLookup|||16|||id number|||210|||510|||
|||excelLookup|||16|||email address|||210|||490|||
|||checkbox|||20|||newsletter signup|||yes|||431|||455|||no|||495|||455|||
|||excelLookup|||20|||birthday|||145|||405|||
|||excelLookup|||20|||anniversary|||395|||405|||

```  

## Sample Documents

Documents in this project are provided as a sample.

* **Configuration file** _/excel-to-pdf/user.conf_ - Must be placed in the same directory as the application .JAR
* **Excel document** _/excel-to-pdf/src/test/resources/Personnel.xlsx_
* **PDF document** _/excel-to-pdf/src/test/resources/SignUpForm.xlsx_

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
