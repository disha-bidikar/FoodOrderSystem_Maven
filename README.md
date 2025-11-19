# Food Order Management System (Maven)
This archive contains two Maven projects:
1. `console-maven` — Java console application (uses JDBC + MySQL)
2. `web-maven` — Simple JSP/Servlet skeleton (WAR) demonstrating how to convert the same DAOs to a web app

Also included:
- `sql/food_order_schema.sql` — SQL schema + sample data
- `README.md` — this file
- `SETUP_MAC.md` — detailed Mac M2 + VS Code setup and run instructions

## Quick start (console)
1. Create the database:
   ```
   mysql -u root -p < sql/food_order_schema.sql
   ```
2. Edit database credentials if needed:
   - `console-maven/src/main/resources/db.properties`
3. Build and run:
   ```
   cd console-maven
   mvn package
   mvn exec:java -Dexec.mainClass="com.foodapp.App"
   ```

## Quick start (web)
1. Update DB credentials:
   - `web-maven/src/main/resources/db.properties`
2. Build WAR:
   ```
   cd web-maven
   mvn package
   ```
   The generated `web-maven/target/web-maven.war` can be deployed to Tomcat or run with `mvn tomcat7:run` (if plugin configured).

## Files of interest
- console-maven/src/main/java/... (Java source for console app)
- console-maven/pom.xml (Maven config for console app)
- web-maven/src/main/java/... (Servlet and web controllers)
- web-maven/src/main/webapp/ (JSP pages)
- sql/food_order_schema.sql
