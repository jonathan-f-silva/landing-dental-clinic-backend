#!/bin/sh

echo "Generating RSA keys"

openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out app.pub
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out app.key
rm keypair.pem

echo "Done"
