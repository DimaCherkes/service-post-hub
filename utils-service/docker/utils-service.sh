#!/usr/bin/env bash

PROFILE=${PROFILE:-local-idea}

echo "Starting service with profile: $PROFILE"
exec java -jar /srv/utils-service-1.0-SNAPSHOT.jar --spring.profiles.active=$PROFILE
