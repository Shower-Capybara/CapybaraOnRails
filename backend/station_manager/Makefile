STATION_MANAGER_IMAGE_NAME ?= station_manager.core
STATION_MANAGER_CONTAINER_NAME ?= station_manager

run:
	env $$(cat .env) ./gradlew app:run

test:
	env $$(cat .env) ./gradlew app:test

build:
	gradle app:build
	docker build -t $(STATION_MANAGER_IMAGE_NAME) .

docker_run:
	docker run \
		--rm \
		--env-file=.env \
		-p 8000:8000 \
		--name $(STATION_MANAGER_CONTAINER_NAME) \
		 $(STATION_MANAGER_IMAGE_NAME)
