# Station manager core

## About

Station manager backend. To get more info on the subprojects
navigate to their README.md's.

Javalin is a web framework of chosen.<br />
Java `20.0.*` is required to run the application.<br />
IntelliJ IDEA is an IDE of chosen.

## Committing your work

- Never commit directly to main branch
- Always ask at least 1 (better 2) people to review your work
- Use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) to format your commit message
- PR should be at max 300-400 lines (better less 200)
- Always add description to your pull request
- Use `squash and merge` to merge your PR to target branch
- Notify other about the work being done

## Local development

First clone the repo with a given command:

```shell
git clone https://github.com/Shower-Capybara/CapybaraOnRails.git
```

Then install pre-commit hook with a given command:

```shell
pre-commit install
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
- `docker compose`
