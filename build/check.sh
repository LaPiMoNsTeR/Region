#!/bin/bash

ERROR=0

PLG_YML_PATH="src/main/resources/plugin.yml"

#--------------------------------------------------------------


PLG_EXIST=1

# Check if plugin.yml is created.

echo "Checking plugin.yml .."
if [ ! -f "$PLG_YML_PATH" ];
then
    echo "'plugin.yml' does not exist.";
    PLG_EXIST=0
    ERROR=1
fi


# Declare array

declare -A PLG_DATA

PLG_DATA[name]=0
PLG_DATA[author]=0
PLG_DATA[version]=0
PLG_DATA[main]=0


#--------------------------------------------------------------


# If plugin.yml isn't created, cancelled attribute check.

if [ $PLG_EXIST == 1 ];
then

    # Check plugin.yml attribute one by one and store the value
    # in an Array.

    echo "Checking plugin.yml content .."
    while IFS= read -r line
    do
        ATTR="$(cut -d':' -f1 <<<"$line")"
        PLG_DATA["$ATTR"]=1
    done < "$PLG_YML_PATH"


    # Check what attribute is missing.

    for i in "${!PLG_DATA[@]}"
    do
        if [ ${PLG_DATA[$i]} == 0 ];
        then
            echo "Attribute '$i' not found in plugin.yml"
            ERROR=1
        fi
    done
fi


#--------------------------------------------------------------


if [ $ERROR == 1 ];
then
    echo "plugin.yml check operation: failed"
    exit 1
else
    echo "plugin.yml check operation: succeeded"
    exit 0
fi
