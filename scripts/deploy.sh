# 가동중인 awsstudy 도커 중단 및 삭제
sudo docker ps -a -q --filter "name=hup-server" | grep -q . && docker stop hup-server && docker rm hup-server | true
sudo docker ps -a -q --filter "name=hup-database" | grep -q . && docker stop hup-database && docker rm hup-database | true

# 기존 이미지 삭제
sudo docker rmi rlawjddn5980/hup-server

# 도커허브 이미지 pull
sudo docker pull rlawjddn5980/hup-server

# 도커 run
sudo docker-compose up

# 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제되지 않습니다.
sudo docker rmi -f $(docker images -f "dangling=true" -q) || true