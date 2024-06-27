#!/usr/bin/env bash

TOKEN=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 32)

echo "Generated token: $TOKEN"