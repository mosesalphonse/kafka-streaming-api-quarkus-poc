
# Kafka Streaming API with Quarkus Reactive - Reference Implementation

## POC:

<li>
Kafka Streaming API with Server Sent Events
  <li>
HTTP reactive (just to compare against kafka streaming)
    <li>
Istio Integration

## Prerequisite:

 <li>
Kubernetes Cluster (tested on GKE version 1.20.9-gke.1001)
   <li>
Istio Instalation (tested on istio-1.11.3)
     <li>
Kiali Dashboard

**Note**: Need a kubernetes cluster with Istio and Kiali dashboard installed on it. Refer istio 'https://istio.io/latest/docs/setup/getting-started/' for istio installation

##  Reference Architecture:

TBC

##  Steps:

```
    
git clone https://github.com/mosesalphonse/kafka-streaming-api-quarkus-poc.git

cd kafka-streaming-api-quarkus-poc

```
Create namespace and add that namespace label to instruct Istio to automatically inject Envoy sidecar proxies when you deploy your application
       
```
  
kubectl create namespace kafka

kubectl label namespace kafka istio-injection=enabled

```
deploy kafka cluster on kubernetes on 'kafka' namespace
       
``` 
./kube/01_kafka/deploy-kafka.sh

```
**Deploy all the 3 workloads (frontend, http processor, messaging processor):**
       
**Note:** the below steps will build and push the docker image into the image repo. In My implementation, I have configured my GCR (Google Container Repository).
     
You must modify the below properties values before making the build:

 **sash-kafka:**
 
**application.properties**
 <li>
quarkus.container-image.registry=gcr.io // if you choose different image repo, modify this, if you are happy to use google, leave as it is
   <li>
quarkus.container-image.group=moses-327312 // in case of google repo, this should be your Google Project ID
     <li>
kafka.bootstrap.servers=my-cluster-kafka-bootstrap.kafka.svc.cluster.local:9092 // Kafaka bootstrap cluster

**sash-http:**

**application.properties**
<li>
quarkus.container-image.registry=gcr.io  // if you choose different image repo, modify this, if you are happy to use google, leave as it is
         <li>
quarkus.container-image.group=moses-327312 // in case of google repo, this should be your Google Project ID

**sash-frontend:**

**application.properties**
           <li>
quarkus.container-image.registry=gcr.io // if you choose different image repo, modify this, if you are happy to use google, leave as it is
             <li>
quarkus.container-image.group=moses-327312 // in case of google repo, this should be your Google Project ID
               <li>
com.sash.quarkus.coffeeshop.http.BaristaService/mp-rest/url=http://sash-http-service.kafka.svc.cluster.local:8080 // sash-http endpoint, leave it as it if you are not changing
                 <li>
kafka.bootstrap.servers=my-cluster-kafka-bootstrap.kafka.svc.cluster.local:9092  // in case of google repo, this should be your Google Project ID
                   
       
```
cd sash-http

mvn clean package -Dquarkus.container-image.push=true

cd .. 
 
cd sash-kafka

mvn clean package -Dquarkus.container-image.push=true

cd .. 

cd sash-frontend

mvn clean package -Dquarkus.container-image.push=true

cd .. 
```

**Note:** If the above build is successfull, docker image should be uploaded into the respective image repo
  
                   
 Deploy the workloads into Kubernetes cluster:
                   
 You must modify the image path in the deployment files under /kube/02_workloads directory
 
 ```
     kubectl -n kafka apply -f  kube/02_workloads/sash-kafka.yaml
     
     kubectl -n kafka apply -f  kube/02_workloads/sash-http.yaml
                   
     kubectl -n kafka apply -f  kube/02_workloads/sash-frontend.yaml
                   
                  
 ```
                  
  Deploy the services into Kubernetes cluster:
                   
 
 ```
     kubectl -n kafka apply -f  kube/03_services/sash-http-services.yaml
     
     kubectl -n kafka apply -f  kube/03_services/sash-frontend-services.yaml
                                     
 ```
                  
  Enable Istion Ingress Gateway to allow traffic into sash-frontend over http port 80 using Kiali dashboard(UI):
                   
 
 ```
    istioctl dashboard kiali
                                               
 ```
      
 After login into kiali dashboard, go to 'Services' and select 'sash-frontend-service'
 
  ```
  Select 'Request Routing' action on the top left
                   
  Select 'Add Routing Rule' 
                   
  Go to 'Advanced Options' and select 'Gateways'
                   
  Enable 'Add Gateways'
                   
  And Click 'Create' button
                   
   ```
