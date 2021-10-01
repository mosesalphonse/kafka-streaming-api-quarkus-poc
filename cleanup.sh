kubectl -n kafka delete -f  kube/03_services/sash-http-services.yaml
    
kubectl -n kafka delete -f  kube/03_services/sash-frontend-services.yaml
                                    
kubectl -n kafka delete -f  kube/02_workloads/sash-kafka.yaml
    
kubectl -n kafka delete -f  kube/02_workloads/sash-http.yaml
                  
kubectl -n kafka delete -f  kube/02_workloads/sash-frontend.yaml

kubectl delete -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka;

kubectl delete -f https://strimzi.io/examples/latest/kafka/kafka-persistent-single.yaml -n kafka;
                  
kubectl delete namespace kafka;
