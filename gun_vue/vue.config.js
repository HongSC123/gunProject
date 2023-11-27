const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: "../gun/src/main/resources/static",
  devServer: {
    proxy: {
      '/':{
        target: 'http://localhost:8888',
        changeOrigin: true,
      }
    }
  },
  pluginOptions: {
    vuetify: {
      autoImport: true,
      defaultAssets: {
        font: {
          family: 'Noto Sans'
        },
        icons: 'mdi'
      },
      theme: {
        dark: true,
        themes: {
          dark: {
            primary: '#42b983',
            secondary: '#424242',
            accent: '#82B1FF',
            error: '#FF5252',
            info: '#2196F3',
            success: '#4CAF50',
            warning: '#FFC107'
          }
        }
      }
    }
  }
})
