Our project can be found in its entirety at https://github.com/acf62/Interaction-Design-25


Requirements
Windows10 (should run on other systems, but the javafx-sdk in the Library folder would need to be replaced with the appropriate version from Gluon https://gluonhq.com/products/javafx/)
1365x965 pixels of screen space (you may need to ensure that your screen is not zoomed in by right clicking on the desktop and accessing display settings)
java jdk installed with the Java bin in the path variable

To run: (These instructions are for windows)

Java version >=9 (we recommend 11 or 12)
Open a command prompt and navigate to the root of the project directory (same folder this file is in)
Run the following command:
Java --module-path .\Library\javafx-sdk-11.0.2\lib\ --add-modules javafx.controls,javafx.fxml -jar .\Interaction_Design_25.jar

Java version 8 (not recommended)
Open a command prompt and navigate to the root of the project directory (same folder this file is in)
Run the following command:
Java -jar .\Interaction_Design_25.jar