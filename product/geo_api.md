

|  | Google Maps API | Wikidata| Max Mind  |
|:-----------|:------------|:-------------|:-------------|
| 4都市が含まれるか<br>aigle,switzerland<br>bern,switzerland<br>dubna-moscow,russia<br>chengdu,china| ok        | ng         | ok         |
| 利用規約     | 1 日あたり 2,500 回のリクエストまで無料。<br>課金が有効である場合、追加のリクエストは 1,000 回あたり $0.50 USD。1 日あたり最大 100,000 回。      | free       | ＄370($100 update)       |
| APIのI/F       | web-api,get,json,req=address,res=lat,lng        | database,csv         | database,csv or binary         |
| Pros         | place_idが取れる<br>cityの粒度をうまく扱ってくれる          | サーバへのリクエスト不要           | サーバへのリクエスト不要           |
| Cons       | -       | クエリーが書けるが複雑       | 複数レコードあるデータ       |
| 備考       | -       | -       | 元データのgeolocationは無料       |



- AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8
- https://maps.googleapis.com/maps/api/geocode/json?address=aigle+switzerland&key=AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8
- https://maps.googleapis.com/maps/api/geocode/json?address=bern+switzerland&key=AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8
- https://maps.googleapis.com/maps/api/geocode/json?address=dubna+russia&key=AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8
- https://maps.googleapis.com/maps/api/geocode/json?address=chengdu+china&key=AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8


https://maps.googleapis.com/maps/api/geocode/json?address=yuzawa+japan&key=AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8
