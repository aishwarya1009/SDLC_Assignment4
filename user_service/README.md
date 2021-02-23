## Running Application Locally

If running locally for development and testing purposes
1. Set environment variables for MySQL
    - You can do this with `source set-env-vars.sh` or you can export them manually via CLI
    - You can change values of these env vars to fit your needs
1. Run `./run-mysql.sh` to get the mysql instance up and running mapped to port 6603 and 
to create the schema
1. Run the application with `./gradlew run`
    - You can also use `clean`, `test`, `build`, etc

## If building and pushing image to AWS ECR

This will be done via Github Actions, so if incorporating Actions, follow the directions
1. Set up an ECR repo that has the same name that you have set for your `$IMAGE_NAME` in `set-env-vars.sh`
    - ** Make sure the repo name matches the `$IMAGE_NAME` and the region matches the `$AWS_REGION` (see `Building and Pushing to ECR` below)
    - https://docs.aws.amazon.com/AmazonECR/latest/userguide/repository-create.html
1. Set up an IAM user for ECR
    - https://docs.aws.amazon.com/AmazonECR/latest/userguide/get-set-up-for-amazon-ecr.html
    - Allow the user access to CLI to get the Access Key ID and Secret Access Key
    - Make sure to take note of the Access Key ID and Secret Access Key as they will be set via environment variables (see below)
1. Download the latest AWS CLI
    - https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html
    
## Building and Pushing to ECR

If building and pushing your image to ECR, run the following
1. Follow directions above for `If building and pushing image to AWS ECR`
1. Set secret environment variables for AWS ECR manually by `export ENV_VAR`

    | Environment Var       | Description               | Example       | Requirement   |
    | -----------           | -----------               | -----------   | -----------   |
    | AWS_ACCOUNT_ID        | AWS ECR Account Id        | 350644415930  | Required      |
    | AWS_REGION            | AWS Region                | us-west-2     | Required      |
    | AWS_ACCESS_KEY_ID     | AWS User Access Key       | see link      | Required      |
    | AWS_SECRET_ACCESS_KEY | AWS User Secret Access Key| see link      | Required      |
    
    https://docs.aws.amazon.com/AmazonECR/latest/userguide/get-set-up-for-amazon-ecr.html
        
1. Set environment variables for MySQL
    - You can do this with `source set-env-vars.sh` or you can export them manually via CLI
    - You can change values of these env vars to fit your needs
1. Run `./run-mysql.sh` to get the mysql instance up and running mapped to port 6603 and 
to create the schema
1. Run `./build-image.sh` to build and tag the image for your AWS ECR
    1. You can also run your app by running `./run-image.sh`
1. Run `./push-to-ecr.sh` to push the image to ECR

## Github Actions CI

If you have Github Actions enabled for your git repo, the workflow under `.github/workflows/build_pushECR.yml` will be triggered
1. This is triggered on push or pull-request to main or dev branches and you can change this configuration in the yml file
1. The process is very similar to the one described above in `Building and Pushing to ECR` with two key differences:
    1. The secret environment variables for AWS (see above) should be set up in Github Secrets
    1. The Github Actions workflow will call the script `set-gh-actions-env-vars.sh` after calling `set-env-vars.sh`
        - This is required to properly set environment variables in Github Actions
        - If you make any changes to `set-env-vars.sh`, make sure you make the corresponding changes in `set-gh-actions-env-vars.sh`

## Flyway

If you change your db schema, you will have to update the flyway schema
1. In `application.yml`, change to `hibernate.hbm2ddl.auto: update`
1. `./gradlew run`
1. Copy the output from hibernate creating the table and add this to the `dbmigrations` folder as a sql script
1. In `application.yml`, change to `hibernate.hbm2ddl.auto: none`
1. `./gradlew run`

#### Micronaut 2.3.1 Documentation

- [User Guide](https://docs.micronaut.io/2.3.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.3.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.3.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

#### Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

#### Feature flyway documentation

- [Micronaut Flyway Database Migration documentation](https://micronaut-projects.github.io/micronaut-flyway/latest/guide/index.html)

- [https://flywaydb.org/](https://flywaydb.org/)

#### Feature jdbc-tomcat documentation

- [Micronaut Tomcat JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

#### Feature hibernate-validator documentation

- [Micronaut Hibernate Validator documentation](https://micronaut-projects.github.io/micronaut-hibernate-validator/latest/guide/index.html)

#### Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

