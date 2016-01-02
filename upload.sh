#!/bin/bash

FILES=*
for f in $FILES
do
  echo "Uploading $f ..."
  curl -X POST "http://localhost:8080/api/images/upload" -F "file=@$f" -F "meta={};type=application/json"
done