export ISTIO_VERSION=1.22.8
curl -L https://istio.io/downloadIstio | ISTIO_VERSION=$ISTIO_VERSION TARGET_ARCH=x86_64 sh -
cd istio-$ISTIO_VERSION
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo --set hub=gcr.io/istio-release

cd istio-1.22.8
kubectl apply -f samples/addons

kubectl get all -n istio-system