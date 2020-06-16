@REM %~dp0% se ekspandira u putanju (direktorij) u kojem je ova trenutna skripta.
@REM Iz tog direktorija pozivamo skriptu setenv.bat kako bi postavila potrebne varijable okruzenja. 
@REM Ako niste podesili PATH tako da ima direktorij od Jave u njemu, donji poziv prefiksirajte punom stazom do java.exe, po potrebi pod navodnicima ako postoje razmaci.
call "%~dp0%setenv.bat"
java -Dderby.system.home=%DERBY_DATABASES% -jar %DERBY_INSTALL%\lib\derbyrun.jar server start
