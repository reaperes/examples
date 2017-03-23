#!/bin/bash
docker run --rm -p 8080:80 nginx
# docker run --rm -p 80:80 -p 443:443 -v ./nginx.conf:/etc/nginx/nginx.conf nginx
# docker run --rm -p 80:80 -p 443:443 -v ./default.conf:/etc/nginx/conf.d/default.conf nginx