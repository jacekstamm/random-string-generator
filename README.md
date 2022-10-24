# Random Unique String Generator
## Requirements:
1) Java 11
2) Docker - to run Database
### HOW TO RUN POSTGRESQL DB WITH DOCKER:
1) Paste to terminal `docker-compose up -d`
### APPLICATION IS RUNNING ON PORT `8080`
### ENDPOINTS IN APPLICATION
1) POST -> `/generator/runJob` - Start Strings generator job

Example of request body:

{
   "minLength": int (2),
   "maxLength": int (3),
   "chars": String ("AbZt"),
   "results": int (18)
}

In success will return 201 and `JOB ID` in response for further use, to download results.
2) GET -> `generator/jobThreadsRunning` check how many job threads are running
3) GET -> `generator/getJobResult/{id}` Download results of job with `id`
##HOW TO RUN (On Mac/Linux):
1) Application `gradle bootRun`
2) Tests `gralde test`
