GENERATOR_IMAGE_NAME ?= station_manager.generator
GENERATOR_CONTAINER_NAME ?= generator

run:
	env $$(cat .env) gradle app:run

test:
	env $$(cat .env) gradle app:test

build:
	gradle app:build
	docker build -t $(GENERATOR_IMAGE_NAME) .

docker_run:
	docker run \
		--rm \
		--env-file=.env \
		-p 8000:8000 \
		--name $(GENERATOR_CONTAINER_NAME) \
		 $(GENERATOR_IMAGE_NAME)
