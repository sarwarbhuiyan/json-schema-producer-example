#!/bin/sh
curl -X POST http://localhost:8081/subjects/testjsonschema-value/versions -H "Content-Type: application/vnd.schemaregistry.v1+json" -d "{ \"schema\": $(jq -R -s '.' src/main/resources/user.json), \"schemaType\":\"JSON\" }"

