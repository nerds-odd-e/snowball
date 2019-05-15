module.exports = {
  devServer: {
   proxy: {
     '/': {
       target: 'http://localhost:8070',
       changeOrigin: true
     }
   }
  }
}
