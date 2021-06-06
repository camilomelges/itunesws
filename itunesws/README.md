# ITunesWs

### Configurations

    - Environments with default values, you can override this:
        - ITUNESWS_SERVER_PORT:80
        - ITUNESWS_SERVER_ERROR_INCLUDE_STACK_TRACE:never
        - ITUNESWS_SERVER_ERROR_WHITELABEL_ENABLED:true
        - ITUNESWS_LOGGING_LEVEL_WEB:error
        - ITUNESWS_SPRING_BEAN_DEFINITION_OVERRIDING:true
        - ITUNESWS_SPRING_DEVTOOLS_ADD_PROPERTIES:true
        - ITUNESWS_UTILS_RABBITMQ_HOST:localhost
        - ITUNESWS_UTILS_RABBITMQ_PORT:5672
        - ITUNESWS_UTILS_RABBITMQ_USERNAME:root
        - ITUNESWS_UTILS_RABBITMQ_PASSWORD:root
        - ITUNESWS_UTILS_RABBITMQ_QUEUES_ITUNES_DATA_QUEUE_NAME:iTunesSaveDataQueue
        - ITUNESWS_UTILS_RABBITMQ_IMAGE_NAME:rabbitmq
        - ITUNESWS_UTILS_RABBITMQ_IMAGE_VERSION:latest
        - ITUNESWS_AUTH_BASIC_USERNAME:root
        - ITUNESWS_AUTH_BASIC_PASSWORD:root
        - ITUNESWS_CORS_ALLOWED_ORIGINS:*
        - ITUNESWS_CORS_ALLOWED_METHODS:GET, OPTIONS
        - ITUNESWS_CORS_ALLOWED_HEADERS:*
        - ITUNESWS_ITUNES_API_PROTOCOL:http
        - ITUNESWS_ITUNES_API_URL:itunes.apple.com
        - ITUNESWS_SWAGGER_ENABLED:true
        - ITUNESWS_SWAGGER_PATHS:/v2/api-docs, /swagger-resources/configuration/ui, /swagger-resources, /swagger-resources/configuration/security, /swagger-ui.html, /webjars/**


### Run with docker

    - Simple example:
        - docker run --name test-env -p 5555:80 -d vellos/itunesws
    - Your can change variables too, look a example changing server port:
        - docker run --name test-env -p 5555:5555 -e ITUNESWS_SERVER_PORT=5555 -d vellos/itunesws

### Run with maven

    - Simple example:
        - mvn spring-boot:run -DskipTests=true
    - Your can change variables too, look a example changing server port:
        - mvn spring-boot:run -Dspring-boot.run.arguments=--ITUNESWS_SERVER_PORT=5555 -DskipTests=true

### Swagger

    - Run application
    - go to ${protocol}://${host}:${port}/swagger-ui/#
    - User and password are root for default

### Usage

    - Just make a GET request to /itunes-data and pass ?query=what you whant
    - The return data is a list of maps, in a tipical response, is an array of javascript objects