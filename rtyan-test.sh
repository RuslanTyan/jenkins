#!/bin/bash

#trap '' 15

cat rtyan-test.result

for i in `seq 1 100`
do
  echo "$i\n"
  echo $i > rtyan-test.result
  sleep 1
done
echo "Success5" >> rtyan-test.result
