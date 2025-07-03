echo "도메인: $1"
echo "도메인버전: $2"
echo "registry 이름: $3"

cd $1

npx vite build
npm run build
echo "빌드 완료"
npm install --save-dev vite

az acr login --name $3
# 이미지 생성
docker build -t $3.azurecr.io/$1:$2 .
echo "이미지 생성 완료"
docker push $3.azurecr.io/$1:$2
echo "이미지 push 완료"