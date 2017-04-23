1. Create network 
docker network create --driver bridge Hac_Network
2. Run RabbitMQ
docker run --net=Hac_Network --hostname my-rabbit -p 5672:5672 -p 15672:15672 -v C:/rabbitData:/var/lib/rabbitmq/mnesia/ --name rabbit rabbitmq:3-management
3. Docker-compose up
