#!/bin/bash

while true; do jstack TOMCAT_PID > $(date +%s); sleep 5; done;
