# astro
Website to view and manage astronomical photos.

#CouchDB Export Import

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
