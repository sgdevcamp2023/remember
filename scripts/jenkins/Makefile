.PHONY: up down all

all: up

up-mysql:
        sudo docker-compose pull
        sudo docker-compose up -d
        sudo docker image prune -f

down-mysql:
        sudo docker-compose down

up-kafka:
        sudo docker-compose -f docker-compose-kafka.yml pull
        sudo docker-compose -f docker-compose-kafka.yml up -d
        sudo docker image prune -f

down-kafka:
        sudo docker-compose -f docker-compose-kafka.yml down

up-community:
        #sudo docker rmi harmony_community-service
        sudo docker-compose -f docker-compose-community.yml pull
        sudo docker-compose -f docker-compose-community.yml up -d
        sudo docker image prune -f

down-community:
        sudo docker-compose -f docker-compose-community.yml down
        sudo docker image prune -f
        sudo docker rmi harmony_community-service

up-jenkins:
        sudo docker-compose -f docker-compose-jenkins.yml pull
        sudo docker-compose -f docker-compose-jenkins.yml up -d
        sudo docker image prune -f

down-jenkins:
        sudo docker-compose -f docker-compose-jenkins.yml down
        sudo docker image prune -f

up-elasticsearch:
        #sudo docker rmi harmony_elasticsearch
        sudo docker-compose -f docker-compose-elasticsearch.yml pull
        sudo docker-compose -f docker-compose-elasticsearch.yml up -d
        sudo docker image prune -f

down-elasticsearch:
        sudo docker-compose -f docker-compose-elasticsearch.yml down
        sudo docker image prune -f

up-fluentd:
        sudo docker-compose -f docker-compose-fluentd.yml pull
        sudo docker-compose -f docker-compose-fluentd.yml up -d
        sudo docker image prune -f

down-fluentd:
        sudo docker-compose -f docker-compose-fluentd.yml down
        sudo docker image prune -f

up-kibana:
        sudo docker-compose -f docker-compose-kibana.yml pull
        sudo docker-compose -f docker-compose-kibana.yml up -d
        sudo docker image prune -f

down-kibana:
        sudo docker-compose -f docker-compose-kibana.yml down
        sudo docker image prune -f

up-redis:
        sudo docker-compose -f docker-compose-redis.yml pull
        sudo docker-compose -f docker-compose-redis.yml up -d
        sudo docker image prune -f

down-redis:
        sudo docker-compose -f docker-compose-redis.yml down
        sudo docker image prune -f

up-state:
        sudo docker-compose -f docker-compose-state.yml pull
        sudo docker-compose -f docker-compose-state.yml up -d
        sudo docker image prune -f

down-state:
        sudo docker-compose -f docker-compose-state.yml down
        sudo docker image prune -f
        sudo docker rmi harmony_state-service

up-mongo:
        sudo docker-compose -f docker-compose-mongo.yml pull
        sudo docker-compose -f docker-compose-mongo.yml up -d
        sudo docker image prune -f

down-mongo:
        sudo docker-compose -f docker-compose-mongo.yml down
        sudo docker image prune -f

up-chat:
        #sudo docker rmi harmony_chat-service
        sudo docker-compose -f docker-compose-chat.yml pull
        sudo docker-compose -f docker-compose-chat.yml up -d
        sudo docker image prune -f

down-chat:
        sudo docker-compose -f docker-compose-chat.yml down
        sudo docker image prune -f
        sudo docker rmi harmony_chat-service

up-mssql:
        sudo docker-compose -f docker-compose-mssql.yml pull
        sudo docker-compose -f docker-compose-mssql.yml up -d
        sudo docker image prune -f

down-mssql:
        sudo docker-compose -f docker-compose-mssql.yml down
        sudo docker image prune -f

up-user:
        #sudo docker rmi user-service
        sudo docker-compose -f docker-compose-user.yml pull
        sudo docker-compose -f docker-compose-user.yml up -d
        sudo docker image prune -f

down-user:
        sudo docker-compose -f docker-compose-user.yml down
        sudo docker image prune -f
        sudo docker rmi user-service

up-media:
        # sudo docker rmi harmony_media-service
        sudo docker-compose -f docker-compose-media.yml pull
        sudo docker-compose -f docker-compose-media.yml up -d
        sudo docker image prune -f

down-media:
        sudo docker-compose -f docker-compose-media.yml down
        sudo docker image prune -f
        sudo docker rmi harmony_media-service

up-gateway:
        #sudo docker rmi smile-gateway
        sudo docker-compose -f docker-compose-gateway.yml pull
        sudo docker-compose -f docker-compose-gateway.yml up -d
        sudo docker image prune -f

down-gateway:
        sudo docker-compose -f docker-compose-gateway.yml down
        sudo docker image prune -f
        sudo docker rmi smile-gateway
