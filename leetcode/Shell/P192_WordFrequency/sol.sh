#!/usr/bin/env bash

# Read from the file words.txt and output the word frequency list to stdout.
cat words.txt |tr -s ' ' '\n'|sort|uniq -c|sort -nrk2|awk '{print $2" "$1'}