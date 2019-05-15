# massive_mailer
### background
jspはロジックとビューの記述が一緒くたになっているため、
分離する目的でvue.jsを選定しました
今後はJavaでAPIを用意して、順次JSPからVue.js切り替えます。

## Project setup
```
$ npm install
```

### Compiles and hot-reloads for development
先にマッシブメーラーのサーバーを立ち上げる必要があります。
```
$ cd ../
$ `./gradlew appStart
```

```
$ cd web/
$ npm run serve
```
and access to http://localhost:8080

## Compiles and minifies for production
```
$ npm run build
```

### Run your tests
```
$ npm run test
```

### Lints and fixes files
```
$ npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
