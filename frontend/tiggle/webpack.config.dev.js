const { merge } = require("webpack-merge");
const webpack = require("webpack");
const baseConfig = require("./webpack.config");

module.exports = merge(baseConfig, {
  mode: "development",
  devServer: {
    port: 3000,
  },
  plugins: [
    new webpack.DefinePlugin({
      "process.env.NODE_ENV": JSON.stringify('development'),
      'process.env.REACT_APP_API_URL': JSON.stringify(process.env.REACT_APP_DEV_API_URL),
    }),
  ],
});
