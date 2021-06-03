/*
 * TESLERDOC - UI
 * Copyright (C) 2020 Tesler Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')

const cssRules = [
    { loader: 'style-loader' },
    {
        loader: 'css-loader',
        options: {
            modules: true,
            localIdentName: '[name]__[local]___[hash:base64:5]'
        }
    }
]

module.exports = (env = {}, options) => {
    const isProduction = options.mode === 'production'
    const indexFileName = `index.${isProduction ? 'ftl' : 'html'}`
    return {
        entry: [
            './src/index.tsx'
        ],
        devtool: 'source-map',
        devServer: {
            host: '0.0.0.0',
            port: 8081,
            disableHostCheck: true,
            publicPath: '/ui/',
            historyApiFallback: {
                index: '/ui/'
            },
            proxy: {
                '/api': {
                    target: 'http://localhost:8080'
                },
                '/sitemap.xml': {
                    target: 'http://localhost:8080'
                }
            }
        },
        module: {
            rules: [
                /**
                 * source-map-loader handles source maps from external libraries (e.g. @tesler-ui/core)
                 */
                {
                    test: /\.(js|css)$/,
                    use: ['source-map-loader'],
                    enforce: 'pre'
                },
                {
                    test: /\.tsx?$/,
                    use: {
                        loader: 'ts-loader',
                        options: {
                            compilerOptions: {
                                sourceMap: true
                            }
                        }
                    },
                    include: /src/,
                    exclude: [/node_modules/, /(\.test.tsx?$)/, path.resolve(__dirname, 'src', 'tests')],
                },
                {
                    test: /\.less$/,
                    include: [
                        path.resolve(__dirname, 'src')
                    ],
                    exclude: [
                        path.resolve(__dirname, 'node_modules'),
                        path.resolve(__dirname, 'src', 'antd.less'),
                    ],
                    use: [
                        { loader: 'style-loader' },
                        {
                            loader: 'css-loader',
                            options: {
                                modules: true,
                                localIdentName: '[name]__[local]___[hash:base64:5]'
                            }
                        },
                        { loader: 'less-loader', options: { javascriptEnabled: true } }
                    ]
                },
                {
                    test: /antd\.less$/,
                    include: [
                        path.resolve(__dirname, 'src')
                    ],
                    use: [
                        { loader: 'style-loader' },
                        { loader: 'css-loader', options: { modules: false } },
                        { loader: 'less-loader', options: { javascriptEnabled: true }}
                    ]
                },
                {
                    test: /\.(png|jpg|jpeg|gif|woff|woff2)$/,
                    use: {
                        loader: 'url-loader',
                        options: {
                            limit: 8192,
                            name: (file) => {
                                return '[path][name].[ext]'
                            }
                        }
                    }
                },
                {
                    test: /\.(eot|ttf|wav|mp3|svg)$/,
                    use: {
                        loader: 'file-loader',
                        options: {
                            name: (file) => {
                                return '[path][name].[ext]'
                            },
                            outputPath: 'assets'
                        }
                    }
                }
            ]
        },
        resolve: {
            modules: ['src', 'node_modules'],
            extensions: ['.tsx', '.ts', '.js'],
            symlinks: false
        },
        output: {
            filename: '[name].[chunkhash].js',
            path: path.resolve(__dirname, 'dist')
        },
        plugins: [
            new CleanWebpackPlugin(),
            new HtmlWebpackPlugin({
                hash: true,
                filename: indexFileName,
                template: './src/templates/index.ejs',
                appOptions: JSON.stringify({ })
            })
        ],
        optimization: {
            splitChunks: {
                cacheGroups: {
                    tesler: {
                        test: /[\\/]node_modules[\\/]@tesler-ui[\\/]core/,
                        name: 'tesler-ui',
                        chunks: 'all'
                    }
                }
            },
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    sourceMap: true,
                    terserOptions: {
                        ecma: 5,
                        mangle: false,
                        keep_classnames: true
                    },
                }),
            ]
        }
    }
}
