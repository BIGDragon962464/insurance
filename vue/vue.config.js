// 适配不同屏幕
const autoprefixer = require('autoprefixer'); //根据浏览器适配css，自动加前缀，不用可以删除
const pxtorem = require('postcss-pxtorem');
module.exports = {
    //postcss-pxtorem
    css: {
        loaderOptions: {
            css: {},
            postcss: {
                postcssOptions: {
                    plugins: [
                        autoprefixer(),
                        pxtorem({
                            rootValue: 37.5,  //手机设计稿宽度为375px的
                            propList: ['*'], //属性的选择器，*表示通用
                            exclude: /web/i, //忽略web下的所有文件
                            selectorBlackList: ['.a-'] // 过滤掉.a- 开头的class，不进行rem转换
                        }),
                    ]
                }
            }
        }
    },
};