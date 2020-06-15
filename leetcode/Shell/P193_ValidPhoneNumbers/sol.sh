#!/usr/bin/env bash

# Read from the file file.txt and output all valid phone numbers to stdout.
grep -E "^(\([0-9]{3}\)\s|[0-9]{3}-)[0-9]{3}-[0-9]{4}$" file.txt