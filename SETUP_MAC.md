# Setup on macOS (Apple Silicon M2) + VS Code (Maven)
Follow these steps to set up your Mac and VS Code to run both the console and web projects.

## 1) Install Homebrew (if not already)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
Then:
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"

## 2) Install JDK 17
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
java -version

## 3) Install Maven
brew install maven
mvn -version

## 4) Install MySQL
brew install mysql
brew services start mysql
mysql_secure_installation
# create the DB using the SQL script included:
mysql -u root -p < /path/to/FoodOrderSystem_Maven/sql/food_order_schema.sql

## 5) Install VS Code
brew install --cask visual-studio-code

## 6) Recommended VS Code extensions
- Extension Pack for Java
- Language Support for Java(TM) by Red Hat
- Debugger for Java
- Maven for Java
- Tomcat for Java (optional, for web)

## 7) Open project
- Open VS Code: File -> Open... -> select the folder `FoodOrderSystem_Maven`
- The `Maven for Java` extension will detect the projects automatically.

## 8) Configure DB credentials
- Edit `console-maven/src/main/resources/db.properties`
- Edit `web-maven/src/main/resources/db.properties`

## 9) Build & run (console)
cd console-maven
mvn package
mvn exec:java -Dexec.mainClass="com.foodapp.App"

## 10) Build & run (web)
cd web-maven
mvn package
# deploy the generated WAR to Tomcat or use a plugin to run embedded Tomcat/Jetty

## Notes
- If MySQL is on a different host/port/user, set it in db.properties
- For Tomcat, install Tomcat separately or use the VS Code Tomcat extension
