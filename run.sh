#!/bin/bash

echo -ne "Check for openjdk package... "

PKG_OK=$(dpkg-query -W --showformat='${Status}\n' openjdk-8-jre | grep "install ok installed")

if [ "" == "$PKG_OK" ]; then
	echo "the package wasn't found in the system. Abort."
	exit 1
fi

echo "OK."

echo -ne "Check for maven package... "

PKG_OK=$(dpkg-query -W --showformat='${Status}\n' maven | grep "install ok installed")

if [ "" == "$PKG_OK" ]; then
	echo "the package wasn't found in the system. Abort."
	exit 1
fi

echo "OK."

echo -ne "Check for docker package... "

PKG_OK=$(dpkg-query -W --showformat='${Status}\n' docker | grep "install ok installed")

if [ "" == "$PKG_OK" ]; then
	echo "the package wasn't found in the system. Abort."
	exit 1
fi

echo "OK."

echo -ne "Check for docker-compose package... "

PKG_OK=$(dpkg-query -W --showformat='${Status}\n' docker-compose | grep "install ok installed")

if [ "" == "$PKG_OK" ]; then
	echo "the package wasn't found in the system. Abort."
	exit 1
fi

echo "OK."
echo "Build the project."

cd DetailService/
mvn clean package

cd ../

cd ProfileService/
mvn clean package

cd ../

docker-compose up --build -d
