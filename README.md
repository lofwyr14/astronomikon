# astro
Website to view and manage astronomical photos.

# CouchDB Starten

```
docker-compose up -d
```

# CouchDB Export Import

```
curl -X GET http://localhost:5984/astro/_all_docs\?include_docs\=true > db.json

curl -X GET 'http://localhost:5984/astro/_all_docs?include_docs=true' | jq '{"docs": [.rows[].doc]}' | jq 'del(.docs[]._rev)' > db-patched.json

```

evtl: löschen von 2 Einträgen für "irgendwas"
replace "rows" -> "docs"

```
curl -d @db.json -H "Content-type: application/json" -X POST http://127.0.0.1:5984/astro/_bulk_docs
```


https://stackoverflow.com/questions/32442339/what-is-the-best-way-to-export-couchdb

# Bugs/Issues/Todos:

* add font awesome css to plain HTML version.

# Lokalen Apachen starten
der auf target/www served:
```
docker run -dit --rm -p 80:80 -v `pwd`/src/main/www:/usr/local/apache2/htdocs/ httpd
```
