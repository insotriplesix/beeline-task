# beeline-task

![Beeline-CI](https://github.com/5aboteur/beeline-task/workflows/Beeline-CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=5aboteur_beeline-task&metric=alert_status)](https://sonarcloud.io/dashboard?id=5aboteur_beeline-task)
[![codecov](https://codecov.io/gh/5aboteur/beeline-task/branch/master/graph/badge.svg)](https://codecov.io/gh/5aboteur/beeline-task)



## Linux

To build the project and run docker containers change the access permissions for `run.sh` script and run it:

```bash
~$ chmod a+x run.sh
~$ ./run.sh
```

Your system must contain several packages which are listed below. The script automatically checks for their presence and aborts if at least one of them has not been installed.

Packages: `openjdk-8-jre, maven, docker, docker-compose`

If the script succeeds, you will see three new containers running in your system:

```bash
~$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                    PORTS                    NAMES
7ad7c4583b1f        detail-service      "java -jar /app.jar"     About an hour ago   Up About an hour          0.0.0.0:8841->8080/tcp   beeline-detail-serv
ce0f186b7a95        profile-service     "java -jar /app.jar"     About an hour ago   Up About an hour          0.0.0.0:8941->8080/tcp   beeline-profile-serv
e4b92fcc7963        postgres:latest     "docker-entrypoint.s…"   About an hour ago   Up About an hour          0.0.0.0:5432->5432/tcp   beeline-postgres-db
```

Now you can check that it's working by sending the `GET` request to a service API. First, check the IP address of the `detail-service` container:

```bash
~$ docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' beeline-detail-serv
172.18.0.4 <---
```

Finally, you can use the `curl` command (or your favourite browser) and paste this URL `http://172.18.0.4:8841/api/v1/detailservice/getCallerProfileByCellId?cellId=11111` to test the service. The response should look similar to this one:

```json
[
   {
      "ctn":"1234567890",
      "callerId":"03e17537-30de-4598-a816-108945fa68b4",
      "name":"Freeman Stephen",
      "email":"stephen.freeman@example.com"
    },
    {
        "ctn":"1234567891",
        "callerId":"3ad35c07-12ff-42de-a36d-e4cc21e9c500",
        "name":"Silva Rivelino",
        "email":"rivelino.silva@example.com"
    },
    {
        "ctn":"1234567894",
        "callerId":"e95cf4fd-a206-4152-b062-bac14262cf5e",
        "name":"Dehli Gabrielle",
        "email":"gabrielle.dehli@example.com"
    },
    {
        "ctn":"1234567898",
        "callerId":"6ea48872-53ed-4590-8a35-5d2af49f8211",
        "name":"Jackson Phoebe",
        "email":"phoebe.jackson@example.com"
    },
    {
        "ctn":"1234567892",
        "callerId":"37ebfda5-acc4-44f9-9060-37bd023bd426",
        "name":"Murray Brent",
        "email":"brent.murray@example.com"
    },
    {
        "ctn":"1234567893",
        "callerId":"9b69aeb5-ced5-4bf0-ba77-ee547288bb15",
        "name":"Lima Luciele",
        "email":"luciele.lima@example.com"
    },
    {
        "ctn":"1234567895",
        "callerId":"6aa54ad1-184d-467f-9a9f-d4b433965c84",
        "name":"Makkinga Elizabet",
        "email":"elizabet.makkinga@example.com"
    },
    {
        "ctn":"1234567897",
        "callerId":"f2cd50f8-176d-4426-9f55-b69c1346fc4f",
        "name":"Neumaier Grzegorz",
        "email":"grzegorz.neumaier@example.com"
    },
    {
        "ctn":"1234567896",
        "callerId":"cc8b08fb-2af8-409f-a38f-ed2435600e43",
        "name":"Anderson Isabella",
        "email":"isabella.anderson@example.com"
    }
]
```
