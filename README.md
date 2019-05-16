# eBamazon

This is Team 11's final project for CCNY Software Engineering
- Jacob Luceno - Marissa Almoslino - Brian Thibeault - Jay Saduakas - 

All the source code is contained in the SRC folder.  It was built in IntelliJ, and requires JavaFX.

Before the code can be built and run however the database must be set up and configured.

We used MariaDB.  There is no external configuration file for the database login and password, but it is
specified in the DBConnection class in the Data Access package. The connection details can be edited in
this class, but by default assumes the user is called "root", and the password is "password".

Once you've configured the local database to your liking, the Repo file "tableCreation.sql" should be run
via the mysql command "source *absolute path to the tableCreation file*".

A super user is automatically inserted, with username "super" and password "super".

All state tax rates are also automatically inserted.

That's all there is to it!

-Team 11
