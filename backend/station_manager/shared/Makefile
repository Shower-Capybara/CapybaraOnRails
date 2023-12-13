test:
	docker compose -f ../../docker-compose.yml up -d postgres
	env $$(cat .env.local.template) ../gradlew test; ret=$$?; \
	docker stop postgres; \
	exit ret;
