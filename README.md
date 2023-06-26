## Authors
* Yoni Bakshi: yehonatanba@edu.hac.ac.il:
* Dvir Avraham: dviravr@edu.hac.ac.il:

## Description

                Welcome to FinTrack
                        Expense
The Expense page allows you to add and manage your expenses.
You can track your spending by adding new expenses and categorizing them for easy reference.
Additionally, you can view your expense history on the "My Expense" page.

                        Budget
The Budget page enables you to set and monitor your budget goals.
You can add new budgets and allocate funds to different categories.
Keep track of your spending against your budget and make informed financial decisions.
The "My Budget" page provides an overview of your budget details.

                        Graphs
FinTrack offers two types of graphs for visualizing your financial data. 
These graphs provide insightful representations of your expenses, 
allowing you to analyze trends and make informed financial decisions.

                       Admin Page
The Admin page is designed for administrative users. 
It provides additional functionality for managing categories and user accounts.
- Category:   Add, remove, and edit categories to organize your expenses effectively.
- Users:      Access a list of all users and their respective expense and budget pages. 
              This feature allows administrators to monitor and manage user activities.

### General information

This project is a web application for managing personal finances.
It allows users to track their expenses and set budgets for different categories.
The application is built using Spring Boot and MySQL.
The front-end is built using HTML(thymeleaf), CSS, and JavaScript.

# How to run the project
to run the project you need to run the sql server and create a database named "ex5".
the DB credentials are stored in the application.properties file.
you may change them if you want.
then you need to run the project, you should not see any errors in IntelliJ console.
then open your browser and go to http://localhost:8080/ (or whatever port you set in application.properties)

## Installation

In order to initialize the project make sure to:

1. When you open the project, if intelliJ propose to "Load Maven Project" do it. You can later reload maven with the "M" icon on the right of the screen, or by right clicking on the pom.xml file and selecting "Maven -> Reload project".
2. You see red lines in the code? Go to File -> Project Structure -> Project Settings -> Project -> SDK -> and choose your Java SDK
3. Still see red stuff? Open the same dialog and click on "Fix" if you see some
4. Edit your configuration "ex5" at the top right. Make sure the "Main class" is set to "hac.DemoApplication" and that Java is set

Everything ok?
1. Run the SQL server and create a database named "ex5". The DB credentials are stored in the application.properties file. You may change them if you want.
2. Run the project, you should not see any errors in IntelliJ console
3. Open your browser and go to http://localhost:8080/ (or whatever port you set in application.properties)

