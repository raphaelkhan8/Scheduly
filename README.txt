# Scheduly

JavaFx w/MySQL scheduling app


## App Information

Author: Raphael Khan
Contact: rkhan38@wgu.edu
App Version: 1.0
Date: 03/07/2021
IDE: IntelliJ Community 2020.2 x64
JDK: Java SE 11.0.9
JavaFX: JavaFX-SDK-11.0.2
Database Driver: JDBC version 8.0.22
Supported Languages: English and French


## Navigation

A. Running the program brings up the Login page.
    a. Returning user: Enter login credentials and click Submit.
    b. New user: Click the Signup button to create an account.
    c. You will be signed-in after successful login or signup.

B. The Home Page view is displayed after signing-in.
    a. Three possible views to navigate to:
        1. Customers Table
            - All of the customers in the database are listed here.
            - Adding, Updating, or Deleting a customer can be done here (Add/Update/Delete buttons).
            - Adding an appointment to a customer can be done here (ADD APPOINTMENT button).
            - Cancel button returns view back to Home Page.
        2. Appointment Manager
            - All of the appointments in the database are listed here.
            - Updating or Deleting an appointment can be done here (Update/Delete buttons).
            - Two radio buttons are available to change the appointment view (by Week/by Month).
            - Clicking a radio button will also populate the View By combo box under the Appointment table.
            - Cancel button returns view back to Home Page.
        3. Report Generator
            - Three possible reports can be displayed here.
            - Select the report type from the combo box and click Generate Report button.
              The report will generate in its corresponding report box.
            - Cancel button returns view back to the Home Page.
    b. Adding/Updating Views
        1. Adding/Updating a Customer
            - All fields (including Combo Boxes) must be filled out or chosen.
            - Country/Division combo-boxes are pre-populated with data from the database.
            - A customer must be selected in order to navigate to Update Customer view.
            - Update view pre-populates fields with the selected customer's info.
            - Click Save to save the customer in the database.
            - Cancel button returns view back to the Customers Table page.
        2. Adding/Updating an Appointment
            - All fields (including Combo Boxes) must be filled out or chosen.
            - An appointments table shows all of the already scheduled appointments.
            - Appointment Type/Contact Type combo-boxes are pre-populated with data from the database.
            - A customer must be selected in order to navigate to Add Appointment view.
            - An appointment must be selected in order to navigate to Update Appointment view.
            - Update view pre-populates fields with the selected Appointment's info.
            - Click Save to save the Appointment in the database.
            - Cancel button (Add Appointment) returns view back to the Customers Table page.
            - Cancel button (Update Appointment) returns view back to the Appointment Manager page.

C. Logout button returns view back to Login page.


## Custom Report Description

- Report 3 generates a list of customers ranked by the number of associated appointments they have. The report includes
  their Id, Name, and number of appointments. This information can be used to find who regular customers are.

