# lab-ms-graph

## image

```sh
podman build -f src/main/docker/Dockerfile.uber-jar \                                                                                                        
  -t ghcr.io/antoniocaccamo/labs/lab-ms-graph:${version} .
```


## k8s

```shell

# namespace
kubectl create namespace labs   

kubectl create secret tls tls-secret \
  --cert=/tmp/${domain}/fullchain.pem     \
  --key=/tmp/${domain}/privkey.pem     \
  --namespace labs

kubectl create secret docker-registry pcr-secret \ 
  --docker-server=registry.private.antoniocaccamo.work \  
  --docker-username=antonio \ 
  --docker-password=${CR_PRIV} \  
  --namespace=labs

# secret
kubectl create secret generic -n labs lab-ms-graph \                                               
--from-literal=TENANT_ID=${TENANT_ID} \
--from-literal=WEBAPP_CLIENT_ID=${WEBAPP_CLIENT_ID} \
--from-literal=WEBAPP_CLIENT_SECRET=${WEBAPP_CLIENT_SECRET} \
--from-literal=MS_GRAPH_CLIENT_ID=${MS_GRAPH_CLIENT_ID} \
--from-literal=MS_GRAPH_CLIENT_SECRET=${MS_GRAPH_CLIENT_SECRET}

```


### references

 - https://learn.microsoft.com/en-us/graph/sdks/choose-authentication-providers?tabs=java#client-credentials-provider   

## Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/lab-ms-graph-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
