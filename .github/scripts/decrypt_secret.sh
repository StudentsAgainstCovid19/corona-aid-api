#!/bin/sh

# Decrypt the secrets file
gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE" --output secrets.tar secrets.tar.gpg
tar xvf secrets.tar