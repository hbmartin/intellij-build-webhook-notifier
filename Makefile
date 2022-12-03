lint:
	./gradlew clean ktlintCheck detekt

tag:
	git tag "v`grep '^pluginVersion' gradle.properties  | cut -f2 -d'=' | tr -d ' \n' `"
	git push --tags

check-master:
	if [[ `git rev-parse --abbrev-ref HEAD` != "main" ]]; then exit 1; fi

check-unstaged:
	git diff-index --quiet HEAD --

pull:
	git pull

sign:
	./gradlew buildPlugin signPlugin

release: check-master check-unstaged pull lint tag sign
