# C195Project: Java Scheduling Application
## Purpose
A scheduling program which interacts with a database to add, edit, and delete customers, to add, edit, and delete appointments, and to provide reports on the data in the database. 

The program first launches with a login form which validates users through the database. Once validated, the program informs the user of any appointments within 15 minutes of login time and then launches the main view which contains three tabs: Customer, Appointment, and Reports. 

The Customer tab contains a table view which lists all the customers and a form to edit existing customers and add new customers. 

The Appointment tab contains a table view which by default lists all the appointments in the current month, a form to edit existing appointments and add new appointments, and a toggle radio button which changes the table view to only display appointments in the current week. 

The Report tab contains a combo box which allows the user to choose reports. Choosing the report loads it onto the tab. If information has been changed, the user can click on the "Refresh" button to update the report.
### Additional Report
The additional report is called the Contact Hours Report. The report lists the number of hours each contact has scheduled for appointments for each month.
## Version
V1 2/19/2022
## Built With
* **IDE:** IntelliJ IDEA 2021.3.2 (Community Edition)
* **JDK:** Java SE 17.0.2
* **JavaFX version:** JavaFX-SDK-17.0.1
* **MySQL Connector Driver:** mysql-connector-java-8.0.25
## How to Run
###First Steps:
Launch the program and enter valid credentials (username/password combination from the database).

###Main View:
The main view contains three tabs: Customer, Appointment, and Report. Click the tab to access its information. The default tab is the Customer tab.

###Customer Tab:
To add a customer, fill in all fields and choose from all dropdowns in the Customer Form and click Save. Make sure the ID is empty to add a new customer. If it is not empty, click Clear.

To edit a customer, select a customer from the table view and edit the information in the Customer Form. The Customer ID cannot be edited.

To delete a customer, select a customer from the table view and click Delete.

###Appointment Tab:
To toggle views, click on the appropriate radio button. "This Month" shows all appointments for this month. "This Week" shows all appointments for this week.

To add an appointment, fill in all fields and choose from all dropdowns in the Appointment Form and click Save. A date must chosen on the date picker from a start time is chosen. Make sure the ID is empty to add a new customer. If it is not empty, click Clear.

To edit an appointment, select an appointment from the table view and edit the information in the Appointment Form. A date must chosen on the date picker from a start time is chosen. The Appointment ID cannot be edited.

To delete a customer, select an appointment from the table view and click Delete.
###Report:
Choose a report on the dropdown to load the report. If appointment information has been changed, click on the Refresh button to reload the report. 

## Author
**Name:** Jazz Elgarico

**Student e-mail:** jelgari@wgu.edu

**Personal e-mail:** jazz.elgarico@gmail.com
