@echo off
setlocal enabledelayedexpansion
set total=0
for /r %%f in (*.kt *.py *.xml *.kts *.toml *.gradle *.properties) do (
  for /f %%c in ('type "%%f" ^| find /c /v ""') do set /a total+=%%c
)
echo Total lines: %total%
