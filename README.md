# Astronomikon

Website zum Ansehen und Bearbeiten astronomischer Fotos.

## Alte Version mit JSF als SPA mit CouchDB Backend

# Starten

## Tomcat + CouchDB in Docker

```
mvn clean install 
docker-compose up -d
```

Mit http://localhost:5984/_utils/ kann man in die CouchDB schauen.

Hier müssen vor der ersten Benutzung noch die Daten importiert werden. 
Wie unten in [Import](#Import) beschrieben.

Die Oberfläche findet man unter http://localhost:8080/astro/astro.xhtml

## Jetty von Maven heraus

Geht nur, wenn eine CouchDB da ist! Die kann aber auch über Docker laufen, wenn der Port
exposed wird. Z.B. indem man wie oben startet und dann nur den Tomcat stoppt 
(docker stop astronomikon_tomcat_1). 
```
mvn clean install jetty:run 
```

#CouchDB Export Import

Aus: https://stackoverflow.com/questions/32442339/what-is-the-best-way-to-export-couchdb

## Export
```
curl -X GET http://localhost:5984/astro/_all_docs\?include_docs\=true > db.json

curl -X GET 'http://localhost:5984/astro/_all_docs?include_docs=true' | jq '{"docs": [.rows[].doc]}' | jq 'del(.docs[]._rev)' > db-patched.json

```

evtl: löschen von 2 Einträgen für "irgendwas"
replace "rows" -> "docs"

## Import
```
curl -d @db.json -H "Content-type: application/json" -X POST http://127.0.0.1:5984/astro/_bulk_docs
```
