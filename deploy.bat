@echo on
@echo =============================================================
@echo $                                                           $
@echo $                      pikaq				  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title pikaq deploy
@color 0a

rem  Please execute command in local directory.

call mvn clean deploy -DskipTests -P release

pause

pause