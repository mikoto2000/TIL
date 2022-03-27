const path = require('path');
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');

const distPath = path.resolve(__dirname, 'dist');

module.exports = {
    mode: 'development',
    entry: './index.js',
    output: {
        path: distPath,
        filename: '[name].bundle.js'
    },
    devServer: {
        static: distPath,
        open: true
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.ttf$/,
                use: ['file-loader']
            }
        ]
    },
    plugins: [
        new MonacoWebpackPlugin({
            languages: ['json']
        })
    ]
};
