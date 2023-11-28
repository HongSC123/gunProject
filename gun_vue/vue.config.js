module.exports = {
  transpileDependencies: [],
  outputDir: "../gun/src/main/resources/static",
  devServer: {
    proxy: {
      '/':{
        target: 'http://localhost:8888',
        changeOrigin: true,
      }
    }
  }
};