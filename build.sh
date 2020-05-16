#!/bin/bash
# Bundle all secret files together
tar cvf secrets.tar src/main/resources/application.yml
# Secret encryption for GitHub Actions
gpg --symmetric --cipher-algo AES256 secrets.tar