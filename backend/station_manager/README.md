# Station manager core

## About

This part of the project is the core of the whole system. 
It's responsible for holding all the business logic, messages bus 
and the REST API.

Javalin is a web framework of chosen.<br />
Java `20.0.*` is required to run the application.<br />
IntelliJ IDEA is an IDE of chosen.

## Committing your work

- Never commit directly to main branch
- Always ask at least 1 (better 2) people to review your work
- Use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) to format your commit message
- PR should be at max 300-400 lines (better less 200)
- Always add description to your pull request
- Notify other about the work being done

## Local development

First clone the repo with a given command:
```shell
git clone https://github.com/Shower-Capybara/CapybaraOnRails.git
```

Once finished, open the project in IntelliJ IDEA. For some reason, it's not
properly working with multi-root project out of the box, so you will need to first open
one of the projects and then on the right-hand side click `Gradle` -> `+` and attach
another project.

Ask your teammates for environment variables values
and populate `.env` file with appropriate values from 
[.env.local.template](.env.local.template).

## Useful commands

Requirements:
- `GNU make`
- `Docker`
- `docker-compose` (future)

Run the web application:

```shell
make run
```

Run the tests:

```shell
make test
```

Build docker image from the application:

```shell
make build
```

Run the docker image:

```shell
make docker_run
```

If you are interested in additional gradle features you can read it online or
execute following command to list available tasks:

```shell
gradle tasks --all
```
