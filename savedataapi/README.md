# SaveDataApi

###### Api to persist data in mongo

### Configurations

    - Environments with default values, you can override this:
        - SAVE_DATA_API_SERVER_PORT:5556
        - SAVE_DATA_API_SERVER_ERROR_INCLUDE_STACK_TRACE:never
        - SAVE_DATA_API_SERVER_ERROR_WHITELABEL_ENABLED:true
        - SAVE_DATA_API_LOGGING_LEVEL_WEB:debug
        - SAVE_DATA_API_SPRING_BEAN_DEFINITION_OVERRIDING:true
        - SAVE_DATA_API_SPRING_DEVTOOLS_ADD_PROPERTIES:true
        - SAVE_DATA_API_UTILS_RABBITMQ_HOST:localhost
        - SAVE_DATA_API_UTILS_RABBITMQ_PORT:5672
        - SAVE_DATA_API_UTILS_RABBITMQ_USERNAME:root
        - SAVE_DATA_API_UTILS_RABBITMQ_PASSWORD:root
        - SAVE_DATA_API_UTILS_RABBITMQ_QUEUES_ITUNES_DATA_QUEUE_NAME:iTunesSaveDataQueue
        - SAVE_DATA_API_UTILS_RABBITMQ_IMAGE_NAME:rabbitmq
        - SAVE_DATA_API_UTILS_RABBITMQ_IMAGE_VERSION:latest
        - SAVE_DATA_API_CORS_ALLOWED_ORIGINS:*
        - SAVE_DATA_API_CORS_ALLOWED_METHODS:GET, OPTIONS
        - SAVE_DATA_API_CORS_ALLOWED_HEADERS:*
        - SAVE_DATA_API_UTILS_RABBITMQ_LISTENER_SIMPLE_PREFETCH:5
        - SAVE_DATA_API_SPRING_DATA_MONGODB_DATABASE:itunesdata
        - SAVE_DATA_API_SPRING_DATA_MONGODB_HOST:localhost
        - SAVE_DATA_API_SPRING_DATA_MONGODB_PORT:27017
        - SAVE_DATA_API_SPRING_DATA_MONGODB_USERNAME:root
        - SAVE_DATA_API_SPRING_DATA_MONGODB_PASSWORD:root
        - SAVE_DATA_API_SPRING_DATA_MONGODB_AUTO_INDEX_CREATION:true

### Docker registry

    - Using docker.hub package registry -> https://hub.docker.com/repository/docker/vellos/savedataapi

### Maven registry

    - Using git.rafamilo package registry -> https://git.rafamilo.com/rafamilo/backend-project/-/packages

### Run with docker

    - Simple example:
        - docker run --name test-env -p 5556:80 -d vellos/savedataapi
    - Your can change variables too, look a example changing server port:
        - docker run --name test-env -p 5556:5556 -e SAVE_DATA_API_SERVER_PORT=5556 -d vellos/savedataapi

### Run with maven

    - Simple example:
        - mvn spring-boot:run -DskipTests=true
    - Your can change variables too, look a example changing server port:
        - mvn spring-boot:run -Dspring-boot.run.arguments=--SAVE_DATA_API_SERVER_PORT=5556 -DskipTests=true

### Usage

    - Rabbitmq receives a message on iTunesSaveDataQueue queue, from itunesws, then this api consumes data and save on mongodb