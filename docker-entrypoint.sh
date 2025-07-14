#!/bin/sh
set -e

# You can add any pre-launch setup here

exec java -jar /usr/local/lib/app.jar "$@"