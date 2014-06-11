Live365-Search-RadioStations 
============================
Description: Extract music genre titles from tech cloud, use them to search and verify genres top radio stations

1. Open a terminal window/command prompt
2. Clone this project.
3. CD into project directory
4. mvn clean install 
5. mvn verify -Pselenium-tests      (to run test on firefox)
6. mvn verify -Pselenium-tests -Dbrowser=chrome     (to run test on chrome)
7. You can specify which browser to use by using one of the following switches:

- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=ie
- -Dbrowser=opera
- -Dbrowser=htmlunit
- -Dbrowser=ghostdriver