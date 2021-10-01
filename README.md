
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
deploy kafaka cluster on kubernetes on 'kafka' namespace
       
``` 
./kube/01_kafka/deploy-kafka.sh

```
