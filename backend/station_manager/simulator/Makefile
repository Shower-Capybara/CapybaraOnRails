SIMULATOR_IMAGE_NAME ?= station_manager.simulator
SIMULATOR_CONTAINER_NAME ?= simulator

run:
	env $$(cat .env) ../gradlew run

test:
	env $$(cat .env) ../gradlew simulator:test

build:
	gradle simulator:build
	docker build -t $(SIMULATOR_IMAGE_NAME) .

docker_run:
	docker run \
		--rm \
		--env-file=.env \
		-p 8000:8000 \
		--name $(SIMULATOR_CONTAINER_NAME) \
		 $(SIMULATOR_IMAGE_NAME)

publish:
	jq -c . data/ClientBeingServedEvent.json | redis-cli -x publish generator:commands