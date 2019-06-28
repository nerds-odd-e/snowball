class HttpClient {

  static fetch(url, options){
      return fetch(`${HttpClient.baseUrl()}${url}`, options).then((res)=>res.json())
  }

  static baseUrl(){
      if (process.env.NODE_ENV === 'development') {
        return 'http://localhost:8060'
      }
      return ''
  }
}
export default HttpClient
