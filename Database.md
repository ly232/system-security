# How to detach and attach database #

## detach database: ##
  * ommand format:
    * ysqldump -uUSERNAME -p DATABASENAME > DATABASENAME.sql：
  * xample:
    * ysqldump -uroot -p entnetdb\_v2 > /Users/crazymooner/Documents/workspace/System          Security/src/JDBC/createTable.sql

## attach database: ##
  * ommand format:
    * .open a schema named DATABASENAME(entnetdb\_v2)
    * .mysql -uUSERNAME -p DATABASENAME < DATABASENAME.sql
    * xample:
      * LINUX) mysql -uroot -p entnetdb\_v2 < /Users/crazymooner/Documents/workspace/System Security/src/JDBC/createTable.sql
      * WINDOWS) C:\Users\Lin>"C:\Program Files\MySQL\MySQL Server 5.5\bin\mysql.exe" --host=localhost --user=root --password=mysql entnetdb\_v2 < "C:\Users\Lin\Desktop\CS5430Security\Project\src\JDBC\createTable.sql"